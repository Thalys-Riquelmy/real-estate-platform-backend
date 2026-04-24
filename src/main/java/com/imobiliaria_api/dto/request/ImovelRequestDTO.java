package com.imobiliaria_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ImovelRequestDTO {
    
    @NotNull(message = "O ID do proprietário é obrigatório")
    private Long proprietarioId;
    
    @NotBlank(message = "O tipo do imóvel é obrigatório")
    private String tipo;
    
    @NotBlank(message = "O endereço é obrigatório")
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
    private Boolean destaque;
    private String observacoes;
}
