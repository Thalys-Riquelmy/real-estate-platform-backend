package com.imobiliaria_api.mapper;

import com.imobiliaria_api.dto.request.ImovelRequestDTO;
import com.imobiliaria_api.dto.response.ImovelResponseDTO;
import com.imobiliaria_api.model.Imovel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {EmpresaMapper.class, ProprietarioMapper.class})
public interface ImovelMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "proprietario", ignore = true)
    @Mapping(target = "fotos", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Imovel toEntity(ImovelRequestDTO dto);

    ImovelResponseDTO toResponseDTO(Imovel entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "proprietario", ignore = true)
    @Mapping(target = "fotos", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(ImovelRequestDTO dto, @MappingTarget Imovel entity);
}
