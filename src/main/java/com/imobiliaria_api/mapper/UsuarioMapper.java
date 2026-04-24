package com.imobiliaria_api.mapper;

import com.imobiliaria_api.dto.request.UsuarioRequestDTO;
import com.imobiliaria_api.dto.response.UsuarioResponseDTO;
import com.imobiliaria_api.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {

    private final EmpresaMapper empresaMapper;

    public Usuario toEntity(UsuarioRequestDTO dto) {
        if (dto == null) return null;
        
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setPerfil(dto.getPerfil());
        usuario.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);
        
        return usuario;
    }

    public UsuarioResponseDTO toResponseDTO(Usuario entity) {
        if (entity == null) return null;
        
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        dto.setPerfil(entity.getPerfil());
        dto.setAtivo(entity.getAtivo());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        
        if (entity.getEmpresa() != null) {
            dto.setEmpresa(empresaMapper.toResponseDTO(entity.getEmpresa()));
        }
        
        return dto;
    }

    public void updateEntityFromDTO(UsuarioRequestDTO dto, Usuario entity) {
        if (dto == null || entity == null) return;
        
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setPerfil(dto.getPerfil());
        if (dto.getAtivo() != null) {
            entity.setAtivo(dto.getAtivo());
        }
    }
}