package com.imobiliaria_api.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VendaResponseDTO {
    private Long id;
    private LocalDate dataContrato;
    private BigDecimal valorImovel;
    private BigDecimal valorEntrada;
    private BigDecimal saldoFinanciado;
    private Integer quantidadeParcelas;
    private Integer diaVencimento;
    private Boolean aplicaIgpm;
    private BigDecimal igpmContrato;
    private String status;
    private String contratoUrl;
    private LocalDateTime createdAt;
    
    private EmpresaResponseDTO empresa;
    private ImovelResponseDTO imovel;
    private ClienteResponseDTO cliente;
    private List<ParcelaVendaResponseDTO> parcelas;
}
