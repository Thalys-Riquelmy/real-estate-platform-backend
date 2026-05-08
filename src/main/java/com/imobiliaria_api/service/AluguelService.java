package com.imobiliaria_api.service;

import com.imobiliaria_api.dto.request.AluguelRequestDTO;
import com.imobiliaria_api.dto.request.PagamentoParcelaRequestDTO;
import com.imobiliaria_api.dto.response.AluguelResponseDTO;
import com.imobiliaria_api.dto.response.CalculoJurosResponse;
import com.imobiliaria_api.dto.response.PrestacaoAluguelResponseDTO;
import com.imobiliaria_api.dto.response.ContratoPdfResponseDTO;
import com.imobiliaria_api.exception.BusinessException;
import com.imobiliaria_api.exception.ResourceNotFoundException;
import com.imobiliaria_api.mapper.AluguelMapperManual;
import com.imobiliaria_api.mapper.PrestacaoAluguelMapperManual;
import com.imobiliaria_api.model.*;
import com.imobiliaria_api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AluguelService {

    private final AluguelRepository aluguelRepository;
    private final PrestacaoAluguelRepository prestacaoRepository;
    private final EmpresaRepository empresaRepository;
    private final ImovelRepository imovelRepository;
    private final ClienteRepository clienteRepository;
    private final ConfiguracaoFinanceiraRepository configuracaoFinanceiraRepository;
    private final AluguelMapperManual aluguelMapper;
    private final PrestacaoAluguelMapperManual prestacaoAluguelMapper;
    private final CalculoJurosService calculoJurosService;

    @Transactional
    public AluguelResponseDTO create(Long empresaId, AluguelRequestDTO requestDTO) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));
        Imovel imovel = imovelRepository.findByIdAndEmpresaId(requestDTO.getImovelId(), empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Imóvel não encontrado"));
        Cliente cliente = clienteRepository.findByIdAndEmpresaId(requestDTO.getClienteId(), empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        Aluguel aluguel = aluguelMapper.toEntity(requestDTO);
        aluguel.setEmpresa(empresa);
        aluguel.setImovel(imovel);
        aluguel.setCliente(cliente);
        aluguel.setStatus("ativo");

        Aluguel saved = aluguelRepository.save(aluguel);
        
        int meses = 12;
        if (requestDTO.getDataFim() != null) {
            meses = (int) java.time.temporal.ChronoUnit.MONTHS.between(
                    requestDTO.getDataInicio().withDayOfMonth(1), 
                    requestDTO.getDataFim().withDayOfMonth(1));
            if (meses <= 0) meses = 1;
        }

        gerarPrestacoes(saved, requestDTO.getValorAluguel(), requestDTO.getDataInicio(), requestDTO.getDiaVencimento(), meses);

        return makeResponse(saved);
    }
    
    @Transactional
    public AluguelResponseDTO createWithContrato(Long empresaId, AluguelRequestDTO requestDTO, MultipartFile contrato) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));
        Imovel imovel = imovelRepository.findByIdAndEmpresaId(requestDTO.getImovelId(), empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Imóvel não encontrado"));
        Cliente cliente = clienteRepository.findByIdAndEmpresaId(requestDTO.getClienteId(), empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        Aluguel aluguel = aluguelMapper.toEntity(requestDTO);
        aluguel.setEmpresa(empresa);
        aluguel.setImovel(imovel);
        aluguel.setCliente(cliente);
        aluguel.setStatus("ativo");
        
        if (contrato != null && !contrato.isEmpty()) {
            byte[] pdfBytes = processarPdf(contrato);
            aluguel.setContratoPdf(pdfBytes);
            System.out.println("PDF salvo - Tamanho: " + pdfBytes.length + " bytes");
        }

        Aluguel saved = aluguelRepository.save(aluguel);
        
        int meses = 12;
        if (requestDTO.getDataFim() != null) {
            meses = (int) java.time.temporal.ChronoUnit.MONTHS.between(
                    requestDTO.getDataInicio().withDayOfMonth(1), 
                    requestDTO.getDataFim().withDayOfMonth(1));
            if (meses <= 0) meses = 1;
        }

        gerarPrestacoes(saved, requestDTO.getValorAluguel(), requestDTO.getDataInicio(), requestDTO.getDiaVencimento(), meses);

        return makeResponse(saved);
    }
    
    @Transactional
    public AluguelResponseDTO atualizarContrato(Long id, Long empresaId, MultipartFile contrato) {
        Aluguel aluguel = getAluguel(id, empresaId);
        
        byte[] pdfBytes = processarPdf(contrato);
        aluguel.setContratoPdf(pdfBytes);
        
        Aluguel updated = aluguelRepository.save(aluguel);
        return makeResponse(updated);
    }
    
    public byte[] obterContratoPdf(Long id, Long empresaId) {
        Aluguel aluguel = getAluguel(id, empresaId);
        
        System.out.println("=== OBTER PDF ===");
        System.out.println("ID: " + id);
        System.out.println("PDF existe? " + (aluguel.getContratoPdf() != null));
        if (aluguel.getContratoPdf() != null) {
            System.out.println("Tamanho: " + aluguel.getContratoPdf().length + " bytes");
        }
        
        if (aluguel.getContratoPdf() == null || aluguel.getContratoPdf().length == 0) {
            throw new BusinessException("Nenhum contrato PDF associado a este aluguel");
        }
        
        return aluguel.getContratoPdf();
    }
    
    public ContratoPdfResponseDTO obterContratoBase64(Long id, Long empresaId) {
        Aluguel aluguel = getAluguel(id, empresaId);
        
        ContratoPdfResponseDTO response = new ContratoPdfResponseDTO();
        response.setId(aluguel.getId());
        response.setNomeArquivo("contrato_aluguel_" + id + ".pdf");
        response.setContentType("application/pdf");
        
        if (aluguel.getContratoPdf() != null && aluguel.getContratoPdf().length > 0) {
            String base64Pdf = Base64.getEncoder().encodeToString(aluguel.getContratoPdf());
            response.setPdfBase64(base64Pdf);
            response.setTamanho((long) aluguel.getContratoPdf().length);
        } else {
            response.setPdfBase64(null);
            response.setTamanho(0L);
        }
        
        return response;
    }
    
    private byte[] compactarPdf(MultipartFile file) {
        try {
            if (file.getSize() > 10 * 1024 * 1024) {
                throw new BusinessException("Arquivo muito grande. Máximo 10MB.");
            }
            return file.getBytes();
        } catch (IOException e) {
            throw new BusinessException("Erro ao processar o arquivo PDF: " + e.getMessage());
        }
    }

    @Transactional
    public List<PrestacaoAluguelResponseDTO> gerarMaisPrestacoes(Long id, Long empresaId, int quantidadeMeses) {
        Aluguel aluguel = getAluguel(id, empresaId);
        List<PrestacaoAluguel> atuais = prestacaoRepository.findByAluguelIdOrderByDataVencimentoAsc(id);
        
        LocalDate dataBase = aluguel.getDataInicio();
        if(!atuais.isEmpty()) {
            dataBase = atuais.get(atuais.size() - 1).getDataVencimento();
        }
        
        gerarPrestacoes(aluguel, aluguel.getValorAluguel(), dataBase, aluguel.getDiaVencimento(), quantidadeMeses);
        
        return prestacaoRepository.findByAluguelIdOrderByDataVencimentoAsc(id).stream()
                .map(prestacaoAluguelMapper::toResponseDTO).collect(Collectors.toList());
    }

    private void gerarPrestacoes(Aluguel aluguel, BigDecimal valorBase, LocalDate dataReferencia, int diaVencimento, int qtde) {
        for (int i = 1; i <= qtde; i++) {
            PrestacaoAluguel p = new PrestacaoAluguel();
            p.setAluguel(aluguel);
            p.setValor(valorBase);
            LocalDate v = dataReferencia.plusMonths(i);
            p.setDataVencimento(v.withDayOfMonth(Math.min(diaVencimento, v.lengthOfMonth())));
            p.setStatus("pendente");
            prestacaoRepository.save(p);
        }
    }

    public List<AluguelResponseDTO> findAll(Long empresaId) {
        return aluguelRepository.findByEmpresaId(empresaId).stream()
                .map(this::makeResponse)
                .collect(Collectors.toList());
    }

    public AluguelResponseDTO findById(Long id, Long empresaId) {
        return makeResponse(getAluguel(id, empresaId));
    }

    public List<PrestacaoAluguelResponseDTO> getPrestacoes(Long id, Long empresaId) {
        getAluguel(id, empresaId);
        return prestacaoRepository.findByAluguelIdOrderByDataVencimentoAsc(id).stream()
                .map(prestacaoAluguelMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PrestacaoAluguelResponseDTO pagarPrestacao(Long id, Long prestacaoId, Long empresaId, PagamentoParcelaRequestDTO dto) {
        Aluguel aluguel = getAluguel(id, empresaId);
        PrestacaoAluguel prestacao = prestacaoRepository.findByIdAndAluguelId(prestacaoId, id)
                .orElseThrow(() -> new ResourceNotFoundException("Prestação não encontrada"));

        if ("pago".equals(prestacao.getStatus())) {
            throw new BusinessException("Prestação já paga.");
        }

        ConfiguracaoFinanceira config = configuracaoFinanceiraRepository.findByEmpresaId(empresaId).orElse(null);
        LocalDate pgto = dto.getDataPagamento() != null ? dto.getDataPagamento() : LocalDate.now();

        CalculoJurosResponse calc = new CalculoJurosResponse(prestacao.getValor(), BigDecimal.ZERO, BigDecimal.ZERO, 0);
        if (config != null && pgto.isAfter(prestacao.getDataVencimento())) {
            calc = calculoJurosService.calcularJuros(prestacao.getValor(), prestacao.getDataVencimento(), pgto, config);
        }

        prestacao.setDataPagamento(pgto);
        prestacao.setStatus("pago");
        prestacao.setFormaPagamento(dto.getFormaPagamento());
        prestacao.setObservacao(dto.getObservacao());
        prestacao.setMultaValor(calc.getMulta());
        prestacao.setJurosValor(calc.getJuros());
        prestacao.setValorPago(calc.getValorTotal());

        PrestacaoAluguel saved = prestacaoRepository.save(prestacao);
        return prestacaoAluguelMapper.toResponseDTO(saved);
    }
    
    @Transactional
    public void updateStatus(Long id, Long empresaId, String status) {
        Aluguel aluguel = getAluguel(id, empresaId);
        aluguel.setStatus(status);
        aluguelRepository.save(aluguel);
    }

    private Aluguel getAluguel(Long id, Long empresaId) {
        return aluguelRepository.findByIdAndEmpresaId(id, empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Aluguel não encontrado"));
    }

    private AluguelResponseDTO makeResponse(Aluguel aluguel) {
        AluguelResponseDTO res = aluguelMapper.toResponseDTO(aluguel);
        List<PrestacaoAluguelResponseDTO> prestacoes = prestacaoRepository.findByAluguelIdOrderByDataVencimentoAsc(aluguel.getId())
                .stream().map(prestacaoAluguelMapper::toResponseDTO).collect(Collectors.toList());
        res.setPrestacoes(prestacoes);
        
        res.setHasContratoPdf(aluguel.getContratoPdf() != null && aluguel.getContratoPdf().length > 0);
        
        return res;
    }
    
    private byte[] processarPdf(MultipartFile file) {
        try {
            if (file.getSize() > 10 * 1024 * 1024) {
                throw new BusinessException("Arquivo muito grande. Máximo 10MB.");
            }
            return file.getBytes();
        } catch (IOException e) {
            throw new BusinessException("Erro ao processar o arquivo PDF: " + e.getMessage());
        }
    }
}