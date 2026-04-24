package com.imobiliaria_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmpresaRequestDTO {
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;
    
    @Size(max = 18, message = "CNPJ deve ter no máximo 18 caracteres")
    private String cnpj;
    
    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String telefone;
    
    @Email(message = "Email inválido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String email;
    
    private String endereco;
    
    @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
    private String cidade;
    
    @Size(min = 2, max = 2, message = "Estado deve ter 2 caracteres")
    private String estado;
    
    private String logoUrl;
}