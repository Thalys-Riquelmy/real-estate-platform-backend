package com.imobiliaria_api.dto.response;

import lombok.Data;

@Data
public class ConfiguracaoEmailResponseDTO {
    private Long id;
    private String smtpHost;
    private Integer smtpPort;
    private String emailRemetente;
    private String nomeRemetente;
    private String username;
    private Boolean auth;
    private Boolean starttls;
    private Integer timeout;
    private Integer connectionTimeout;
    private Boolean ativo;
    private Boolean testeEnviado;
}