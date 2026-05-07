package com.imobiliaria_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "imoveis")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Imovel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proprietario_id", nullable = false)
    private Proprietario proprietario;
    
    @Column(nullable = false, length = 30)
    private String tipo; // casa, apartamento, terreno, comercial
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String endereco;
    
    @Column(length = 10)
    private String numero;
    
    @Column(length = 50)
    private String complemento;
    
    @Column(length = 100)
    private String bairro;
    
    @Column(length = 100)
    private String cidade;
    
    @Column(length = 2)
    private String estado;
    
    @Column(length = 10)
    private String cep;
    
    private BigDecimal areaTerreno;
    private BigDecimal areaConstruida;
    private Integer quartos;
    private Integer banheiros;
    private Integer vagas;
    private Integer andar;
    
    private BigDecimal valorVenda;
    private BigDecimal valorAluguel;
    
    @Column(length = 20)
    private String status = "disponivel"; // disponivel, vendido, alugado, reservado, manutencao
    
    @OneToMany(mappedBy = "imovel", cascade = CascadeType.ALL)
    private List<ImagemImovel> imagens;
    
    private Boolean destaque = false;
    
    @Column(columnDefinition = "TEXT")
    private String observacoes;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
