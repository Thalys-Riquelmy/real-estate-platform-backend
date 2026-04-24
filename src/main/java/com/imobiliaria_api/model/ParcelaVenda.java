package com.imobiliaria_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "parcelas_venda")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcelaVenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venda_id", nullable = false)
    private Venda venda;
    
    private Integer numeroParcela;
    private LocalDate dataVencimento;
    
    @Column(name = "valor_base")
    private BigDecimal valorBase;
    
    @Column(name = "igpm_aplicado")
    private BigDecimal igpmAplicado;
    
    @Column(name = "valor_final")
    private BigDecimal valorFinal;
    
    private String status = "pendente"; // pendente, pago, atrasado, cancelado
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
