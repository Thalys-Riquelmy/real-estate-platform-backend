package com.imobiliaria_api.controller;

import com.imobiliaria_api.dto.request.ClienteRequestDTO;
import com.imobiliaria_api.dto.response.ClienteResponseDTO;
import com.imobiliaria_api.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas/{empresaId}/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @Operation(summary = "Criar novo cliente", description = "Cria um novo cliente (comprador/locatário) vinculado a uma empresa")
    public ResponseEntity<ClienteResponseDTO> create(
            @PathVariable Long empresaId,
            @Valid @RequestBody ClienteRequestDTO requestDTO) {
        ClienteResponseDTO response = clienteService.create(empresaId, requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar clientes", description = "Lista todos os clientes de uma empresa")
    public ResponseEntity<List<ClienteResponseDTO>> findAll(@PathVariable Long empresaId) {
        List<ClienteResponseDTO> responses = clienteService.findAll(empresaId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente", description = "Busca um cliente por ID")
    public ResponseEntity<ClienteResponseDTO> findById(
            @PathVariable Long empresaId,
            @PathVariable Long id) {
        ClienteResponseDTO response = clienteService.findById(id, empresaId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cpf/{cpfCnpj}")
    @Operation(summary = "Buscar cliente por CPF/CNPJ", description = "Busca um cliente pelo seu CPF ou CNPJ")
    public ResponseEntity<ClienteResponseDTO> findByCpfCnpj(
            @PathVariable Long empresaId,
            @PathVariable String cpfCnpj) {
        ClienteResponseDTO response = clienteService.findByCpfCnpj(cpfCnpj, empresaId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Filtrar clientes por tipo", description = "Busca clientes filtando pelo tipo (comprador, locatario, ambos)")
    public ResponseEntity<List<ClienteResponseDTO>> findByTipo(
            @PathVariable Long empresaId,
            @PathVariable String tipo) {
        List<ClienteResponseDTO> responses = clienteService.findByTipo(tipo, empresaId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente")
    public ResponseEntity<ClienteResponseDTO> update(
            @PathVariable Long empresaId,
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDTO requestDTO) {
        ClienteResponseDTO response = clienteService.update(id, empresaId, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover cliente", description = "Remove fisicamente um cliente (caso não haja relacionamentos)")
    public ResponseEntity<Void> delete(
            @PathVariable Long empresaId,
            @PathVariable Long id) {
        clienteService.delete(id, empresaId);
        return ResponseEntity.noContent().build();
    }
}
