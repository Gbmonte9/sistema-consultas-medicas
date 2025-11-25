package com.gabriel.consultasmedicas.dto;

import lombok.Data;

@Data
public class PacienteResponseDTO extends UsuarioResponseDTO {
    private String cpf;
    private String telefone;
}