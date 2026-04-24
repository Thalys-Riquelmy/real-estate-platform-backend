package com.imobiliaria_api.service;

import com.imobiliaria_api.dto.request.ConfiguracaoEmailRequestDTO;
import com.imobiliaria_api.dto.request.ConfiguracaoFinanceiraRequestDTO;
import com.imobiliaria_api.dto.request.EmpresaRequestDTO;
import com.imobiliaria_api.dto.response.ConfiguracaoEmailResponseDTO;
import com.imobiliaria_api.dto.response.ConfiguracaoFinanceiraResponseDTO;
import com.imobiliaria_api.dto.response.EmpresaResponseDTO;
import com.imobiliaria_api.exception.BusinessException;
import com.imobiliaria_api.exception.ResourceNotFoundException;
import com.imobiliaria_api.mapper.ConfiguracaoEmailMapper;
import com.imobiliaria_api.mapper.ConfiguracaoFinanceiraMapper;
import com.imobiliaria_api.mapper.EmpresaMapper;
import com.imobiliaria_api.model.ConfiguracaoEmail;
import com.imobiliaria_api.model.ConfiguracaoFinanceira;
import com.imobiliaria_api.model.Empresa;
import com.imobiliaria_api.repository.ConfiguracaoEmailRepository;
import com.imobiliaria_api.repository.ConfiguracaoFinanceiraRepository;
import com.imobiliaria_api.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final ConfiguracaoEmailRepository configuracaoEmailRepository;
    private final ConfiguracaoFinanceiraRepository configuracaoFinanceiraRepository;
    private final EmpresaMapper empresaMapper;
    private final ConfiguracaoEmailMapper configuracaoEmailMapper;
    private final ConfiguracaoFinanceiraMapper configuracaoFinanceiraMapper;

    public EmpresaResponseDTO criarEmpresa(EmpresaRequestDTO dto) {
        log.info("Criando nova empresa: {}", dto.getNome());
        
        if (dto.getCnpj() != null && empresaRepository.existsByCnpj(dto.getCnpj())) {
            throw new BusinessException("CNPJ já cadastrado");
        }

        Empresa empresa = empresaMapper.toEntity(dto);
        empresa.setAtivo(true);
        
        Empresa savedEmpresa = empresaRepository.save(empresa);
        log.info("Empresa criada com sucesso: ID={}, Nome={}", savedEmpresa.getId(), savedEmpresa.getNome());
        
        return empresaMapper.toResponseDTO(savedEmpresa);
    }

    public List<EmpresaResponseDTO> listarTodas() {
        log.debug("Listando todas as empresas");
        return empresaRepository.findAll().stream()
            .map(empresaMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    public EmpresaResponseDTO buscarPorId(Long id) {
        log.debug("Buscando empresa por ID: {}", id);
        Empresa empresa = empresaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada com ID: " + id));
        return empresaMapper.toResponseDTO(empresa);
    }

    public EmpresaResponseDTO atualizarEmpresa(Long id, EmpresaRequestDTO dto) {
        log.info("Atualizando empresa ID: {}", id);
        
        Empresa empresa = empresaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada com ID: " + id));

        empresaMapper.updateEntityFromDTO(dto, empresa);
        
        Empresa updatedEmpresa = empresaRepository.save(empresa);
        log.info("Empresa atualizada com sucesso: ID={}", id);
        
        return empresaMapper.toResponseDTO(updatedEmpresa);
    }

    public void desativarEmpresa(Long id) {
        log.info("Desativando empresa ID: {}", id);
        
        Empresa empresa = empresaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada com ID: " + id));
        empresa.setAtivo(false);
        empresaRepository.save(empresa);
        
        log.info("Empresa desativada: ID={}", id);
    }

    public ConfiguracaoEmailResponseDTO configurarEmail(Long empresaId, ConfiguracaoEmailRequestDTO dto) {
        log.info("Configurando email para empresa ID: {}", empresaId);
        
        Empresa empresa = empresaRepository.findById(empresaId)
            .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));

        ConfiguracaoEmail configuracao = configuracaoEmailRepository.findByEmpresaId(empresaId)
            .orElse(null);

        if (configuracao == null) {
            configuracao = configuracaoEmailMapper.toEntity(dto);
            configuracao.setEmpresa(empresa);
        } else {
            configuracaoEmailMapper.updateEntityFromDTO(dto, configuracao);
        }

        ConfiguracaoEmail saved = configuracaoEmailRepository.save(configuracao);
        log.info("Configuração de email salva para empresa ID: {}", empresaId);
        
        return configuracaoEmailMapper.toResponseDTO(saved);
    }

    public ConfiguracaoEmailResponseDTO buscarConfiguracaoEmail(Long empresaId) {
        log.debug("Buscando configuração de email para empresa ID: {}", empresaId);
        
        ConfiguracaoEmail configuracao = configuracaoEmailRepository.findByEmpresaId(empresaId)
            .orElseThrow(() -> new ResourceNotFoundException("Configuração de email não encontrada"));
        return configuracaoEmailMapper.toResponseDTO(configuracao);
    }

    public ConfiguracaoFinanceiraResponseDTO configurarFinanceiro(Long empresaId, ConfiguracaoFinanceiraRequestDTO dto) {
        log.info("Configurando financeiro para empresa ID: {}", empresaId);
        
        Empresa empresa = empresaRepository.findById(empresaId)
            .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));

        ConfiguracaoFinanceira configuracao = configuracaoFinanceiraRepository.findByEmpresaId(empresaId)
            .orElse(null);

        if (configuracao == null) {
            configuracao = configuracaoFinanceiraMapper.toEntity(dto);
            configuracao.setEmpresa(empresa);
        } else {
            configuracaoFinanceiraMapper.updateEntityFromDTO(dto, configuracao);
        }

        ConfiguracaoFinanceira saved = configuracaoFinanceiraRepository.save(configuracao);
        log.info("Configuração financeira salva para empresa ID: {}", empresaId);
        
        return configuracaoFinanceiraMapper.toResponseDTO(saved);
    }

    public ConfiguracaoFinanceiraResponseDTO buscarConfiguracaoFinanceira(Long empresaId) {
        log.debug("Buscando configuração financeira para empresa ID: {}", empresaId);
        
        ConfiguracaoFinanceira configuracao = configuracaoFinanceiraRepository.findByEmpresaId(empresaId)
            .orElseThrow(() -> new ResourceNotFoundException("Configuração financeira não encontrada"));
        return configuracaoFinanceiraMapper.toResponseDTO(configuracao);
    }
}