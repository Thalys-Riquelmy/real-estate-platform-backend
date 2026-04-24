package com.imobiliaria_api.controller;

import com.imobiliaria_api.dto.request.PagamentoParcelaRequestDTO;
import com.imobiliaria_api.dto.request.VendaRequestDTO;
import com.imobiliaria_api.dto.response.ParcelaVendaResponseDTO;
import com.imobiliaria_api.dto.response.VendaResponseDTO;
import com.imobiliaria_api.service.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/empresas/{empresaId}/vendas")
@RequiredArgsConstructor
@Tag(name = "Vendas (Financiamento)", description = "Endpoints para contratos de venda financiada e parcelas")
public class VendaController {

    private final VendaService vendaService;

    @PostMapping
    @Operation(summary = "Criar nova venda", description = "Cria um contrato de financiamento gerando automaticamente as parcelas")
    public ResponseEntity<VendaResponseDTO> create(
            @PathVariable Long empresaId,
            @Valid @RequestBody VendaRequestDTO requestDTO) {
        VendaResponseDTO response = vendaService.create(empresaId, requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar vendas", description = "Lista todos os contratos de venda da empresa")
    public ResponseEntity<List<VendaResponseDTO>> findAll(@PathVariable Long empresaId) {
        List<VendaResponseDTO> responses = vendaService.findAll(empresaId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar venda", description = "Busca uma venda por ID detalhada com parcelas")
    public ResponseEntity<VendaResponseDTO> findById(
            @PathVariable Long empresaId,
            @PathVariable Long id) {
        VendaResponseDTO response = vendaService.findById(id, empresaId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/parcelas")
    @Operation(summary = "Listar parcelas", description = "Lista as parcelas de uma venda específica")
    public ResponseEntity<List<ParcelaVendaResponseDTO>> getParcelas(
            @PathVariable Long empresaId,
            @PathVariable Long id) {
        List<ParcelaVendaResponseDTO> responses = vendaService.getParcelas(id, empresaId);
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{id}/parcelas/{parcelaId}/pagar")
    @Operation(summary = "Pagar parcela", description = "Registra o pagamento de uma parcela com cálculo de juros caso haja atraso")
    public ResponseEntity<ParcelaVendaResponseDTO> pagarParcela(
            @PathVariable Long empresaId,
            @PathVariable Long id,
            @PathVariable Long parcelaId,
            @RequestBody PagamentoParcelaRequestDTO requestDTO) {
        ParcelaVendaResponseDTO response = vendaService.pagarParcela(id, parcelaId, empresaId, requestDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Alterar status da venda", description = "Muda o status da venda (ativo, quitado, cancelado)")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long empresaId,
            @PathVariable Long id,
            @RequestBody Map<String, String> statusBody) {
        vendaService.updateStatus(id, empresaId, statusBody.get("status"));
        return ResponseEntity.noContent().build();
    }
}
