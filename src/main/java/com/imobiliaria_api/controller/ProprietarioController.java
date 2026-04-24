package com.imobiliaria_api.controller;

import com.imobiliaria_api.dto.request.ProprietarioRequestDTO;
import com.imobiliaria_api.dto.response.ProprietarioResponseDTO;
import com.imobiliaria_api.service.ProprietarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas/{empresaId}/proprietarios")
@RequiredArgsConstructor
@Tag(name = "Proprietários", description = "Endpoints para gerenciamento de proprietários")
public class ProprietarioController {

    private final ProprietarioService proprietarioService;

    @PostMapping
    @Operation(summary = "Criar novo proprietário", description = "Cria um novo proprietário vinculado a uma empresa")
    public ResponseEntity<ProprietarioResponseDTO> create(
            @PathVariable Long empresaId,
            @Valid @RequestBody ProprietarioRequestDTO requestDTO) {
        ProprietarioResponseDTO response = proprietarioService.create(empresaId, requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar proprietários", description = "Lista todos os proprietários de uma empresa")
    public ResponseEntity<List<ProprietarioResponseDTO>> findAll(@PathVariable Long empresaId) {
        List<ProprietarioResponseDTO> responses = proprietarioService.findAll(empresaId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar proprietário", description = "Busca um proprietário por ID")
    public ResponseEntity<ProprietarioResponseDTO> findById(
            @PathVariable Long empresaId,
            @PathVariable Long id) {
        ProprietarioResponseDTO response = proprietarioService.findById(id, empresaId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cpf/{cpfCnpj}")
    @Operation(summary = "Buscar proprietário por CPF/CNPJ", description = "Busca um proprietário pelo seu CPF ou CNPJ")
    public ResponseEntity<ProprietarioResponseDTO> findByCpfCnpj(
            @PathVariable Long empresaId,
            @PathVariable String cpfCnpj) {
        ProprietarioResponseDTO response = proprietarioService.findByCpfCnpj(cpfCnpj, empresaId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar proprietário", description = "Atualiza os dados de um proprietário")
    public ResponseEntity<ProprietarioResponseDTO> update(
            @PathVariable Long empresaId,
            @PathVariable Long id,
            @Valid @RequestBody ProprietarioRequestDTO requestDTO) {
        ProprietarioResponseDTO response = proprietarioService.update(id, empresaId, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover proprietário", description = "Realiza o soft delete (desativação) de um proprietário")
    public ResponseEntity<Void> delete(
            @PathVariable Long empresaId,
            @PathVariable Long id) {
        proprietarioService.delete(id, empresaId);
        return ResponseEntity.noContent().build();
    }
}
