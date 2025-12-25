package com.gabriel.consultasmedicas.dto;

import com.gabriel.consultasmedicas.model.TipoUsuario;

import lombok.Builder;
import lombok.Data;

import java.util.UUID; 

@Data
@Builder
public class UsuarioResponseDTO {

    private UUID id;
    
    private String nome;
    private String email;
    private TipoUsuario tipo;

}