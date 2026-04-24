package com.imobiliaria_api.controller;

import com.imobiliaria_api.dto.request.LoginRequestDTO;
import com.imobiliaria_api.dto.response.ApiResponse;
import com.imobiliaria_api.dto.response.TokenResponseDTO;
import com.imobiliaria_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequestDTO loginDTO) {
        log.info("Requisição de login para email: {}", loginDTO.getEmail());
        
        TokenResponseDTO token = authService.login(loginDTO);
        
        return ResponseEntity.ok(ApiResponse.success("Login realizado com sucesso", token));
    }
}