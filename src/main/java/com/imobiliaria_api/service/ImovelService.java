package com.imobiliaria_api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imobiliaria_api.dto.request.FiltroImovelDTO;
import com.imobiliaria_api.dto.request.ImovelRequestDTO;
import com.imobiliaria_api.dto.response.ImovelResponseDTO;
import com.imobiliaria_api.exception.BusinessException;
import com.imobiliaria_api.exception.ResourceNotFoundException;
import com.imobiliaria_api.mapper.ImovelMapper;
import com.imobiliaria_api.model.Empresa;
import com.imobiliaria_api.model.Imovel;
import com.imobiliaria_api.model.Proprietario;
import com.imobiliaria_api.repository.EmpresaRepository;
import com.imobiliaria_api.repository.ImovelRepository;
import com.imobiliaria_api.repository.ProprietarioRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImovelService {

    private final ImovelRepository imovelRepository;
    private final EmpresaRepository empresaRepository;
    private final ProprietarioRepository proprietarioRepository;
    private final ImovelMapper imovelMapper;
    private final ObjectMapper objectMapper;

    @Transactional
    public ImovelResponseDTO create(Long empresaId, ImovelRequestDTO requestDTO) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));

        Proprietario proprietario = proprietarioRepository.findByIdAndEmpresaId(requestDTO.getProprietarioId(), empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Proprietário não encontrado na empresa"));

        Imovel imovel = imovelMapper.toEntity(requestDTO);
        imovel.setEmpresa(empresa);
        imovel.setProprietario(proprietario);
        
        if (requestDTO.getStatus() == null) {
            imovel.setStatus("disponivel");
        }

        Imovel saved = imovelRepository.save(imovel);
        return imovelMapper.toResponseDTO(saved);
    }

    public ImovelResponseDTO findById(Long id, Long empresaId) {
        Imovel imovel = getImovel(id, empresaId);
        return imovelMapper.toResponseDTO(imovel);
    }

    public List<ImovelResponseDTO> findAll(Long empresaId, FiltroImovelDTO filtro) {
        Specification<Imovel> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("empresa").get("id"), empresaId));

            if (filtro.getStatus() != null) predicates.add(cb.equal(root.get("status"), filtro.getStatus()));
            if (filtro.getTipo() != null) predicates.add(cb.equal(root.get("tipo"), filtro.getTipo()));
            if (filtro.getQuartos() != null) predicates.add(cb.equal(root.get("quartos"), filtro.getQuartos()));
            if (filtro.getCidade() != null) predicates.add(cb.equal(root.get("cidade"), filtro.getCidade()));
            if (filtro.getBairro() != null) predicates.add(cb.equal(root.get("bairro"), filtro.getBairro()));

            if (filtro.getBusca() != null && !filtro.getBusca().isBlank()) {
                String likePattern = "%" + filtro.getBusca().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("endereco")), likePattern));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return imovelRepository.findAll(spec).stream()
                .map(imovelMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ImovelResponseDTO update(Long id, Long empresaId, ImovelRequestDTO requestDTO) {
        Imovel imovel = getImovel(id, empresaId);

        if (!imovel.getProprietario().getId().equals(requestDTO.getProprietarioId())) {
            Proprietario proprietario = proprietarioRepository.findByIdAndEmpresaId(requestDTO.getProprietarioId(), empresaId)
                    .orElseThrow(() -> new ResourceNotFoundException("Proprietário não encontrado"));
            imovel.setProprietario(proprietario);
        }

        imovelMapper.updateEntityFromDTO(requestDTO, imovel);
        Imovel updated = imovelRepository.save(imovel);
        return imovelMapper.toResponseDTO(updated);
    }

    @Transactional
    public void updateStatus(Long id, Long empresaId, String status) {
        Imovel imovel = getImovel(id, empresaId);
        imovel.setStatus(status);
        imovelRepository.save(imovel);
    }

    @Transactional
    public void delete(Long id, Long empresaId) {
        Imovel imovel = getImovel(id, empresaId);
        imovelRepository.delete(imovel);
    }

    @Transactional
    public ImovelResponseDTO addFoto(Long id, Long empresaId, String baseUrl) {
        Imovel imovel = getImovel(id, empresaId);
        // Simulando list de URLs
        List<String> fotosList = parseFotos(imovel.getFotos());
        fotosList.add(baseUrl);
        imovel.setFotos(toJson(fotosList));
        Imovel saved = imovelRepository.save(imovel);
        return imovelMapper.toResponseDTO(saved);
    }

    private Imovel getImovel(Long id, Long empresaId) {
        return imovelRepository.findByIdAndEmpresaId(id, empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Imóvel não encontrado"));
    }

    private List<String> parseFotos(String json) {
        if (!StringUtils.hasText(json)) return new ArrayList<>();
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
        } catch (JsonProcessingException e) {
            return new ArrayList<>();
        }
    }

    private String toJson(List<String> fotos) {
        try {
            return objectMapper.writeValueAsString(fotos);
        } catch (JsonProcessingException e) {
            return "[]";
        }
    }
}
