package com.imobiliaria_api.controller;

import com.imobiliaria_api.dto.request.ConfiguracaoEmailRequestDTO;
import com.imobiliaria_api.dto.request.ConfiguracaoFinanceiraRequestDTO;
import com.imobiliaria_api.dto.request.EmpresaRequestDTO;
import com.imobiliaria_api.dto.response.ApiResponse;
import com.imobiliaria_api.dto.response.ConfiguracaoEmailResponseDTO;
import com.imobiliaria_api.dto.response.ConfiguracaoFinanceiraResponseDTO;
import com.imobiliaria_api.dto.response.EmpresaResponseDTO;
import com.imobiliaria_api.service.EmpresaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
@RequiredArgsConstructor
@Slf4j
public class EmpresaController {

    private final EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<ApiResponse> criar(@Valid @RequestBody EmpresaRequestDTO dto) {
        log.info("Requisição para criar empresa: {}", dto.getNome());
        
        EmpresaResponseDTO empresa = empresaService.criarEmpresa(dto);
        
        return ResponseEntity.ok(ApiResponse.success("Empresa criada com sucesso", empresa));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> listarTodas() {
        log.info("Requisição para listar todas as empresas");
        
        List<EmpresaResponseDTO> empresas = empresaService.listarTodas();
        
        return ResponseEntity.ok(ApiResponse.success("Lista de empresas", empresas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> buscarPorId(@PathVariable Long id) {
        log.info("Requisição para buscar empresa ID: {}", id);
        
        EmpresaResponseDTO empresa = empresaService.buscarPorId(id);
        
        return ResponseEntity.ok(ApiResponse.success("Empresa encontrada", empresa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> atualizar(@PathVariable Long id, @Valid @RequestBody EmpresaRequestDTO dto) {
        log.info("Requisição para atualizar empresa ID: {}", id);
        
        EmpresaResponseDTO empresa = empresaService.atualizarEmpresa(id, dto);
        
        return ResponseEntity.ok(ApiResponse.success("Empresa atualizada com sucesso", empresa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> desativar(@PathVariable Long id) {
        log.info("Requisição para desativar empresa ID: {}", id);
        
        empresaService.desativarEmpresa(id);
        
        return ResponseEntity.ok(ApiResponse.success("Empresa desativada com sucesso", null));
    }

    @PostMapping("/{id}/configuracao-email")
    public ResponseEntity<ApiResponse> configurarEmail(
            @PathVariable Long id, 
            @Valid @RequestBody ConfiguracaoEmailRequestDTO dto) {
        log.info("Requisição para configurar email da empresa ID: {}", id);
        
        ConfiguracaoEmailResponseDTO config = empresaService.configurarEmail(id, dto);
        
        return ResponseEntity.ok(ApiResponse.success("Configuração de email salva com sucesso", config));
    }

    @GetMapping("/{id}/configuracao-email")
    public ResponseEntity<ApiResponse> buscarConfiguracaoEmail(@PathVariable Long id) {
        log.info("Requisição para buscar configuração de email da empresa ID: {}", id);
        
        ConfiguracaoEmailResponseDTO config = empresaService.buscarConfiguracaoEmail(id);
        
        return ResponseEntity.ok(ApiResponse.success("Configuração de email", config));
    }

    @PostMapping("/{id}/configuracao-financeira")
    public ResponseEntity<ApiResponse> configurarFinanceiro(
            @PathVariable Long id, 
            @Valid @RequestBody ConfiguracaoFinanceiraRequestDTO dto) {
        log.info("Requisição para configurar financeiro da empresa ID: {}", id);
        
        ConfiguracaoFinanceiraResponseDTO config = empresaService.configurarFinanceiro(id, dto);
        
        return ResponseEntity.ok(ApiResponse.success("Configuração financeira salva com sucesso", config));
    }

    @GetMapping("/{id}/configuracao-financeira")
    public ResponseEntity<ApiResponse> buscarConfiguracaoFinanceira(@PathVariable Long id) {
        log.info("Requisição para buscar configuração financeira da empresa ID: {}", id);
        
        ConfiguracaoFinanceiraResponseDTO config = empresaService.buscarConfiguracaoFinanceira(id);
        
        return ResponseEntity.ok(ApiResponse.success("Configuração financeira", config));
    }
}