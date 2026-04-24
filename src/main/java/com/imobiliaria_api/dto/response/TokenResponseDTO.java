package com.imobiliaria_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponseDTO {
    private String token;
    private String tipo = "Bearer";
    private Long expiresIn;
    private UsuarioResponseDTO usuario;
}