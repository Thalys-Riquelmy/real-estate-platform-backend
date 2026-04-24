package com.imobiliaria_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "configuracoes_financeiras")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfiguracaoFinanceira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @Column(name = "juros_mensal", precision = 5, scale = 2)
    private BigDecimal jurosMensal = new BigDecimal("1.5");

    @Column(name = "juros_diario", precision = 5, scale = 4)
    private BigDecimal jurosDiario = new BigDecimal("0.05");

    @Column(name = "multa_fixa", precision = 5, scale = 2)
    private BigDecimal multaFixa = new BigDecimal("2.0");

    @Column(name = "dias_carencia")
    private Integer diasCarencia = 0;

    @Column(name = "igpm_atual", precision = 5, scale = 2)
    private BigDecimal igpmAtual = new BigDecimal("5.0");

    @Column(name = "igpm_data_referencia")
    private LocalDate igpmDataReferencia;

    @Column(columnDefinition = "TEXT")
    private String formasPagamento;

    private String observacoes;
}