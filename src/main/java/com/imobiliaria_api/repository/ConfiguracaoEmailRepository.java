package com.imobiliaria_api.repository;

import com.imobiliaria_api.model.ConfiguracaoEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ConfiguracaoEmailRepository extends JpaRepository<ConfiguracaoEmail, Long> {
    Optional<ConfiguracaoEmail> findByEmpresaId(Long empresaId);
}