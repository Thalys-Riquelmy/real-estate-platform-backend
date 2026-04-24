package com.imobiliaria_api.mapper;

import com.imobiliaria_api.dto.request.ConfiguracaoEmailRequestDTO;
import com.imobiliaria_api.dto.response.ConfiguracaoEmailResponseDTO;
import com.imobiliaria_api.model.ConfiguracaoEmail;
import org.springframework.stereotype.Component;

@Component
public class ConfiguracaoEmailMapper {

    public ConfiguracaoEmail toEntity(ConfiguracaoEmailRequestDTO dto) {
        if (dto == null) return null;
        
        ConfiguracaoEmail config = new ConfiguracaoEmail();
        config.setSmtpHost(dto.getSmtpHost());
        config.setSmtpPort(dto.getSmtpPort());
        config.setEmailRemetente(dto.getEmailRemetente());
        config.setNomeRemetente(dto.getNomeRemetente());
        config.setUsername(dto.getUsername());
        config.setPassword(dto.getPassword());
        config.setAuth(dto.getAuth() != null ? dto.getAuth() : true);
        config.setStarttls(dto.getStarttls() != null ? dto.getStarttls() : true);
        config.setTimeout(dto.getTimeout() != null ? dto.getTimeout() : 5000);
        config.setConnectionTimeout(dto.getConnectionTimeout() != null ? dto.getConnectionTimeout() : 5000);
        config.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);
        config.setTesteEnviado(false);
        
        return config;
    }

    public ConfiguracaoEmailResponseDTO toResponseDTO(ConfiguracaoEmail entity) {
        if (entity == null) return null;
        
        ConfiguracaoEmailResponseDTO dto = new ConfiguracaoEmailResponseDTO();
        dto.setId(entity.getId());
        dto.setSmtpHost(entity.getSmtpHost());
        dto.setSmtpPort(entity.getSmtpPort());
        dto.setEmailRemetente(entity.getEmailRemetente());
        dto.setNomeRemetente(entity.getNomeRemetente());
        dto.setUsername(entity.getUsername());
        dto.setAuth(entity.getAuth());
        dto.setStarttls(entity.getStarttls());
        dto.setTimeout(entity.getTimeout());
        dto.setConnectionTimeout(entity.getConnectionTimeout());
        dto.setAtivo(entity.getAtivo());
        dto.setTesteEnviado(entity.getTesteEnviado());
        
        return dto;
    }

    public void updateEntityFromDTO(ConfiguracaoEmailRequestDTO dto, ConfiguracaoEmail entity) {
        if (dto == null || entity == null) return;
        
        entity.setSmtpHost(dto.getSmtpHost());
        entity.setSmtpPort(dto.getSmtpPort());
        entity.setEmailRemetente(dto.getEmailRemetente());
        entity.setNomeRemetente(dto.getNomeRemetente());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        if (dto.getAuth() != null) entity.setAuth(dto.getAuth());
        if (dto.getStarttls() != null) entity.setStarttls(dto.getStarttls());
        if (dto.getTimeout() != null) entity.setTimeout(dto.getTimeout());
        if (dto.getConnectionTimeout() != null) entity.setConnectionTimeout(dto.getConnectionTimeout());
        if (dto.getAtivo() != null) entity.setAtivo(dto.getAtivo());
    }
}