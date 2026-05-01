package com.imobiliaria_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "DTO para criação/atualização de Proprietário")
public class ProprietarioRequestDTO {
    
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    @Schema(description = "Nome do proprietário", example = "João da Silva")
    private String nome;
    
    @NotBlank(message = "O CPF/CNPJ é obrigatório")
    @Size(max = 20, message = "O CPF/CNPJ deve ter no máximo 20 caracteres")
    @Schema(description = "CPF ou CNPJ", example = "123.456.789-00")
    private String cpfCnpj;
    
    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    @Schema(description = "Telefone para contato", example = "(11) 99999-8888")
    private String telefone;
    
    @Email(message = "O formato de e-mail é inválido")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres")
    @Schema(description = "E-mail de contato", example = "joao@email.com")
    private String email;
    
    @Schema(description = "Endereço completo", example = "Rua A, 123 - São Paulo - SP")
    private String endereco;
    
    @Size(max = 10, message = "O número deve ter no máximo 10 caracteres")
    @Schema(description = "Número do imóvel", example = "123")
    private String numero;
    
    @Size(max = 100, message = "O complemento deve ter no máximo 100 caracteres")
    @Schema(description = "Complemento do endereço", example = "Apto 45")
    private String complemento;
    
    @Size(max = 100, message = "O bairro deve ter no máximo 100 caracteres")
    @Schema(description = "Bairro", example = "Centro")
    private String bairro;
    
    @Size(max = 20, message = "O CEP deve ter no máximo 20 caracteres")
    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "Formato de CEP inválido")
    @Schema(description = "CEP", example = "12345-678")
    private String cep;
    
    @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres")
    @Schema(description = "Cidade", example = "São Paulo")
    private String cidade;
    
    @Size(min = 2, max = 2, message = "O estado deve ter 2 caracteres")
    @Schema(description = "Estado (UF)", example = "SP")
    private String estado;
    
    @Schema(description = "Banco para depósito/transferência", example = "Banco do Brasil")
    private String banco;
    
    @Schema(description = "Agência bancária", example = "1234-5")
    private String agencia;
    
    @Schema(description = "Conta bancária", example = "12345-6")
    private String conta;
}
