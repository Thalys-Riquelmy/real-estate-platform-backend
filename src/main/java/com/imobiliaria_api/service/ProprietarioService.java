package com.imobiliaria_api.service;

import com.imobiliaria_api.dto.request.ProprietarioRequestDTO;
import com.imobiliaria_api.dto.response.ProprietarioResponseDTO;
import com.imobiliaria_api.exception.BusinessException;
import com.imobiliaria_api.exception.ResourceNotFoundException;
import com.imobiliaria_api.mapper.ProprietarioMapperManual;
import com.imobiliaria_api.model.Empresa;
import com.imobiliaria_api.model.Proprietario;
import com.imobiliaria_api.repository.EmpresaRepository;
import com.imobiliaria_api.repository.ProprietarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProprietarioService {

    private final ProprietarioRepository proprietarioRepository;
    private final EmpresaRepository empresaRepository;
    private final ProprietarioMapperManual proprietarioMapper;

    @Transactional
    public ProprietarioResponseDTO create(Long empresaId, ProprietarioRequestDTO requestDTO) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada com ID: " + empresaId));

        proprietarioRepository.findByCpfCnpjAndEmpresaId(requestDTO.getCpfCnpj(), empresaId)
                .ifPresent(p -> { throw new BusinessException("Já existe um proprietário com o CPF/CNPJ informado nesta empresa."); });

        Proprietario proprietario = proprietarioMapper.toEntity(requestDTO);
        proprietario.setEmpresa(empresa);

        Proprietario saved = proprietarioRepository.save(proprietario);
        return proprietarioMapper.toResponseDTO(saved);
    }

    public List<ProprietarioResponseDTO> findAll(Long empresaId) {
        return proprietarioRepository.findByEmpresaId(empresaId).stream()
                .map(proprietarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ProprietarioResponseDTO findById(Long id, Long empresaId) {
        Proprietario proprietario = getProprietario(id, empresaId);
        return proprietarioMapper.toResponseDTO(proprietario);
    }

    public ProprietarioResponseDTO findByCpfCnpj(String cpfCnpj, Long empresaId) {
        Proprietario proprietario = proprietarioRepository.findByCpfCnpjAndEmpresaId(cpfCnpj, empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Proprietário não encontrado com CPF/CNPJ: " + cpfCnpj));
        return proprietarioMapper.toResponseDTO(proprietario);
    }

    @Transactional
    public ProprietarioResponseDTO update(Long id, Long empresaId, ProprietarioRequestDTO requestDTO) {
        Proprietario proprietario = getProprietario(id, empresaId);

        if (!proprietario.getCpfCnpj().equals(requestDTO.getCpfCnpj())) {
            proprietarioRepository.findByCpfCnpjAndEmpresaId(requestDTO.getCpfCnpj(), empresaId)
                    .ifPresent(p -> { throw new BusinessException("Já existe outro proprietário com o CPF/CNPJ informado na empresa."); });
        }

        proprietarioMapper.updateEntityFromDTO(requestDTO, proprietario);
        Proprietario updated = proprietarioRepository.save(proprietario);
        return proprietarioMapper.toResponseDTO(updated);
    }

    @Transactional
    public void delete(Long id, Long empresaId) {
        Proprietario proprietario = getProprietario(id, empresaId);
        proprietario.setAtivo(false);
        proprietarioRepository.save(proprietario);
    }

    private Proprietario getProprietario(Long id, Long empresaId) {
        return proprietarioRepository.findByIdAndEmpresaId(id, empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Proprietário não encontrado com ID: " + id));
    }
}
