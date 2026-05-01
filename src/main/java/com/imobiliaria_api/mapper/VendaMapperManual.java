package com.imobiliaria_api.mapper;

import com.imobiliaria_api.dto.request.VendaRequestDTO;
import com.imobiliaria_api.dto.response.VendaResponseDTO;
import com.imobiliaria_api.model.Venda;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VendaMapperManual {

    private final EmpresaMapper empresaMapper;
    private final ImovelMapperManual imovelMapper;
    private final ClienteMapperManual clienteMapper;

    public Venda toEntity(VendaRequestDTO dto) {
        if (dto == null) return null;
        
        Venda venda = new Venda();
        venda.setDataContrato(dto.getDataContrato());
        venda.setValorImovel(dto.getValorImovel());
        venda.setValorEntrada(dto.getValorEntrada());
        venda.setDiaVencimento(dto.getDiaVencimento());
        venda.setQuantidadeParcelas(dto.getQuantidadeParcelas());
        venda.setAplicaIgpm(dto.getAplicaIgpm() != null ? dto.getAplicaIgpm() : false);
        
        return venda;
    }

    public VendaResponseDTO toResponseDTO(Venda entity) {
        if (entity == null) return null;
        
        VendaResponseDTO dto = new VendaResponseDTO();
        dto.setId(entity.getId());
        dto.setDataContrato(entity.getDataContrato());
        dto.setValorImovel(entity.getValorImovel());
        dto.setValorEntrada(entity.getValorEntrada());
        dto.setSaldoFinanciado(entity.getSaldoFinanciado());
        dto.setQuantidadeParcelas(entity.getQuantidadeParcelas());
        dto.setDiaVencimento(entity.getDiaVencimento());
        dto.setAplicaIgpm(entity.getAplicaIgpm());
        dto.setIgpmContrato(entity.getIgpmContrato());
        dto.setStatus(entity.getStatus());
        dto.setContratoUrl(entity.getContratoUrl());
        dto.setCreatedAt(entity.getCreatedAt());
                
        if (entity.getEmpresa() != null) {
            dto.setEmpresa(empresaMapper.toResponseDTO(entity.getEmpresa()));
        }
        if (entity.getImovel() != null) {
            dto.setImovel(imovelMapper.toResponseDTO(entity.getImovel()));
        }
        if (entity.getCliente() != null) {
            dto.setCliente(clienteMapper.toResponseDTO(entity.getCliente()));
        }
        
        return dto;
    }
}