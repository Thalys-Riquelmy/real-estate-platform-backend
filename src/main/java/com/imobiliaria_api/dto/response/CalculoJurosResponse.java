package com.imobiliaria_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculoJurosResponse {
    private BigDecimal valorTotal;
    private BigDecimal multa;
    private BigDecimal juros;
    private long diasAtraso;
}
