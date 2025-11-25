package com.gabriel.consultasmedicas.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class PacienteRequestDTO extends UsuarioRequestDTO {
    @NotBlank
    private String cpf;
    
    private String telefone;
}