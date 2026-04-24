package com.imobiliaria_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "configuracoes_email")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfiguracaoEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @Column(name = "smtp_host", nullable = false, length = 100)
    private String smtpHost;

    @Column(name = "smtp_port", nullable = false)
    private Integer smtpPort;

    @Column(name = "email_remetente", nullable = false, length = 100)
    private String emailRemetente;

    @Column(name = "nome_remetente", nullable = false, length = 100)
    private String nomeRemetente;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false)
    private String password;

    private Boolean auth = true;
    private Boolean starttls = true;
    private Integer timeout = 5000;
    private Integer connectionTimeout = 5000;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "teste_enviado")
    private Boolean testeEnviado = false;
}