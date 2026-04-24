package com.imobiliaria_api.controller;

import com.imobiliaria_api.dto.response.DashboardResponseDTO;
import com.imobiliaria_api.repository.AluguelRepository;
import com.imobiliaria_api.repository.ImovelRepository;
import com.imobiliaria_api.repository.ParcelaVendaRepository;
import com.imobiliaria_api.repository.PrestacaoAluguelRepository;
import com.imobiliaria_api.repository.VendaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/empresas/{empresaId}/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Dashboard e métricas")
public class DashboardController {

    private final ImovelRepository imovelRepository;
    private final VendaRepository vendaRepository;
    private final AluguelRepository aluguelRepository;
    private final ParcelaVendaRepository parcelaVendaRepository;
    private final PrestacaoAluguelRepository prestacaoAluguelRepository;

    @GetMapping
    @Operation(summary = "Resumo do Dashboard", description = "Retorna os indicadores principais")
    public ResponseEntity<DashboardResponseDTO> getDashboard(@PathVariable Long empresaId) {
        
        long totalImoveisDisponiveis = imovelRepository.findAll((root, query, cb) -> 
            cb.and(cb.equal(root.get("empresa").get("id"), empresaId), cb.equal(root.get("status"), "disponivel"))).size();
        long totalImoveisVendidos = imovelRepository.findAll((root, query, cb) -> 
            cb.and(cb.equal(root.get("empresa").get("id"), empresaId), cb.equal(root.get("status"), "vendido"))).size();
        long totalImoveisAlugados = imovelRepository.findAll((root, query, cb) -> 
            cb.and(cb.equal(root.get("empresa").get("id"), empresaId), cb.equal(root.get("status"), "alugado"))).size();
            
        long contratosVenda = vendaRepository.findByEmpresaId(empresaId).stream()
            .filter(v -> "ativo".equals(v.getStatus())).count();
            
        long contratosAluguel = aluguelRepository.findByEmpresaId(empresaId).stream()
            .filter(a -> "ativo".equals(a.getStatus())).count();

        LocalDate hoje = LocalDate.now();
        long vendasAtrasadas = vendaRepository.findByEmpresaId(empresaId).stream()
            .flatMap(v -> parcelaVendaRepository.findByVendaIdOrderByNumeroParcelaAsc(v.getId()).stream())
            .filter(p -> "pendente".equals(p.getStatus()) && p.getDataVencimento().isBefore(hoje))
            .count();
        long alugueisAtrasados = aluguelRepository.findByEmpresaId(empresaId).stream()
            .flatMap(a -> prestacaoAluguelRepository.findByAluguelIdOrderByDataVencimentoAsc(a.getId()).stream())
            .filter(p -> "pendente".equals(p.getStatus()) && p.getDataVencimento().isBefore(hoje))
            .count();

        DashboardResponseDTO dashboard = DashboardResponseDTO.builder()
                .totalImoveisDisponiveis(totalImoveisDisponiveis)
                .totalImoveisVendidos(totalImoveisVendidos)
                .totalImoveisAlugados(totalImoveisAlugados)
                .contratosAtivosVenda(contratosVenda)
                .contratosAtivosAluguel(contratosAluguel)
                .faturamentoPrevistoMes(new BigDecimal("150000.00")) // mock de previsão global mensal
                .faturamentoRealizadoMes(BigDecimal.ZERO) // mock real do mês
                .inadimplencia(vendasAtrasadas + alugueisAtrasados)
                .build();
                
        return ResponseEntity.ok(dashboard);
    }
}
