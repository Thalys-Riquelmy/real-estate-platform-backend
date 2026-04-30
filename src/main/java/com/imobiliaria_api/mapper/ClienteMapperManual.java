package com.imobiliaria_api.mapper;

import com.imobiliaria_api.dto.request.ClienteRequestDTO;
import com.imobiliaria_api.dto.response.ClienteResponseDTO;
import com.imobiliaria_api.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapperManual {

    public Cliente toEntity(ClienteRequestDTO dto) {
        if (dto == null) return null;
        
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setCpfCnpj(dto.getCpfCnpj());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEmail(dto.getEmail());
        cliente.setEndereco(dto.getEndereco());
        cliente.setNumero(dto.getNumero());
        cliente.setComplemento(dto.getComplemento());
        cliente.setBairro(dto.getBairro());
        cliente.setCep(dto.getCep());
        cliente.setCidade(dto.getCidade());
        cliente.setEstado(dto.getEstado());
        cliente.setTipo(dto.getTipo());
        cliente.setObservacoes(dto.getObservacoes());
                
        return cliente;
    }

    public ClienteResponseDTO toResponseDTO(Cliente entity) {
        if (entity == null) return null;
        
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setCpfCnpj(entity.getCpfCnpj());
        dto.setTelefone(entity.getTelefone());
        dto.setEmail(entity.getEmail());
        dto.setEndereco(entity.getEndereco());
        dto.setNumero(entity.getNumero());
        dto.setComplemento(entity.getComplemento());
        dto.setBairro(entity.getBairro());
        dto.setCep(entity.getCep());
        dto.setCidade(entity.getCidade());
        dto.setEstado(entity.getEstado());
        dto.setTipo(entity.getTipo());
        dto.setObservacoes(entity.getObservacoes());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        
        return dto;
    }

    public void updateEntityFromDTO(ClienteRequestDTO dto, Cliente entity) {
        if (dto == null) return;
        
        entity.setNome(dto.getNome());
        entity.setCpfCnpj(dto.getCpfCnpj());
        entity.setTelefone(dto.getTelefone());
        entity.setEmail(dto.getEmail());
        entity.setEndereco(dto.getEndereco());
        entity.setNumero(dto.getNumero());
        entity.setComplemento(dto.getComplemento());
        entity.setBairro(dto.getBairro());
        entity.setCep(dto.getCep());
        entity.setCidade(dto.getCidade());
        entity.setEstado(dto.getEstado());
        entity.setTipo(dto.getTipo());
        entity.setObservacoes(dto.getObservacoes());
        
    }
}