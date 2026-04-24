package com.imobiliaria_api.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ConfiguracaoFinanceiraRequestDTO {
    
    private BigDecimal jurosMensal = new BigDecimal("1.5");
    private BigDecimal jurosDiario = new BigDecimal("0.05");
    private BigDecimal multaFixa = new BigDecimal("2.0");
    private Integer diasCarencia = 0;
    private BigDecimal igpmAtual = new BigDecimal("5.0");
    private LocalDate igpmDataReferencia;
    private String formasPagamento;
    private String observacoes;
}