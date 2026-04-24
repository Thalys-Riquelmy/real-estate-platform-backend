package com.imobiliaria_api.mapper;

import com.imobiliaria_api.dto.response.PrestacaoAluguelResponseDTO;
import com.imobiliaria_api.model.PrestacaoAluguel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrestacaoAluguelMapper {

    PrestacaoAluguelResponseDTO toResponseDTO(PrestacaoAluguel entity);
}
