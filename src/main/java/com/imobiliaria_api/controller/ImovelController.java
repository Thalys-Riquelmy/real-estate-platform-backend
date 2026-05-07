package com.imobiliaria_api.controller;

import com.imobiliaria_api.dto.request.FiltroImovelDTO;
import com.imobiliaria_api.dto.request.ImovelRequestDTO;
import com.imobiliaria_api.dto.response.ImagemImovelResponseDTO;
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

import java.util.List;
import java.util.Map;

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

    @PostMapping(value = "/com-imagens", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Criar imóvel com imagens", description = "Cria um imóvel já com as imagens compactadas")
    public ResponseEntity<ImovelResponseDTO> createWithImages(
            @PathVariable Long empresaId,
            @RequestPart("imovel") @Valid ImovelRequestDTO requestDTO,
            @RequestPart(value = "imagens", required = false) MultipartFile[] imagens) {
        ImovelResponseDTO response = imovelService.createWithImages(empresaId, requestDTO, imagens);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar imóveis com filtros", description = "Lista imóveis de uma empresa, permitindo filtros")
    public ResponseEntity<List<ImovelResponseDTO>> findAll(
            @PathVariable Long empresaId,
            @ModelAttribute FiltroImovelDTO filtro) {
        List<ImovelResponseDTO> responses = imovelService.findAll(empresaId, filtro);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar imóvel", description = "Busca um imóvel por ID com suas imagens")
    public ResponseEntity<ImovelResponseDTO> findById(
            @PathVariable Long empresaId,
            @PathVariable Long id) {
        ImovelResponseDTO response = imovelService.findById(id, empresaId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/imagens")
    @Operation(summary = "Listar imagens", description = "Lista todas as imagens de um imóvel")
    public ResponseEntity<List<ImagemImovelResponseDTO>> listarImagens(
            @PathVariable Long empresaId,
            @PathVariable Long id) {
        // Verifica se o imóvel existe
        imovelService.findById(id, empresaId);
        List<ImagemImovelResponseDTO> imagens = imovelService.listarImagens(id);
        return ResponseEntity.ok(imagens);
    }

    @PostMapping("/{id}/imagens")
    @Operation(summary = "Adicionar imagem", description = "Adiciona uma nova imagem ao imóvel")
    public ResponseEntity<ImagemImovelResponseDTO> adicionarImagem(
            @PathVariable Long empresaId,
            @PathVariable Long id,
            @RequestParam("imagem") MultipartFile imagem,
            @RequestParam(value = "principal", defaultValue = "false") Boolean principal) {
        ImagemImovelResponseDTO response = imovelService.adicionarImagem(id, imagem, principal);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/imagens/upload")
    @Operation(summary = "Upload múltiplo de imagens", description = "Envia várias imagens para o imóvel")
    public ResponseEntity<List<ImagemImovelResponseDTO>> uploadImagens(
            @PathVariable Long empresaId,
            @PathVariable Long id,
            @RequestParam("imagens") MultipartFile[] imagens) {
        List<ImagemImovelResponseDTO> responses = imovelService.salvarImagens(id, imagens);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}/imagens/{imagemId}")
    @Operation(summary = "Remover imagem", description = "Remove uma imagem do imóvel")
    public ResponseEntity<Void> removerImagem(
            @PathVariable Long empresaId,
            @PathVariable Long id,
            @PathVariable Long imagemId) {
        imovelService.removerImagem(id, imagemId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/imagens/{imagemId}/principal")
    @Operation(summary = "Definir imagem principal", description = "Define uma imagem como principal do imóvel")
    public ResponseEntity<Void> definirImagemPrincipal(
            @PathVariable Long empresaId,
            @PathVariable Long id,
            @PathVariable Long imagemId) {
        imovelService.definirImagemPrincipal(id, imagemId);
        return ResponseEntity.noContent().build();
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
    
    @PutMapping(value = "/{id}/com-imagens", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Atualizar imóvel com imagens", description = "Atualiza os dados e as imagens do imóvel")
    public ResponseEntity<ImovelResponseDTO> updateWithImages(
            @PathVariable Long empresaId,
            @PathVariable Long id,
            @RequestPart("imovel") @Valid ImovelRequestDTO requestDTO,
            @RequestPart(value = "imagens", required = false) MultipartFile[] imagens) {
        ImovelResponseDTO response = imovelService.update(id, empresaId, requestDTO);
        if (imagens != null && imagens.length > 0) {
            imovelService.salvarImagens(id, imagens);
            response = imovelService.findById(id, empresaId);
        }
        return ResponseEntity.ok(response);
    }
}