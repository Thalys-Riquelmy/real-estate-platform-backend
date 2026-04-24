package com.imobiliaria_api.mapper;

import com.imobiliaria_api.dto.request.EmpresaRequestDTO;
import com.imobiliaria_api.dto.response.EmpresaResponseDTO;
import com.imobiliaria_api.model.Empresa;
import org.springframework.stereotype.Component;

@Component
public class EmpresaMapper {

    public Empresa toEntity(EmpresaRequestDTO dto) {
        if (dto == null) return null;
        
        Empresa empresa = new Empresa();
        empresa.setNome(dto.getNome());
        empresa.setCnpj(dto.getCnpj());
        empresa.setTelefone(dto.getTelefone());
        empresa.setEmail(dto.getEmail());
        empresa.setEndereco(dto.getEndereco());
        empresa.setCidade(dto.getCidade());
        empresa.setEstado(dto.getEstado());
        empresa.setLogoUrl(dto.getLogoUrl());
        
        return empresa;
    }

    public EmpresaResponseDTO toResponseDTO(Empresa entity) {
        if (entity == null) return null;
        
        EmpresaResponseDTO dto = new EmpresaResponseDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setCnpj(entity.getCnpj());
        dto.setTelefone(entity.getTelefone());
        dto.setEmail(entity.getEmail());
        dto.setEndereco(entity.getEndereco());
        dto.setCidade(entity.getCidade());
        dto.setEstado(entity.getEstado());
        dto.setLogoUrl(entity.getLogoUrl());
        dto.setAtivo(entity.getAtivo());
        dto.setDataCadastro(entity.getDataCadastro());
        
        return dto;
    }

    public void updateEntityFromDTO(EmpresaRequestDTO dto, Empresa entity) {
        if (dto == null || entity == null) return;
        
        entity.setNome(dto.getNome());
        entity.setCnpj(dto.getCnpj());
        entity.setTelefone(dto.getTelefone());
        entity.setEmail(dto.getEmail());
        entity.setEndereco(dto.getEndereco());
        entity.setCidade(dto.getCidade());
        entity.setEstado(dto.getEstado());
        entity.setLogoUrl(dto.getLogoUrl());
    }
}