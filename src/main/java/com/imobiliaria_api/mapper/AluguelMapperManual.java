package com.imobiliaria_api.mapper;

import com.imobiliaria_api.dto.request.AluguelRequestDTO;
import com.imobiliaria_api.dto.response.AluguelResponseDTO;
import com.imobiliaria_api.model.Aluguel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AluguelMapperManual {

    private final EmpresaMapper empresaMapper;
    private final ImovelMapperManual imovelMapper;
    private final ClienteMapperManual clienteMapper;

    public Aluguel toEntity(AluguelRequestDTO dto) {
        if (dto == null) return null;
        
        Aluguel aluguel = new Aluguel();
        aluguel.setDataInicio(dto.getDataInicio());
        aluguel.setDataFim(dto.getDataFim());
        aluguel.setDiaVencimento(dto.getDiaVencimento());
        aluguel.setValorAluguel(dto.getValorAluguel());
        
        return aluguel;
    }

    public AluguelResponseDTO toResponseDTO(Aluguel entity) {
        if (entity == null) return null;
        
        AluguelResponseDTO dto = new AluguelResponseDTO();
        dto.setId(entity.getId());
        dto.setDataInicio(entity.getDataInicio());
        dto.setDataFim(entity.getDataFim());
        dto.setDiaVencimento(entity.getDiaVencimento());
        dto.setValorAluguel(entity.getValorAluguel());
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