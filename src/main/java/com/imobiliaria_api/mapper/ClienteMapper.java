package com.imobiliaria_api.mapper;

import com.imobiliaria_api.dto.request.ClienteRequestDTO;
import com.imobiliaria_api.dto.response.ClienteResponseDTO;
import com.imobiliaria_api.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {EmpresaMapper.class})
public interface ClienteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Cliente toEntity(ClienteRequestDTO dto);

    ClienteResponseDTO toResponseDTO(Cliente entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(ClienteRequestDTO dto, @MappingTarget Cliente entity);
}
