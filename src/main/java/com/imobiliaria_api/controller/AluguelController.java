package com.imobiliaria_api.controller;

import com.imobiliaria_api.dto.request.AluguelRequestDTO;
import com.imobiliaria_api.dto.request.PagamentoParcelaRequestDTO;
import com.imobiliaria_api.dto.response.AluguelResponseDTO;
import com.imobiliaria_api.dto.response.PrestacaoAluguelResponseDTO;
import com.imobiliaria_api.service.AluguelService;
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
@RequestMapping("/empresas/{empresaId}/alugueis")
@RequiredArgsConstructor
@Tag(name = "Aluguéis", description = "Endpoints para contratos de aluguel e prestações")
public class AluguelController {

    private final AluguelService aluguelService;

    @PostMapping
    @Operation(summary = "Criar aluguel", description = "Cria um contrato de aluguel novo")
    public ResponseEntity<AluguelResponseDTO> create(
            @PathVariable Long empresaId,
            @Valid @RequestBody AluguelRequestDTO requestDTO) {
        AluguelResponseDTO response = aluguelService.create(empresaId, requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar aluguéis", description = "Lista contratos de aluguel da empresa")
    public ResponseEntity<List<AluguelResponseDTO>> findAll(@PathVariable Long empresaId) {
        return ResponseEntity.ok(aluguelService.findAll(empresaId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar aluguel", description = "Busca contrato de aluguel por ID")
    public ResponseEntity<AluguelResponseDTO> findById(
            @PathVariable Long empresaId,
            @PathVariable Long id) {
        return ResponseEntity.ok(aluguelService.findById(id, empresaId));
    }

    @GetMapping("/{id}/prestacoes")
    @Operation(summary = "Listar prestações", description = "Lista as prestações do contrato")
    public ResponseEntity<List<PrestacaoAluguelResponseDTO>> getPrestacoes(
            @PathVariable Long empresaId,
            @PathVariable Long id) {
        return ResponseEntity.ok(aluguelService.getPrestacoes(id, empresaId));
    }

    @PostMapping("/{id}/prestacoes/{prestacaoId}/pagar")
    @Operation(summary = "Pagar prestação", description = "Registra o pagamento de uma prestação")
    public ResponseEntity<PrestacaoAluguelResponseDTO> pagarPrestacao(
            @PathVariable Long empresaId,
            @PathVariable Long id,
            @PathVariable Long prestacaoId,
            @RequestBody PagamentoParcelaRequestDTO requestDTO) {
        return ResponseEntity.ok(aluguelService.pagarPrestacao(id, prestacaoId, empresaId, requestDTO));
    }

    @PostMapping("/{id}/gerar-prestacoes")
    @Operation(summary = "Gerar mais prestações", description = "Gera mais X meses de prestações (ex. útil para virada de contrato)")
    public ResponseEntity<List<PrestacaoAluguelResponseDTO>> gerarMais(
            @PathVariable Long empresaId,
            @PathVariable Long id,
            @RequestParam(defaultValue = "12") int meses) {
        return ResponseEntity.ok(aluguelService.gerarMaisPrestacoes(id, empresaId, meses));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Mudar status", description = "Muda o status do contrato de aluguel (ex: encerrar)")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long empresaId,
            @PathVariable Long id,
            @RequestBody Map<String, String> statusBody) {
        aluguelService.updateStatus(id, empresaId, statusBody.get("status"));
        return ResponseEntity.noContent().build();
    }
}
