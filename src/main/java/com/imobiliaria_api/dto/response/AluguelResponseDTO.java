package com.imobiliaria_api.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AluguelResponseDTO {
    private Long id;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private BigDecimal valorAluguel;
    private Integer diaVencimento;
    private String status;
    private String contratoUrl;
    private LocalDateTime createdAt;
    
    private EmpresaResponseDTO empresa;
    private ImovelResponseDTO imovel;
    private ClienteResponseDTO cliente;
    private List<PrestacaoAluguelResponseDTO> prestacoes;
}
