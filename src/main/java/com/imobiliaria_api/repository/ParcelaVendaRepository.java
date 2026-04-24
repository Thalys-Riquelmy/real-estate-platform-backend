package com.imobiliaria_api.repository;

import com.imobiliaria_api.model.ParcelaVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParcelaVendaRepository extends JpaRepository<ParcelaVenda, Long> {
    List<ParcelaVenda> findByVendaIdOrderByNumeroParcelaAsc(Long vendaId);
    Optional<ParcelaVenda> findByIdAndVendaId(Long id, Long vendaId);
}
