package com.imobiliaria_api.mapper;

import com.imobiliaria_api.dto.response.ParcelaVendaResponseDTO;
import com.imobiliaria_api.model.ParcelaVenda;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParcelaVendaMapper {

    ParcelaVendaResponseDTO toResponseDTO(ParcelaVenda entity);
}
