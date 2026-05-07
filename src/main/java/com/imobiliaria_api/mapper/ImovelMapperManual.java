package com.imobiliaria_api.mapper;

import com.imobiliaria_api.dto.request.ImovelRequestDTO;
import com.imobiliaria_api.dto.response.ImovelResponseDTO;
import com.imobiliaria_api.model.Imovel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImovelMapperManual {

    private final EmpresaMapper empresaMapper;
    private final ProprietarioMapperManual proprietarioMapper;

    public Imovel toEntity(ImovelRequestDTO dto) {
        if (dto == null) return null;
        
        Imovel imovel = new Imovel();
        imovel.setTipo(dto.getTipo());
        imovel.setStatus(dto.getStatus());
        imovel.setValorVenda(dto.getValorVenda());
        imovel.setValorAluguel(dto.getValorAluguel());
        imovel.setAreaConstruida(dto.getAreaConstruida());
        imovel.setAreaTerreno(dto.getAreaTerreno());
        imovel.setQuartos(dto.getQuartos());
        imovel.setBanheiros(dto.getBanheiros());
        imovel.setVagas(dto.getVagas());
        imovel.setAndar(dto.getAndar());
        
        // Endereço
        imovel.setEndereco(dto.getEndereco());
        imovel.setNumero(dto.getNumero());
        imovel.setComplemento(dto.getComplemento());
        imovel.setBairro(dto.getBairro());
        imovel.setCep(dto.getCep());
        imovel.setCidade(dto.getCidade());
        imovel.setEstado(dto.getEstado());
        
        imovel.setObservacoes(dto.getObservacoes());
        imovel.setDestaque(dto.getDestaque() != null ? dto.getDestaque() : false);
        
        return imovel;
    }

    public ImovelResponseDTO toResponseDTO(Imovel entity) {
        if (entity == null) return null;
        
        ImovelResponseDTO dto = new ImovelResponseDTO();
        dto.setId(entity.getId());
        dto.setTipo(entity.getTipo());
        dto.setStatus(entity.getStatus());
        dto.setValorVenda(entity.getValorVenda());
        dto.setValorAluguel(entity.getValorAluguel());
        dto.setAreaConstruida(entity.getAreaConstruida());
        dto.setAreaTerreno(entity.getAreaTerreno());
        dto.setQuartos(entity.getQuartos());
        dto.setBanheiros(entity.getBanheiros());
        dto.setVagas(entity.getVagas());
        dto.setAndar(entity.getAndar());
        
        // Endereço
        dto.setEndereco(entity.getEndereco());
        dto.setNumero(entity.getNumero());
        dto.setComplemento(entity.getComplemento());
        dto.setBairro(entity.getBairro());
        dto.setCep(entity.getCep());
        dto.setCidade(entity.getCidade());
        dto.setEstado(entity.getEstado());
        
        dto.setObservacoes(entity.getObservacoes());
        dto.setDestaque(entity.getDestaque());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        
        if (entity.getEmpresa() != null) {
            dto.setEmpresa(empresaMapper.toResponseDTO(entity.getEmpresa()));
        }
        if (entity.getProprietario() != null) {
            dto.setProprietario(proprietarioMapper.toResponseDTO(entity.getProprietario()));
        }
               
        return dto;
    }

    public void updateEntityFromDTO(ImovelRequestDTO dto, Imovel entity) {
        if (dto == null || entity == null) return;
        
        if (dto.getTipo() != null) entity.setTipo(dto.getTipo());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
        if (dto.getValorVenda() != null) entity.setValorVenda(dto.getValorVenda());
        if (dto.getValorAluguel() != null) entity.setValorAluguel(dto.getValorAluguel());
        if (dto.getAreaConstruida() != null) entity.setAreaConstruida(dto.getAreaConstruida());
        if (dto.getAreaTerreno() != null) entity.setAreaTerreno(dto.getAreaTerreno());
        if (dto.getQuartos() != null) entity.setQuartos(dto.getQuartos());
        if (dto.getBanheiros() != null) entity.setBanheiros(dto.getBanheiros());
        if (dto.getVagas() != null) entity.setVagas(dto.getVagas());
        if (dto.getAndar() != null) entity.setAndar(dto.getAndar());
        if (dto.getEndereco() != null) entity.setEndereco(dto.getEndereco());
        if (dto.getNumero() != null) entity.setNumero(dto.getNumero());
        if (dto.getComplemento() != null) entity.setComplemento(dto.getComplemento());
        if (dto.getBairro() != null) entity.setBairro(dto.getBairro());
        if (dto.getCep() != null) entity.setCep(dto.getCep());
        if (dto.getCidade() != null) entity.setCidade(dto.getCidade());
        if (dto.getEstado() != null) entity.setEstado(dto.getEstado());
        if (dto.getObservacoes() != null) entity.setObservacoes(dto.getObservacoes());
        if (dto.getDestaque() != null) entity.setDestaque(dto.getDestaque());
    }
}