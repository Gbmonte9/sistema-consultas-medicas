package com.gabriel.consultasmedicas.dto;

import lombok.Data;

@Data
public class MedicoResponseDTO extends UsuarioResponseDTO {
    private String crm;
    private String especialidade;
}