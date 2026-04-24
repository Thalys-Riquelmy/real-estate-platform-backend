package com.imobiliaria_api.service;  

import com.imobiliaria_api.dto.request.LoginRequestDTO;
import com.imobiliaria_api.dto.response.TokenResponseDTO;
import com.imobiliaria_api.dto.response.UsuarioResponseDTO;
import com.imobiliaria_api.exception.BusinessException;
import com.imobiliaria_api.mapper.UsuarioMapper;
import com.imobiliaria_api.model.Usuario;
import com.imobiliaria_api.repository.UsuarioRepository;
import com.imobiliaria_api.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;  
    private final PasswordEncoder passwordEncoder;

    public TokenResponseDTO login(LoginRequestDTO loginDTO) {
        try {
            log.info("Tentativa de login para email: {}", loginDTO.getEmail());
            
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtTokenProvider.generateToken(authentication);
            Long expiresIn = jwtTokenProvider.getExpirationTime();

            Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

            UsuarioResponseDTO usuarioResponse = usuarioMapper.toResponseDTO(usuario);

            log.info("Login realizado com sucesso: {}", loginDTO.getEmail());
            return new TokenResponseDTO(jwt, "Bearer", expiresIn, usuarioResponse);
            
        } catch (Exception e) {
            log.error("Erro no login para email {}: {}", loginDTO.getEmail(), e.getMessage());
            throw new BusinessException("Email ou senha inválidos");
        }
    }
}