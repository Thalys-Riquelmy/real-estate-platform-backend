package com.imobiliaria_api.service;

import com.imobiliaria_api.dto.request.ClienteRequestDTO;
import com.imobiliaria_api.dto.response.ClienteResponseDTO;
import com.imobiliaria_api.exception.BusinessException;
import com.imobiliaria_api.exception.ResourceNotFoundException;
import com.imobiliaria_api.mapper.ClienteMapperManual;
import com.imobiliaria_api.model.Cliente;
import com.imobiliaria_api.model.Empresa;
import com.imobiliaria_api.repository.ClienteRepository;
import com.imobiliaria_api.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;
    private final ClienteMapperManual clienteMapper;  

    @Transactional
    public ClienteResponseDTO create(Long empresaId, ClienteRequestDTO requestDTO) {
        
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada com ID: " + empresaId));

        clienteRepository.findByCpfCnpjAndEmpresaId(requestDTO.getCpfCnpj(), empresaId)
                .ifPresent(c -> { throw new BusinessException("Já existe um cliente com o CPF/CNPJ informado nesta empresa."); });

        Cliente cliente = clienteMapper.toEntity(requestDTO);
        cliente.setEmpresa(empresa);

        Cliente saved = clienteRepository.save(cliente);
        return clienteMapper.toResponseDTO(saved);
    }

    public List<ClienteResponseDTO> findAll(Long empresaId) {
        return clienteRepository.findByEmpresaId(empresaId).stream()
                .map(clienteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO findById(Long id, Long empresaId) {
        Cliente cliente = getCliente(id, empresaId);
        return clienteMapper.toResponseDTO(cliente);
    }

    public ClienteResponseDTO findByCpfCnpj(String cpfCnpj, Long empresaId) {
        Cliente cliente = clienteRepository.findByCpfCnpjAndEmpresaId(cpfCnpj, empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com CPF/CNPJ: " + cpfCnpj));
        return clienteMapper.toResponseDTO(cliente);
    }

    public List<ClienteResponseDTO> findByTipo(String tipo, Long empresaId) {
        return clienteRepository.findByEmpresaIdAndTipo(empresaId, tipo).stream()
                .map(clienteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ClienteResponseDTO update(Long id, Long empresaId, ClienteRequestDTO requestDTO) {
        Cliente cliente = getCliente(id, empresaId);

        if (!cliente.getCpfCnpj().equals(requestDTO.getCpfCnpj())) {
            clienteRepository.findByCpfCnpjAndEmpresaId(requestDTO.getCpfCnpj(), empresaId)
                    .ifPresent(c -> { throw new BusinessException("Já existe outro cliente com o CPF/CNPJ informado."); });
        }

        clienteMapper.updateEntityFromDTO(requestDTO, cliente);
        Cliente updated = clienteRepository.save(cliente);
        return clienteMapper.toResponseDTO(updated);
    }

    @Transactional
    public void delete(Long id, Long empresaId) {
        Cliente cliente = getCliente(id, empresaId);
        clienteRepository.delete(cliente);
    }

    private Cliente getCliente(Long id, Long empresaId) {
        return clienteRepository.findByIdAndEmpresaId(id, empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado."));
    }
}