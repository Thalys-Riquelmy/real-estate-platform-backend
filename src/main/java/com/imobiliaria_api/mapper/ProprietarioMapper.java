package com.imobiliaria_api.mapper;

import com.imobiliaria_api.dto.request.ProprietarioRequestDTO;
import com.imobiliaria_api.dto.response.ProprietarioResponseDTO;
import com.imobiliaria_api.model.Proprietario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {EmpresaMapper.class})
public interface ProprietarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "ativo", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Proprietario toEntity(ProprietarioRequestDTO dto);

    ProprietarioResponseDTO toResponseDTO(Proprietario entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(ProprietarioRequestDTO dto, @MappingTarget Proprietario entity);
}
