package com.imobiliaria_api.mapper;

import com.imobiliaria_api.dto.request.AluguelRequestDTO;
import com.imobiliaria_api.dto.response.AluguelResponseDTO;
import com.imobiliaria_api.model.Aluguel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EmpresaMapper.class, ImovelMapper.class, ClienteMapper.class, PrestacaoAluguelMapper.class})
public interface AluguelMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "imovel", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "contratoUrl", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Aluguel toEntity(AluguelRequestDTO dto);

    @Mapping(target = "prestacoes", ignore = true)
    AluguelResponseDTO toResponseDTO(Aluguel entity);
}
