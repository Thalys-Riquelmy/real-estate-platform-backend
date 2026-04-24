package com.imobiliaria_api.service;

import com.imobiliaria_api.dto.request.ConfiguracaoFinanceiraRequestDTO;
import com.imobiliaria_api.dto.response.ConfiguracaoFinanceiraResponseDTO;
import com.imobiliaria_api.exception.ResourceNotFoundException;
import com.imobiliaria_api.model.ConfiguracaoFinanceira;
import com.imobiliaria_api.model.Empresa;
import com.imobiliaria_api.repository.ConfiguracaoFinanceiraRepository;
import com.imobiliaria_api.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConfiguracaoFinanceiraService {

    private final ConfiguracaoFinanceiraRepository repository;
    private final EmpresaRepository empresaRepository;

    public ConfiguracaoFinanceiraResponseDTO getByEmpresaId(Long empresaId) {
        ConfiguracaoFinanceira config = repository.findByEmpresaId(empresaId).orElseGet(() -> {
            Empresa e = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));
            ConfiguracaoFinanceira nova = new ConfiguracaoFinanceira();
            nova.setEmpresa(e);
            return repository.save(nova);
        });
        return toDTO(config);
    }

    @Transactional
    public ConfiguracaoFinanceiraResponseDTO update(Long empresaId, ConfiguracaoFinanceiraRequestDTO dto) {
        ConfiguracaoFinanceira config = repository.findByEmpresaId(empresaId)
            .orElseGet(() -> {
                Empresa e = empresaRepository.findById(empresaId)
                    .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));
                ConfiguracaoFinanceira nova = new ConfiguracaoFinanceira();
                nova.setEmpresa(e);
                return nova;
            });
        
        BeanUtils.copyProperties(dto, config, "id", "empresa");
        ConfiguracaoFinanceira saved = repository.save(config);
        return toDTO(saved);
    }
    
    private ConfiguracaoFinanceiraResponseDTO toDTO(ConfiguracaoFinanceira entity) {
        ConfiguracaoFinanceiraResponseDTO dto = new ConfiguracaoFinanceiraResponseDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
