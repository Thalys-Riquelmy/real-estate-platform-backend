package com.imobiliaria_api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class VendaRequestDTO {
    @NotNull(message = "O imóvel é obrigatório")
    private Long imovelId;
    
    @NotNull(message = "O cliente é obrigatório")
    private Long clienteId;
    
    @NotNull(message = "A data do contrato é obrigatória")
    private LocalDate dataContrato;
    
    @NotNull(message = "O valor do imóvel é obrigatório")
    private BigDecimal valorImovel;
    
    @NotNull(message = "O valor de entrada é obrigatório")
    private BigDecimal valorEntrada;
    
    @NotNull(message = "A quantidade de parcelas é obrigatória")
    private Integer quantidadeParcelas;
    
    @NotNull(message = "O dia de vencimento é obrigatório")
    private Integer diaVencimento;
    
    private Boolean aplicaIgpm;
}
