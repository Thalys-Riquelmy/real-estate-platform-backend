package com.imobiliaria_api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AluguelRequestDTO {
    @NotNull(message = "O imóvel é obrigatório")
    private Long imovelId;
    
    @NotNull(message = "O cliente é obrigatório")
    private Long clienteId;
    
    @NotNull(message = "A data de início é obrigatória")
    private LocalDate dataInicio;
    
    private LocalDate dataFim;
    
    @NotNull(message = "O valor do aluguel é obrigatório")
    private BigDecimal valorAluguel;
    
    @NotNull(message = "O dia de vencimento é obrigatório")
    private Integer diaVencimento;
}
