package com.imobiliaria_api.dto.response;

import com.imobiliaria_api.model.enums.PerfilUsuario;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private PerfilUsuario perfil;
    private Boolean ativo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private EmpresaResponseDTO empresa;
}