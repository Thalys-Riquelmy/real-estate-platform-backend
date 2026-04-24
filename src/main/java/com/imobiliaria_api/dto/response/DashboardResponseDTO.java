package com.imobiliaria_api.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class DashboardResponseDTO {
    private long totalImoveisDisponiveis;
    private long totalImoveisAlugados;
    private long totalImoveisVendidos;
    private long contratosAtivosVenda;
    private long contratosAtivosAluguel;
    private BigDecimal faturamentoPrevistoMes;
    private BigDecimal faturamentoRealizadoMes;
    private long inadimplencia; // quantidade de parcelas atrasadas
}
