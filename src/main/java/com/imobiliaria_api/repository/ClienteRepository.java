package com.imobiliaria_api.repository;

import com.imobiliaria_api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByEmpresaId(Long empresaId);
    Optional<Cliente> findByIdAndEmpresaId(Long id, Long empresaId);
    Optional<Cliente> findByCpfCnpjAndEmpresaId(String cpfCnpj, Long empresaId);
    List<Cliente> findByEmpresaIdAndTipo(Long empresaId, String tipo);
}
