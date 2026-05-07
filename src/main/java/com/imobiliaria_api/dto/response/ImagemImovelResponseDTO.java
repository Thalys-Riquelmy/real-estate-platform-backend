package com.imobiliaria_api.dto.response;

import lombok.Data;

@Data
public class ImagemImovelResponseDTO {
    private Long id;
    private Integer ordem;
    private Boolean principal;
    private String contentType;
    private String imagemBase64; // Imagem em Base64 para envio ao frontend
}