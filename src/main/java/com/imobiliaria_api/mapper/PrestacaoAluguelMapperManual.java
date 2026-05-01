package com.imobiliaria_api.mapper;

import com.imobiliaria_api.dto.response.PrestacaoAluguelResponseDTO;
import com.imobiliaria_api.model.PrestacaoAluguel;
import org.springframework.stereotype.Component;

@Component
public class PrestacaoAluguelMapperManual {

    public PrestacaoAluguelResponseDTO toResponseDTO(PrestacaoAluguel entity) {
        if (entity == null) return null;
        
        PrestacaoAluguelResponseDTO dto = new PrestacaoAluguelResponseDTO();
        dto.setId(entity.getId());
        dto.setDataVencimento(entity.getDataVencimento());
        dto.setDataPagamento(entity.getDataPagamento());
        dto.setValor(entity.getValor());
        dto.setJurosValor(entity.getJurosValor());
        dto.setMultaValor(entity.getMultaValor());
        dto.setValorPago(entity.getValorPago());
        dto.setStatus(entity.getStatus());
        dto.setFormaPagamento(entity.getFormaPagamento());
        dto.setComprovanteUrl(entity.getComprovanteUrl());
        dto.setObservacao(entity.getObservacao());
        
        return dto;
    }
}