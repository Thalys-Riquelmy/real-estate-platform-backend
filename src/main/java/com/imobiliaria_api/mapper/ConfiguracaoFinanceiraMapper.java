package com.imobiliaria_api.mapper;

import com.imobiliaria_api.dto.request.ConfiguracaoFinanceiraRequestDTO;
import com.imobiliaria_api.dto.response.ConfiguracaoFinanceiraResponseDTO;
import com.imobiliaria_api.model.ConfiguracaoFinanceira;
import org.springframework.stereotype.Component;

@Component
public class ConfiguracaoFinanceiraMapper {

    public ConfiguracaoFinanceira toEntity(ConfiguracaoFinanceiraRequestDTO dto) {
        if (dto == null) return null;
        
        ConfiguracaoFinanceira config = new ConfiguracaoFinanceira();
        config.setJurosMensal(dto.getJurosMensal());
        config.setJurosDiario(dto.getJurosDiario());
        config.setMultaFixa(dto.getMultaFixa());
        config.setDiasCarencia(dto.getDiasCarencia());
        config.setIgpmAtual(dto.getIgpmAtual());
        config.setIgpmDataReferencia(dto.getIgpmDataReferencia());
        config.setFormasPagamento(dto.getFormasPagamento());
        config.setObservacoes(dto.getObservacoes());
        
        return config;
    }

    public ConfiguracaoFinanceiraResponseDTO toResponseDTO(ConfiguracaoFinanceira entity) {
        if (entity == null) return null;
        
        ConfiguracaoFinanceiraResponseDTO dto = new ConfiguracaoFinanceiraResponseDTO();
        dto.setId(entity.getId());
        dto.setJurosMensal(entity.getJurosMensal());
        dto.setJurosDiario(entity.getJurosDiario());
        dto.setMultaFixa(entity.getMultaFixa());
        dto.setDiasCarencia(entity.getDiasCarencia());
        dto.setIgpmAtual(entity.getIgpmAtual());
        dto.setIgpmDataReferencia(entity.getIgpmDataReferencia());
        dto.setFormasPagamento(entity.getFormasPagamento());
        dto.setObservacoes(entity.getObservacoes());
        
        return dto;
    }

    public void updateEntityFromDTO(ConfiguracaoFinanceiraRequestDTO dto, ConfiguracaoFinanceira entity) {
        if (dto == null || entity == null) return;
        
        if (dto.getJurosMensal() != null) entity.setJurosMensal(dto.getJurosMensal());
        if (dto.getJurosDiario() != null) entity.setJurosDiario(dto.getJurosDiario());
        if (dto.getMultaFixa() != null) entity.setMultaFixa(dto.getMultaFixa());
        if (dto.getDiasCarencia() != null) entity.setDiasCarencia(dto.getDiasCarencia());
        if (dto.getIgpmAtual() != null) entity.setIgpmAtual(dto.getIgpmAtual());
        if (dto.getIgpmDataReferencia() != null) entity.setIgpmDataReferencia(dto.getIgpmDataReferencia());
        if (dto.getFormasPagamento() != null) entity.setFormasPagamento(dto.getFormasPagamento());
        if (dto.getObservacoes() != null) entity.setObservacoes(dto.getObservacoes());
    }
}