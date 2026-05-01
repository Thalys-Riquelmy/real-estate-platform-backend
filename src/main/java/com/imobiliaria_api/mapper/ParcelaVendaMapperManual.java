package com.imobiliaria_api.mapper;

import com.imobiliaria_api.dto.response.ParcelaVendaResponseDTO;
import com.imobiliaria_api.model.ParcelaVenda;
import org.springframework.stereotype.Component;

@Component
public class ParcelaVendaMapperManual {

    public ParcelaVendaResponseDTO toResponseDTO(ParcelaVenda entity) {
        if (entity == null) return null;
        
        ParcelaVendaResponseDTO dto = new ParcelaVendaResponseDTO();
        dto.setId(entity.getId());
        dto.setNumeroParcela(entity.getNumeroParcela());
        dto.setDataVencimento(entity.getDataVencimento());
        dto.setDataPagamento(entity.getDataPagamento());
        dto.setValorBase(entity.getValorBase());
        dto.setIgpmAplicado(entity.getIgpmAplicado());
        dto.setJurosValor(entity.getJurosValor());
        dto.setMultaValor(entity.getMultaValor());
        dto.setValorFinal(entity.getValorFinal());
        dto.setValorPago(entity.getValorPago());
        dto.setStatus(entity.getStatus());
        dto.setFormaPagamento(entity.getFormaPagamento());
        dto.setComprovanteUrl(entity.getComprovanteUrl());
        dto.setObservacao(entity.getObservacao());
        
        return dto;
    }
}