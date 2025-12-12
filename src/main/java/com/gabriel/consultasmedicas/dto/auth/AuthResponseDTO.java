package com.gabriel.consultasmedicas.dto.auth;

import com.gabriel.consultasmedicas.model.TipoUsuario;

import lombok.Builder;
import lombok.Data;

@Data
@Builder 
public class AuthResponseDTO {

    private String token; 
    private Long id; 
    private String nome;
    private String email;
    private TipoUsuario tipo; 
    private String mensagem; 
}