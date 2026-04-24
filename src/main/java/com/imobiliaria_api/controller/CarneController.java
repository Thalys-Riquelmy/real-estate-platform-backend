package com.imobiliaria_api.controller;

import com.imobiliaria_api.dto.response.CarneResponseDTO;
import com.imobiliaria_api.service.AluguelService;
import com.imobiliaria_api.service.VendaService;
import com.imobiliaria_api.dto.response.VendaResponseDTO;
import com.imobiliaria_api.dto.response.AluguelResponseDTO;
import com.imobiliaria_api.model.ConfiguracaoFinanceira;
import com.imobiliaria_api.repository.ConfiguracaoFinanceiraRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/empresas/{empresaId}/financeiro/carnes")
@RequiredArgsConstructor
@Tag(name = "Carnês / Financeiro", description = "Endpoints para emissão de carnês e configurações")
public class CarneController {

    private final VendaService vendaService;
    private final AluguelService aluguelService;
    private final ConfiguracaoFinanceiraRepository configRepo;

    @GetMapping("/venda/{vendaId}")
    @Operation(summary = "Gerar carnê de Venda", description = "Retorna os dados estruturados de um carnê para venda financiada")
    public ResponseEntity<CarneResponseDTO> gerarCarneVenda(
            @PathVariable Long empresaId,
            @PathVariable Long vendaId) {
        
        VendaResponseDTO venda = vendaService.findById(vendaId, empresaId);
        ConfiguracaoFinanceira config = configRepo.findByEmpresaId(empresaId).orElse(null);
        String instrucoes = config != null ? "Após vencimento cobrar multa de " + config.getMultaFixa() + "% e juros." : "Pagamento até de vencimento";

        CarneResponseDTO carne = CarneResponseDTO.builder()
                .titulo("Carnê de Financiamento - Venda #" + venda.getId())
                .contratoId(venda.getId())
                .nomeCliente(venda.getCliente().getNome())
                .cpfCnpjCliente(venda.getCliente().getCpfCnpj())
                .boletos(venda.getParcelas().stream()
                        .filter(p -> !"pago".equals(p.getStatus()) && !"cancelado".equals(p.getStatus()))
                        .map(p -> CarneResponseDTO.BoletoCarneDTO.builder()
                                .numeroParcela(p.getNumeroParcela())
                                .dataVencimento(p.getDataVencimento())
                                .valorFinal(p.getValorFinal())
                                .codigoBarras("00000.00000 00000.00000 00000.00000 0 0000000000" + p.getId()) // mock
                                .instrucoes(instrucoes)
                                .build())
                        .collect(Collectors.toList()))
                .build();
                
        return ResponseEntity.ok(carne);
    }
    
    @GetMapping("/aluguel/{aluguelId}")
    @Operation(summary = "Gerar carnê de Aluguel", description = "Retorna os dados estruturados de um carnê para contrato de aluguel")
    public ResponseEntity<CarneResponseDTO> gerarCarneAluguel(
            @PathVariable Long empresaId,
            @PathVariable Long aluguelId) {
        
        AluguelResponseDTO aluguel = aluguelService.findById(aluguelId, empresaId);
        ConfiguracaoFinanceira config = configRepo.findByEmpresaId(empresaId).orElse(null);
        String instrucoes = config != null ? "Após vencimento cobrar multa de " + config.getMultaFixa() + "% e juros." : "Pagamento até data de vencimento";

        CarneResponseDTO carne = CarneResponseDTO.builder()
                .titulo("Carnê de Aluguel - Contrato #" + aluguel.getId())
                .contratoId(aluguel.getId())
                .nomeCliente(aluguel.getCliente().getNome())
                .cpfCnpjCliente(aluguel.getCliente().getCpfCnpj())
                .boletos(aluguel.getPrestacoes().stream()
                        .filter(p -> !"pago".equals(p.getStatus()) && !"cancelado".equals(p.getStatus()))
                        .map(p -> CarneResponseDTO.BoletoCarneDTO.builder()
                                .numeroParcela(null)
                                .dataVencimento(p.getDataVencimento())
                                .valorFinal(p.getValor())
                                .codigoBarras("11111.11111 11111.11111 11111.11111 1 1111111111" + p.getId()) // mock
                                .instrucoes(instrucoes)
                                .build())
                        .collect(Collectors.toList()))
                .build();
                
        return ResponseEntity.ok(carne);
    }
}
