package com.imobiliaria_api.repository;

import com.imobiliaria_api.model.ImagemImovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagemImovelRepository extends JpaRepository<ImagemImovel, Long> {
    List<ImagemImovel> findByImovelIdOrderByOrdemAsc(Long imovelId);
    void deleteByImovelId(Long imovelId);
}