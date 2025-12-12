package com.gabriel.consultasmedicas.dto;

import com.gabriel.consultasmedicas.model.TipoUsuario;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PacienteResponseDTO {

    private Long id; 
    private String nomeUsuario; 
    private String emailUsuario; 
    private TipoUsuario tipo; 
    private String cpf;
    private String telefone;
}