package com.imobiliaria_api.service;

import com.imobiliaria_api.dto.request.PagamentoParcelaRequestDTO;
import com.imobiliaria_api.dto.request.VendaRequestDTO;
import com.imobiliaria_api.dto.response.CalculoJurosResponse;
import com.imobiliaria_api.dto.response.ParcelaVendaResponseDTO;
import com.imobiliaria_api.dto.response.VendaResponseDTO;
import com.imobiliaria_api.exception.BusinessException;
import com.imobiliaria_api.exception.ResourceNotFoundException;
import com.imobiliaria_api.mapper.ParcelaVendaMapperManual;
import com.imobiliaria_api.mapper.VendaMapperManual;
import com.imobiliaria_api.model.*;
import com.imobiliaria_api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ParcelaVendaRepository parcelaVendaRepository;
    private final EmpresaRepository empresaRepository;
    private final ImovelRepository imovelRepository;
    private final ClienteRepository clienteRepository;
    private final ConfiguracaoFinanceiraRepository configuracaoFinanceiraRepository;
    private final VendaMapperManual vendaMapper;
    private final ParcelaVendaMapperManual parcelaVendaMapper;
    private final CalculoJurosService calculoJurosService;

    @Transactional
    public VendaResponseDTO create(Long empresaId, VendaRequestDTO requestDTO) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));
        Imovel imovel = imovelRepository.findByIdAndEmpresaId(requestDTO.getImovelId(), empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Imóvel não encontrado"));
        Cliente cliente = clienteRepository.findByIdAndEmpresaId(requestDTO.getClienteId(), empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        ConfiguracaoFinanceira config = configuracaoFinanceiraRepository.findByEmpresaId(empresaId)
                .orElse(null);
                
        Venda venda = vendaMapper.toEntity(requestDTO);
        venda.setEmpresa(empresa);
        venda.setImovel(imovel);
        venda.setCliente(cliente);
        venda.setStatus("ativo");
        
        if (config != null && Boolean.TRUE.equals(requestDTO.getAplicaIgpm())) {
            venda.setIgpmContrato(config.getIgpmAtual());
        }

        Venda saved = vendaRepository.save(venda);
        
        // Gerar parcelas
        BigDecimal saldoFinanciado = requestDTO.getValorImovel().subtract(requestDTO.getValorEntrada());
        BigDecimal valBase = saldoFinanciado.divide(new BigDecimal(requestDTO.getQuantidadeParcelas()), 2, java.math.RoundingMode.HALF_UP);
        
        for (int i = 1; i <= requestDTO.getQuantidadeParcelas(); i++) {
            ParcelaVenda parcela = new ParcelaVenda();
            parcela.setVenda(saved);
            parcela.setNumeroParcela(i);
            
            LocalDate dataVenc = requestDTO.getDataContrato()
                .plusMonths(i)
                .withDayOfMonth(Math.min(requestDTO.getDiaVencimento(), requestDTO.getDataContrato().plusMonths(i).lengthOfMonth()));
            parcela.setDataVencimento(dataVenc);
            
            parcela.setValorBase(valBase);
            parcela.setValorFinal(valBase);
            
            if (Boolean.TRUE.equals(requestDTO.getAplicaIgpm()) && config != null && config.getIgpmAtual() != null) {
                int anoReajuste = (i - 1) / 12;
                if (anoReajuste > 0) {
                    BigDecimal fator = BigDecimal.ONE.add(config.getIgpmAtual().divide(new BigDecimal("100"), 4, java.math.RoundingMode.HALF_UP));
                    BigDecimal fatorAcumulado = fator.pow(anoReajuste);
                    BigDecimal valReajustado = valBase.multiply(fatorAcumulado).setScale(2, java.math.RoundingMode.HALF_UP);
                    parcela.setValorFinal(valReajustado);
                    parcela.setIgpmAplicado(config.getIgpmAtual());
                }
            }
            
            parcela.setStatus("pendente");
            parcelaVendaRepository.save(parcela);
        }

        return makeVendaResponse(saved);
    }

    public List<VendaResponseDTO> findAll(Long empresaId) {
        return vendaRepository.findByEmpresaId(empresaId).stream()
                .map(this::makeVendaResponse)
                .collect(Collectors.toList());
    }

    public VendaResponseDTO findById(Long id, Long empresaId) {
        Venda venda = getVenda(id, empresaId);
        return makeVendaResponse(venda);
    }

    public List<ParcelaVendaResponseDTO> getParcelas(Long id, Long empresaId) {
        getVenda(id, empresaId);
        return parcelaVendaRepository.findByVendaIdOrderByNumeroParcelaAsc(id).stream()
                .map(parcelaVendaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ParcelaVendaResponseDTO pagarParcela(Long id, Long parcelaId, Long empresaId, PagamentoParcelaRequestDTO dto) {
        Venda venda = getVenda(id, empresaId);
        ParcelaVenda parcela = parcelaVendaRepository.findByIdAndVendaId(parcelaId, id)
                .orElseThrow(() -> new ResourceNotFoundException("Parcela não encontrada"));
                
        if ("pago".equals(parcela.getStatus())) {
            throw new BusinessException("Parcela já se encontra paga.");
        }

        ConfiguracaoFinanceira config = configuracaoFinanceiraRepository.findByEmpresaId(empresaId).orElse(null);
        LocalDate dataPgto = dto.getDataPagamento() != null ? dto.getDataPagamento() : LocalDate.now();
        
        CalculoJurosResponse calculo = new CalculoJurosResponse(parcela.getValorFinal(), BigDecimal.ZERO, BigDecimal.ZERO, 0);
        
        if (config != null && dataPgto.isAfter(parcela.getDataVencimento())) {
            calculo = calculoJurosService.calcularJuros(parcela.getValorFinal(), parcela.getDataVencimento(), dataPgto, config);
        }

        parcela.setDataPagamento(dataPgto);
        parcela.setFormaPagamento(dto.getFormaPagamento());
        parcela.setObservacao(dto.getObservacao());
        parcela.setStatus("pago");
        parcela.setMultaValor(calculo.getMulta());
        parcela.setJurosValor(calculo.getJuros());
        parcela.setValorPago(calculo.getValorTotal());
        
        ParcelaVenda saved = parcelaVendaRepository.save(parcela);
        return parcelaVendaMapper.toResponseDTO(saved);
    }

    @Transactional
    public void updateStatus(Long id, Long empresaId, String status) {
        Venda venda = getVenda(id, empresaId);
        venda.setStatus(status);
        vendaRepository.save(venda);
    }

    private Venda getVenda(Long id, Long empresaId) {
        return vendaRepository.findByIdAndEmpresaId(id, empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada"));
    }

    private VendaResponseDTO makeVendaResponse(Venda venda) {
        VendaResponseDTO res = vendaMapper.toResponseDTO(venda);
        List<ParcelaVendaResponseDTO> parcelas = parcelaVendaRepository.findByVendaIdOrderByNumeroParcelaAsc(venda.getId())
            .stream().map(parcelaVendaMapper::toResponseDTO).collect(Collectors.toList());
        res.setParcelas(parcelas);
        
        if (res.getSaldoFinanciado() == null && venda.getValorImovel() != null && venda.getValorEntrada() != null) {
            res.setSaldoFinanciado(venda.getValorImovel().subtract(venda.getValorEntrada()));
        }
        return res;
    }
}
