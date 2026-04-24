package com.imobiliaria_api.repository;

import com.imobiliaria_api.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Optional<Empresa> findByCnpj(String cnpj);
    Optional<Empresa> findByNome(String nome);
    boolean existsByCnpj(String cnpj);
}