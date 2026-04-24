package com.imobiliaria_api.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EmpresaResponseDTO {
    private Long id;
    private String nome;
    private String cnpj;
    private String telefone;
    private String email;
    private String endereco;
    private String cidade;
    private String estado;
    private String logoUrl;
    private Boolean ativo;
    private LocalDateTime dataCadastro;
}