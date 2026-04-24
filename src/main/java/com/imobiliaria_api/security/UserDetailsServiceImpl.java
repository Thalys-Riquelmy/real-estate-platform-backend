package com.imobiliaria_api.security;

import com.imobiliaria_api.model.Usuario;
import com.imobiliaria_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("Buscando usuário por email: {}", email);
        
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));

        if (!usuario.getAtivo()) {
            log.warn("Tentativa de login com usuário inativo: {}", email);
            throw new UsernameNotFoundException("Usuário inativo");
        }

        log.debug("Usuário encontrado: {}", email);
        
        return User.builder()
            .username(usuario.getEmail())
            .password(usuario.getSenha())
            .roles(usuario.getPerfil().name())
            .build();
    }
}