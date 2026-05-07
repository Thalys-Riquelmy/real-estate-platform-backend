package com.imobiliaria_api.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FiltroImovelDTO {
    private String status;
    private String tipo;
    private Integer quartos;
    private Integer banheiros;
    private Integer vagas;
    private String cidade;
    private String bairro;
    private BigDecimal valorVendaMin;
    private BigDecimal valorVendaMax;
    private BigDecimal valorAluguelMin;
    private BigDecimal valorAluguelMax;
    private String busca; 
}
