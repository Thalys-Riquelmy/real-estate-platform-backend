package com.imobiliaria_api.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ConfiguracaoFinanceiraResponseDTO {
    private Long id;
    private BigDecimal jurosMensal;
    private BigDecimal jurosDiario;
    private BigDecimal multaFixa;
    private Integer diasCarencia;
    private BigDecimal igpmAtual;
    private LocalDate igpmDataReferencia;
    private String formasPagamento;
    private String observacoes;
}