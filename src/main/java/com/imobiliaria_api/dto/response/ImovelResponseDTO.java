package com.imobiliaria_api.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ImovelResponseDTO {
    private Long id;
    private String tipo;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private BigDecimal areaTerreno;
    private BigDecimal areaConstruida;
    private Integer quartos;
    private Integer banheiros;
    private Integer vagas;
    private Integer andar;
    private BigDecimal valorVenda;
    private BigDecimal valorAluguel;
    private String status;
    private List<ImagemImovelResponseDTO> imagens;
    private Boolean destaque;
    private String observacoes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private EmpresaResponseDTO empresa;
    private ProprietarioResponseDTO proprietario;
}
