package com.imobiliaria_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "prestacoes_aluguel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestacaoAluguel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluguel_id", nullable = false)
    private Aluguel aluguel;
    
    private LocalDate dataVencimento;
    private BigDecimal valor;
    
    private String status = "pendente"; // pendente, pago, atrasado
    private LocalDate dataPagamento;
    private BigDecimal valorPago;
    private BigDecimal multaValor;
    private BigDecimal jurosValor;
    private String formaPagamento;
    private String comprovanteUrl;
    private String observacao;
    
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
