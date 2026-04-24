package com.imobiliaria_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ConfiguracaoEmailRequestDTO {
    
    @NotBlank(message = "SMTP Host é obrigatório")
    private String smtpHost;
    
    @NotNull(message = "SMTP Port é obrigatório")
    @Positive(message = "Porta deve ser positiva")
    private Integer smtpPort;
    
    @NotBlank(message = "Email remetente é obrigatório")
    @Email(message = "Email inválido")
    private String emailRemetente;
    
    @NotBlank(message = "Nome remetente é obrigatório")
    private String nomeRemetente;
    
    @NotBlank(message = "Usuário é obrigatório")
    private String username;
    
    @NotBlank(message = "Senha é obrigatória")
    private String password;
    
    private Boolean auth = true;
    private Boolean starttls = true;
    private Integer timeout = 5000;
    private Integer connectionTimeout = 5000;
    private Boolean ativo = true;
}