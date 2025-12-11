package com.gabriel.consultasmedicas.dto;

import com.gabriel.consultasmedicas.model.TipoUsuario;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private TipoUsuario tipo;

}
