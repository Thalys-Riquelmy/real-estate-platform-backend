package com.imobiliaria_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "imagens_imovel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagemImovel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imovel_id", nullable = false)
    private Imovel imovel;
    
    @Column(columnDefinition = "BYTEA", nullable = false)
    private byte[] imagemCompactada; 
    
    private String contentType;
    private Integer ordem;
    private Boolean principal = false;
}