package com.imobiliaria_api.repository;

import com.imobiliaria_api.model.PrestacaoAluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrestacaoAluguelRepository extends JpaRepository<PrestacaoAluguel, Long> {
    List<PrestacaoAluguel> findByAluguelIdOrderByDataVencimentoAsc(Long aluguelId);
    Optional<PrestacaoAluguel> findByIdAndAluguelId(Long id, Long aluguelId);
}
