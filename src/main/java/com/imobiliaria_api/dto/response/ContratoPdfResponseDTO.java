package com.imobiliaria_api.dto.response;

import lombok.Data;

@Data
public class ContratoPdfResponseDTO {
    private Long id;
    private String nomeArquivo;
    private String contentType;
    private String pdfBase64;
    private Long tamanho;
}