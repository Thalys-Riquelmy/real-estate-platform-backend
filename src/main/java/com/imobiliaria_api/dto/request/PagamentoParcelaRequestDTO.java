package com.imobiliaria_api.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PagamentoParcelaRequestDTO {
    private LocalDate dataPagamento;
    private String formaPagamento;
    private String observacao;
}
