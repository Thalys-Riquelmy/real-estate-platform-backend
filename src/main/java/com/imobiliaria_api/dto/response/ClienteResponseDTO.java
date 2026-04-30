package com.imobiliaria_api.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ClienteResponseDTO {
    private Long id;
    private String nome;
    private String cpfCnpj;
    private String telefone;
    private String email;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String cidade;
    private String estado;
    private String tipo;
    private String observacoes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private EmpresaResponseDTO empresa;
}
