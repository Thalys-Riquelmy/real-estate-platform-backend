package com.imobiliaria_api.repository;

import com.imobiliaria_api.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByEmpresaId(Long empresaId);
    Optional<Venda> findByIdAndEmpresaId(Long id, Long empresaId);
}
