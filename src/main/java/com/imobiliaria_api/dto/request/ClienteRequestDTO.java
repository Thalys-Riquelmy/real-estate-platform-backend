package com.imobiliaria_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "DTO para criação/atualização de Cliente")
public class ClienteRequestDTO {
    
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    @Schema(description = "Nome do cliente", example = "Maria Oliveira")
    private String nome;
    
    @NotBlank(message = "O CPF/CNPJ é obrigatório")
    @Size(max = 20, message = "O CPF/CNPJ deve ter no máximo 20 caracteres")
    @Schema(description = "CPF ou CNPJ", example = "987.654.321-00")
    private String cpfCnpj;
    
    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    @Schema(description = "Telefone para contato", example = "(11) 97777-6666")
    private String telefone;
    
    @Email(message = "O formato de e-mail é inválido")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres")
    @Schema(description = "E-mail de contato", example = "maria@email.com")
    private String email;
    
    @Schema(description = "Endereço completo", example = "Rua B, 456 - São Paulo - SP")
    private String endereco;
    
    @NotBlank(message = "O tipo de cliente é obrigatório")
    @Pattern(regexp = "^(comprador|locatario|ambos)$", message = "O tipo deve ser: comprador, locatario ou ambos")
    @Schema(description = "Tipo do cliente", example = "ambos", allowableValues = {"comprador", "locatario", "ambos"})
    private String tipo;
    
    @Schema(description = "Observações", example = "Cliente preferencial")
    private String observacoes;
}
