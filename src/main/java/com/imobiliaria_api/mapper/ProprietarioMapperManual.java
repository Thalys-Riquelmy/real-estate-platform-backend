package com.imobiliaria_api.mapper;

import com.imobiliaria_api.dto.request.ProprietarioRequestDTO;
import com.imobiliaria_api.dto.response.ProprietarioResponseDTO;
import com.imobiliaria_api.model.Proprietario;
import org.springframework.stereotype.Component;

@Component
public class ProprietarioMapperManual {

    public Proprietario toEntity(ProprietarioRequestDTO dto) {
        if (dto == null) return null;
        
        Proprietario proprietario = new Proprietario();
        proprietario.setNome(dto.getNome());
        proprietario.setCpfCnpj(dto.getCpfCnpj());
        proprietario.setTelefone(dto.getTelefone());
        proprietario.setEmail(dto.getEmail());
        
        // Endereço
        proprietario.setEndereco(dto.getEndereco());
        proprietario.setNumero(dto.getNumero());
        proprietario.setComplemento(dto.getComplemento());
        proprietario.setBairro(dto.getBairro());
        proprietario.setCep(dto.getCep());
        proprietario.setCidade(dto.getCidade());
        proprietario.setEstado(dto.getEstado());
        
        // Dados bancários
        proprietario.setBanco(dto.getBanco());
        proprietario.setAgencia(dto.getAgencia());
        proprietario.setConta(dto.getConta());
        
        proprietario.setAtivo(true);
        
        return proprietario;
    }

    public ProprietarioResponseDTO toResponseDTO(Proprietario entity) {
        if (entity == null) return null;
        
        ProprietarioResponseDTO dto = new ProprietarioResponseDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setCpfCnpj(entity.getCpfCnpj());
        dto.setTelefone(entity.getTelefone());
        dto.setEmail(entity.getEmail());
        
        // Endereço
        dto.setEndereco(entity.getEndereco());
        dto.setNumero(entity.getNumero());
        dto.setComplemento(entity.getComplemento());
        dto.setBairro(entity.getBairro());
        dto.setCep(entity.getCep());
        dto.setCidade(entity.getCidade());
        dto.setEstado(entity.getEstado());
        
        // Dados bancários
        dto.setBanco(entity.getBanco());
        dto.setAgencia(entity.getAgencia());
        dto.setConta(entity.getConta());
        
        dto.setAtivo(entity.getAtivo());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        
        if (entity.getEmpresa() != null) {
            com.imobiliaria_api.dto.response.EmpresaResponseDTO empresaDTO = new com.imobiliaria_api.dto.response.EmpresaResponseDTO();
            empresaDTO.setId(entity.getEmpresa().getId());
            empresaDTO.setNome(entity.getEmpresa().getNome());
            empresaDTO.setCnpj(entity.getEmpresa().getCnpj());
            empresaDTO.setTelefone(entity.getEmpresa().getTelefone());
            empresaDTO.setEmail(entity.getEmpresa().getEmail());
            empresaDTO.setEndereco(entity.getEmpresa().getEndereco());
            empresaDTO.setCidade(entity.getEmpresa().getCidade());
            empresaDTO.setEstado(entity.getEmpresa().getEstado());
            empresaDTO.setLogoUrl(entity.getEmpresa().getLogoUrl());
            empresaDTO.setAtivo(entity.getEmpresa().getAtivo());
            empresaDTO.setDataCadastro(entity.getEmpresa().getDataCadastro());
            dto.setEmpresa(empresaDTO);
        }
        
        return dto;
    }

    public void updateEntityFromDTO(ProprietarioRequestDTO dto, Proprietario entity) {
        if (dto == null) return;
        
        entity.setNome(dto.getNome());
        entity.setCpfCnpj(dto.getCpfCnpj());
        entity.setTelefone(dto.getTelefone());
        entity.setEmail(dto.getEmail());
        
        // Endereço
        entity.setEndereco(dto.getEndereco());
        entity.setNumero(dto.getNumero());
        entity.setComplemento(dto.getComplemento());
        entity.setBairro(dto.getBairro());
        entity.setCep(dto.getCep());
        entity.setCidade(dto.getCidade());
        entity.setEstado(dto.getEstado());
        
        // Dados bancários
        entity.setBanco(dto.getBanco());
        entity.setAgencia(dto.getAgencia());
        entity.setConta(dto.getConta());
    }
}