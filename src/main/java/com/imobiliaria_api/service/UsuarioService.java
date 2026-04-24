package com.imobiliaria_api.service;

import com.imobiliaria_api.dto.request.UsuarioRequestDTO;
import com.imobiliaria_api.dto.response.UsuarioResponseDTO;
import com.imobiliaria_api.exception.BusinessException;
import com.imobiliaria_api.exception.ResourceNotFoundException;
import com.imobiliaria_api.mapper.UsuarioMapper;
import com.imobiliaria_api.model.Empresa;
import com.imobiliaria_api.model.Usuario;
import com.imobiliaria_api.repository.EmpresaRepository;
import com.imobiliaria_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioResponseDTO criarUsuario(Long empresaId, UsuarioRequestDTO dto) {
        log.info("Criando usuário para empresa ID: {}", empresaId);
        
        Empresa empresa = empresaRepository.findById(empresaId)
            .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }

        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setEmpresa(empresa);
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        Usuario savedUsuario = usuarioRepository.save(usuario);
        log.info("Usuário criado com sucesso: ID={}, Email={}", savedUsuario.getId(), savedUsuario.getEmail());
        
        return usuarioMapper.toResponseDTO(savedUsuario);
    }

    public List<UsuarioResponseDTO> listarPorEmpresa(Long empresaId) {
        log.debug("Listando usuários da empresa ID: {}", empresaId);
        
        return usuarioRepository.findAll().stream()
            .filter(u -> u.getEmpresa().getId().equals(empresaId))
            .map(usuarioMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        log.debug("Buscando usuário por ID: {}", id);
        
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));
        return usuarioMapper.toResponseDTO(usuario);
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO dto) {
        log.info("Atualizando usuário ID: {}", id);
        
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!usuario.getEmail().equals(dto.getEmail()) && usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email já cadastrado por outro usuário");
        }

        usuarioMapper.updateEntityFromDTO(dto, usuario);
        
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        Usuario updatedUsuario = usuarioRepository.save(usuario);
        log.info("Usuário atualizado com sucesso: ID={}", id);
        
        return usuarioMapper.toResponseDTO(updatedUsuario);
    }

    public void desativarUsuario(Long id) {
        log.info("Desativando usuário ID: {}", id);
        
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
        
        log.info("Usuário desativado: ID={}", id);
    }

    public void ativarUsuario(Long id) {
        log.info("Ativando usuário ID: {}", id);
        
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);
        
        log.info("Usuário ativado: ID={}", id);
    }

    public void alterarSenha(Long id, String senhaAntiga, String senhaNova) {
        log.info("Alterando senha do usuário ID: {}", id);
        
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senhaAntiga, usuario.getSenha())) {
            throw new BusinessException("Senha antiga incorreta");
        }

        usuario.setSenha(passwordEncoder.encode(senhaNova));
        usuarioRepository.save(usuario);
        
        log.info("Senha alterada com sucesso para usuário ID: {}", id);
    }
}