package com.imobiliaria_api.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class CarneResponseDTO {
    private String titulo;
    private Long contratoId;
    private String nomeCliente;
    private String cpfCnpjCliente;
    private List<BoletoCarneDTO> boletos;
    
    @Data
    @Builder
    public static class BoletoCarneDTO {
        private Integer numeroParcela;
        private LocalDate dataVencimento;
        private BigDecimal valorFinal;
        private String codigoBarras;
        private String instrucoes;
    }
}
