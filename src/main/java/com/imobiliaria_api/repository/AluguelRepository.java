package com.imobiliaria_api.repository;

import com.imobiliaria_api.model.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel, Long> {
    List<Aluguel> findByEmpresaId(Long empresaId);
    Optional<Aluguel> findByIdAndEmpresaId(Long id, Long empresaId);
}
