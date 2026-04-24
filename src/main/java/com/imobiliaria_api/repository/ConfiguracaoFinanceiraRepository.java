package com.imobiliaria_api.repository;

import com.imobiliaria_api.model.ConfiguracaoFinanceira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ConfiguracaoFinanceiraRepository extends JpaRepository<ConfiguracaoFinanceira, Long> {
    Optional<ConfiguracaoFinanceira> findByEmpresaId(Long empresaId);
}