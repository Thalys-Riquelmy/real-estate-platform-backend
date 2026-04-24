package com.imobiliaria_api.service;

import com.imobiliaria_api.dto.response.CalculoJurosResponse;
import com.imobiliaria_api.model.ConfiguracaoFinanceira;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class CalculoJurosService {
    
    public CalculoJurosResponse calcularJuros(
        BigDecimal valorOriginal,
        LocalDate dataVencimento,
        LocalDate dataPagamento,
        ConfiguracaoFinanceira config
    ) {
        long diasAtraso = ChronoUnit.DAYS.between(dataVencimento, dataPagamento);
        
        if (diasAtraso <= config.getDiasCarencia()) {
            return new CalculoJurosResponse(valorOriginal, BigDecimal.ZERO, BigDecimal.ZERO, 0);
        }
        
        BigDecimal percentMulta = config.getMultaFixa() != null ? config.getMultaFixa() : BigDecimal.ZERO;
        BigDecimal percentJuros = config.getJurosDiario() != null ? config.getJurosDiario() : BigDecimal.ZERO;
        
        BigDecimal multa = valorOriginal.multiply(percentMulta.divide(new BigDecimal("100"), 4, java.math.RoundingMode.HALF_UP));
        BigDecimal jurosDiarios = valorOriginal.multiply(percentJuros.divide(new BigDecimal("100"), 6, java.math.RoundingMode.HALF_UP));
        BigDecimal juros = jurosDiarios.multiply(new BigDecimal(diasAtraso));
        
        BigDecimal valorTotal = valorOriginal.add(multa).add(juros);
        
        return new CalculoJurosResponse(valorTotal, multa, juros, diasAtraso);
    }
}
