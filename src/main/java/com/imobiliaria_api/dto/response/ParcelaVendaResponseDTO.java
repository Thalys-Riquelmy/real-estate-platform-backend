package com.imobiliaria_api.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ParcelaVendaResponseDTO {
    private Long id;
    private Integer numeroParcela;
    private LocalDate dataVencimento;
    private BigDecimal valorBase;
    private BigDecimal igpmAplicado;
    private BigDecimal valorFinal;
    private String status;
    private LocalDate dataPagamento;
    private BigDecimal valorPago;
    private BigDecimal multaValor;
    private BigDecimal jurosValor;
    private String formaPagamento;
    private String comprovanteUrl;
    private String observacao;
}
