package com.imobiliaria_api.repository;

import com.imobiliaria_api.model.Proprietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProprietarioRepository extends JpaRepository<Proprietario, Long> {
    List<Proprietario> findByEmpresaId(Long empresaId);
    Optional<Proprietario> findByIdAndEmpresaId(Long id, Long empresaId);
    Optional<Proprietario> findByCpfCnpjAndEmpresaId(String cpfCnpj, Long empresaId);
}
