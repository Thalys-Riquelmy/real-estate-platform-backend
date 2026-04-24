package com.imobiliaria_api.mapper;

import com.imobiliaria_api.dto.request.VendaRequestDTO;
import com.imobiliaria_api.dto.response.VendaResponseDTO;
import com.imobiliaria_api.model.Venda;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EmpresaMapper.class, ImovelMapper.class, ClienteMapper.class, ParcelaVendaMapper.class})
public interface VendaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "imovel", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "saldoFinanciado", ignore = true)
    @Mapping(target = "igpmContrato", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "contratoUrl", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Venda toEntity(VendaRequestDTO dto);

    @Mapping(target = "parcelas", ignore = true)
    VendaResponseDTO toResponseDTO(Venda entity);
}
