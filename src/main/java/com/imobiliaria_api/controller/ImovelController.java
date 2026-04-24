package com.imobiliaria_api.controller;

import com.imobiliaria_api.dto.request.FiltroImovelDTO;
import com.imobiliaria_api.dto.request.ImovelRequestDTO;
import com.imobiliaria_api.dto.response.ImovelResponseDTO;
import com.imobiliaria_api.service.ImovelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;
import java.util.UUID;

import java.util.List;

@RestController
@RequestMapping("/empresas/{empresaId}/imoveis")
@RequiredArgsConstructor
@Tag(name = "Imóveis", description = "Endpoints para gerenciamento de imóveis e fotos")
public class ImovelController {

    private final ImovelService imovelService;

    @PostMapping
    @Operation(summary = "Criar novo imóvel", description = "Cria um imóvel vinculado a uma empresa e proprietário")
    public ResponseEntity<ImovelResponseDTO> create(
            @PathVariable Long empresaId,
            @Valid @RequestBody ImovelRequestDTO requestDTO) {
        ImovelResponseDTO response = imovelService.create(empresaId, requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar imóveis com filtros", description = "Lista imóveis de uma empresa, permitindo filtros opcionais via POST body (usando get para ser rest mas aqui vamos injetar no DTO por param).")
    public ResponseEntity<List<ImovelResponseDTO>> findAll(
            @PathVariable Long empresaId,
            @ModelAttribute FiltroImovelDTO filtro) {
        // DTO é preenchido através da query string por ser um ModelAttribute (GET Request)
        List<ImovelResponseDTO> responses = imovelService.findAll(empresaId, filtro);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar imóvel", description = "Busca um imóvel por ID")
    public ResponseEntity<ImovelResponseDTO> findById(
            @PathVariable Long empresaId,
            @PathVariable Long id) {
        ImovelResponseDTO response = imovelService.findById(id, empresaId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar imóvel", description = "Atualiza os dados de um imóvel")
    public ResponseEntity<ImovelResponseDTO> update(
            @PathVariable Long empresaId,
            @PathVariable Long id,
            @Valid @RequestBody ImovelRequestDTO requestDTO) {
        ImovelResponseDTO response = imovelService.update(id, empresaId, requestDTO);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Mudar status do imóvel", description = "Altera o status (disponivel, vendido, alugado, etc)")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long empresaId,
            @PathVariable Long id,
            @RequestBody Map<String, String> statusBody) {
        imovelService.updateStatus(id, empresaId, statusBody.get("status"));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover imóvel", description = "Remove um imóvel (caso não tenha vínculos impeditivos)")
    public ResponseEntity<Void> delete(
            @PathVariable Long empresaId,
            @PathVariable Long id) {
        imovelService.delete(id, empresaId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/fotos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload de fotos", description = "Simula upload e salva a URL da foto no JSON array do imóvel")
    public ResponseEntity<ImovelResponseDTO> uploadFotos(
            @PathVariable Long empresaId,
            @PathVariable Long id,
            @RequestParam("fotos") MultipartFile[] fotos) {
        // Mock de upload: gerando nomes random e adicionando url
        ImovelResponseDTO lastState = null;
        for (MultipartFile f : fotos) {
            String fakeUrl = "/uploads/imoveis/" + id + "/" + UUID.randomUUID() + ".jpg";
            lastState = imovelService.addFoto(id, empresaId, fakeUrl);
        }
        return ResponseEntity.ok(lastState);
    }
}
