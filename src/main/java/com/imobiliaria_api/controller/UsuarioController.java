package com.imobiliaria_api.controller;

import com.imobiliaria_api.dto.request.UsuarioRequestDTO;
import com.imobiliaria_api.dto.response.ApiResponse;
import com.imobiliaria_api.dto.response.UsuarioResponseDTO;
import com.imobiliaria_api.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas/{empresaId}/usuarios")
@RequiredArgsConstructor
@Slf4j
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<ApiResponse> criar(
            @PathVariable Long empresaId,
            @Valid @RequestBody UsuarioRequestDTO dto) {
        log.info("Requisição para criar usuário na empresa ID: {}", empresaId);
        
        UsuarioResponseDTO usuario = usuarioService.criarUsuario(empresaId, dto);
        
        return ResponseEntity.ok(ApiResponse.success("Usuário criado com sucesso", usuario));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> listarPorEmpresa(@PathVariable Long empresaId) {
        log.info("Requisição para listar usuários da empresa ID: {}", empresaId);
        
        List<UsuarioResponseDTO> usuarios = usuarioService.listarPorEmpresa(empresaId);
        
        return ResponseEntity.ok(ApiResponse.success("Lista de usuários", usuarios));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> buscarPorId(@PathVariable Long id) {
        log.info("Requisição para buscar usuário ID: {}", id);
        
        UsuarioResponseDTO usuario = usuarioService.buscarPorId(id);
        
        return ResponseEntity.ok(ApiResponse.success("Usuário encontrado", usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto) {
        log.info("Requisição para atualizar usuário ID: {}", id);
        
        UsuarioResponseDTO usuario = usuarioService.atualizarUsuario(id, dto);
        
        return ResponseEntity.ok(ApiResponse.success("Usuário atualizado com sucesso", usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> desativar(@PathVariable Long id) {
        log.info("Requisição para desativar usuário ID: {}", id);
        
        usuarioService.desativarUsuario(id);
        
        return ResponseEntity.ok(ApiResponse.success("Usuário desativado com sucesso", null));
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<ApiResponse> ativar(@PathVariable Long id) {
        log.info("Requisição para ativar usuário ID: {}", id);
        
        usuarioService.ativarUsuario(id);
        
        return ResponseEntity.ok(ApiResponse.success("Usuário ativado com sucesso", null));
    }

    @PatchMapping("/{id}/alterar-senha")
    public ResponseEntity<ApiResponse> alterarSenha(
            @PathVariable Long id,
            @RequestParam String senhaAntiga,
            @RequestParam String senhaNova) {
        log.info("Requisição para alterar senha do usuário ID: {}", id);
        
        usuarioService.alterarSenha(id, senhaAntiga, senhaNova);
        
        return ResponseEntity.ok(ApiResponse.success("Senha alterada com sucesso", null));
    }
}