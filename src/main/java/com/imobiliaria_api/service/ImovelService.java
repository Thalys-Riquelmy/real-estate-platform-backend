package com.imobiliaria_api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imobiliaria_api.dto.request.FiltroImovelDTO;
import com.imobiliaria_api.dto.request.ImovelRequestDTO;
import com.imobiliaria_api.dto.response.ImagemImovelResponseDTO;
import com.imobiliaria_api.dto.response.ImovelResponseDTO;
import com.imobiliaria_api.exception.BusinessException;
import com.imobiliaria_api.exception.ResourceNotFoundException;
import com.imobiliaria_api.mapper.ImovelMapperManual;
import com.imobiliaria_api.model.Empresa;
import com.imobiliaria_api.model.ImagemImovel;
import com.imobiliaria_api.model.Imovel;
import com.imobiliaria_api.model.Proprietario;
import com.imobiliaria_api.repository.EmpresaRepository;
import com.imobiliaria_api.repository.ImagemImovelRepository;
import com.imobiliaria_api.repository.ImovelRepository;
import com.imobiliaria_api.repository.ProprietarioRepository;
import com.imobiliaria_api.util.ImageUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImovelService {

    private final ImovelRepository imovelRepository;
    private final ImagemImovelRepository imagemImovelRepository;
    private final EmpresaRepository empresaRepository;
    private final ProprietarioRepository proprietarioRepository;
    private final ImovelMapperManual imovelMapper;
    private final ObjectMapper objectMapper;

    // Constantes para compressão
    private static final int MAX_WIDTH = 1200;
    private static final int MAX_HEIGHT = 800;
    private static final float QUALITY = 0.8f; // 80% qualidade

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

    @Transactional
    public ImovelResponseDTO createWithImages(Long empresaId, ImovelRequestDTO requestDTO, MultipartFile[] imagens) {
        // Primeiro salva o imóvel sem as imagens
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
        
        // Processa e salva as imagens
        if (imagens != null && imagens.length > 0) {
            salvarImagens(saved.getId(), imagens);
        }
        
        return imovelMapper.toResponseDTO(saved);
    }

    @Transactional
    public List<ImagemImovelResponseDTO> salvarImagens(Long imovelId, MultipartFile[] imagens) {
        Imovel imovel = getImovel(imovelId);
        
        // Remove imagens antigas se existirem
        imagemImovelRepository.deleteByImovelId(imovelId);
        
        List<ImagemImovel> imagensSalvas = new ArrayList<>();
        int ordem = 0;
        
        for (MultipartFile imagem : imagens) {
            try {
                // Compacta a imagem
                byte[] imagemCompactada = ImageUtil.compressImageToBytes(
                    imagem, MAX_WIDTH, MAX_HEIGHT, QUALITY
                );
                
                ImagemImovel img = new ImagemImovel();
                img.setImovel(imovel);
                img.setImagemCompactada(imagemCompactada);
                img.setContentType(imagem.getContentType());
                img.setOrdem(ordem);
                img.setPrincipal(ordem == 0); // Primeira imagem é a principal
                
                imagensSalvas.add(imagemImovelRepository.save(img));
                ordem++;
                
            } catch (IOException e) {
                throw new BusinessException("Erro ao processar imagem: " + e.getMessage());
            }
        }
        
        return imagensSalvas.stream()
                .map(this::toImagemResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ImagemImovelResponseDTO adicionarImagem(Long imovelId, MultipartFile imagem, Boolean isPrincipal) {
        Imovel imovel = getImovel(imovelId);
        
        try {
            byte[] imagemCompactada = ImageUtil.compressImageToBytes(
                imagem, MAX_WIDTH, MAX_HEIGHT, QUALITY
            );
            
            // Se for principal, remove o principal das outras
            if (isPrincipal != null && isPrincipal) {
                imagemImovelRepository.findByImovelIdOrderByOrdemAsc(imovelId)
                    .forEach(img -> {
                        img.setPrincipal(false);
                        imagemImovelRepository.save(img);
                    });
            }
            
            List<ImagemImovel> imagensExistentes = imagemImovelRepository.findByImovelIdOrderByOrdemAsc(imovelId);
            int novaOrdem = imagensExistentes.size();
            
            ImagemImovel novaImagem = new ImagemImovel();
            novaImagem.setImovel(imovel);
            novaImagem.setImagemCompactada(imagemCompactada);
            novaImagem.setContentType(imagem.getContentType());
            novaImagem.setOrdem(novaOrdem);
            novaImagem.setPrincipal(isPrincipal != null && isPrincipal);
            
            ImagemImovel saved = imagemImovelRepository.save(novaImagem);
            return toImagemResponseDTO(saved);
            
        } catch (IOException e) {
            throw new BusinessException("Erro ao processar imagem: " + e.getMessage());
        }
    }

    @Transactional
    public void removerImagem(Long imovelId, Long imagemId) {
        ImagemImovel imagem = imagemImovelRepository.findById(imagemId)
                .orElseThrow(() -> new ResourceNotFoundException("Imagem não encontrada"));
        
        if (!imagem.getImovel().getId().equals(imovelId)) {
            throw new BusinessException("Imagem não pertence ao imóvel informado");
        }
        
        imagemImovelRepository.delete(imagem);
    }

    @Transactional
    public void definirImagemPrincipal(Long imovelId, Long imagemId) {
        // Remove principal de todas as imagens
        imagemImovelRepository.findByImovelIdOrderByOrdemAsc(imovelId)
            .forEach(img -> {
                img.setPrincipal(false);
                imagemImovelRepository.save(img);
            });
        
        // Define a nova imagem como principal
        ImagemImovel imagem = imagemImovelRepository.findById(imagemId)
                .orElseThrow(() -> new ResourceNotFoundException("Imagem não encontrada"));
        imagem.setPrincipal(true);
        imagemImovelRepository.save(imagem);
    }

    public List<ImagemImovelResponseDTO> listarImagens(Long imovelId) {
        return imagemImovelRepository.findByImovelIdOrderByOrdemAsc(imovelId).stream()
                .map(this::toImagemResponseDTO)
                .collect(Collectors.toList());
    }

    public byte[] obterImagemCompactada(Long imagemId) {
        ImagemImovel imagem = imagemImovelRepository.findById(imagemId)
                .orElseThrow(() -> new ResourceNotFoundException("Imagem não encontrada"));
        return imagem.getImagemCompactada();
    }

    public ImovelResponseDTO findById(Long id, Long empresaId) {
        Imovel imovel = getImovel(id, empresaId);
        ImovelResponseDTO response = imovelMapper.toResponseDTO(imovel);
        
        // Carrega as imagens
        List<ImagemImovelResponseDTO> imagens = listarImagens(id);
        response.setImagens(imagens);
        
        return response;
    }

    public List<ImovelResponseDTO> findAll(Long empresaId, FiltroImovelDTO filtro) {
        Specification<Imovel> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("empresa").get("id"), empresaId));

            if (filtro.getStatus() != null) predicates.add(cb.equal(root.get("status"), filtro.getStatus()));
            if (filtro.getTipo() != null) predicates.add(cb.equal(root.get("tipo"), filtro.getTipo()));
            if (filtro.getQuartos() != null) predicates.add(cb.equal(root.get("quartos"), filtro.getQuartos()));
            if (filtro.getBanheiros() != null) predicates.add(cb.equal(root.get("banheiros"), filtro.getBanheiros()));
            if (filtro.getVagas() != null) predicates.add(cb.equal(root.get("vagas"), filtro.getVagas()));
            if (filtro.getCidade() != null) predicates.add(cb.equal(root.get("cidade"), filtro.getCidade()));
            if (filtro.getBairro() != null) predicates.add(cb.equal(root.get("bairro"), filtro.getBairro()));

            if (filtro.getValorVendaMin() != null) 
                predicates.add(cb.greaterThanOrEqualTo(root.get("valorVenda"), filtro.getValorVendaMin()));
            if (filtro.getValorVendaMax() != null) 
                predicates.add(cb.lessThanOrEqualTo(root.get("valorVenda"), filtro.getValorVendaMax()));
            if (filtro.getValorAluguelMin() != null) 
                predicates.add(cb.greaterThanOrEqualTo(root.get("valorAluguel"), filtro.getValorAluguelMin()));
            if (filtro.getValorAluguelMax() != null) 
                predicates.add(cb.lessThanOrEqualTo(root.get("valorAluguel"), filtro.getValorAluguelMax()));

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
        // Remove as imagens primeiro
        imagemImovelRepository.deleteByImovelId(id);
        imovelRepository.delete(imovel);
    }

    private Imovel getImovel(Long id, Long empresaId) {
        return imovelRepository.findByIdAndEmpresaId(id, empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Imóvel não encontrado"));
    }

    private Imovel getImovel(Long id) {
        return imovelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Imóvel não encontrado"));
    }

    private ImagemImovelResponseDTO toImagemResponseDTO(ImagemImovel entity) {
        ImagemImovelResponseDTO dto = new ImagemImovelResponseDTO();
        dto.setId(entity.getId());
        dto.setOrdem(entity.getOrdem());
        dto.setPrincipal(entity.getPrincipal());
        dto.setContentType(entity.getContentType());
        
        // Converte a imagem compactada para Base64 para envio no JSON
        if (entity.getImagemCompactada() != null) {
            String base64Image = Base64.getEncoder().encodeToString(entity.getImagemCompactada());
            dto.setImagemBase64(base64Image);
        }
        
        return dto;
    }
}