package com.imobiliaria_api.controller;

import com.imobiliaria_api.dto.request.ConfiguracaoFinanceiraRequestDTO;
import com.imobiliaria_api.dto.response.ConfiguracaoFinanceiraResponseDTO;
import com.imobiliaria_api.service.ConfiguracaoFinanceiraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empresas/{empresaId}/configuracoes-financeiras")
@RequiredArgsConstructor
@Tag(name = "Configuração Financeira", description = "Endpoints para configuração de juros, multas e IGP-M")
public class ConfiguracaoFinanceiraController {

    private final ConfiguracaoFinanceiraService service;

    @GetMapping
    @Operation(summary = "Obter configurações", description = "Retorna as configurações financeiras da empresa")
    public ResponseEntity<ConfiguracaoFinanceiraResponseDTO> getByEmpresaId(@PathVariable Long empresaId) {
        return ResponseEntity.ok(service.getByEmpresaId(empresaId));
    }

    @PutMapping
    @Operation(summary = "Atualizar configurações", description = "Atualiza as configurações financeiras (juros, multas, igpm)")
    public ResponseEntity<ConfiguracaoFinanceiraResponseDTO> update(
            @PathVariable Long empresaId,
            @RequestBody ConfiguracaoFinanceiraRequestDTO dto) {
        return ResponseEntity.ok(service.update(empresaId, dto));
    }
}
