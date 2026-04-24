package com.imobiliaria_api.repository;

import com.imobiliaria_api.model.Imovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long>, JpaSpecificationExecutor<Imovel> {
    Optional<Imovel> findByIdAndEmpresaId(Long id, Long empresaId);
}
