package com.gabriel.consultasmedicas.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class MedicoRequestDTO extends UsuarioRequestDTO {
    @NotBlank
    private String crm;
    
    @NotBlank
    private String especialidade;
}