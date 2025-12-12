package com.gabriel.consultasmedicas.dto;

import com.gabriel.consultasmedicas.model.TipoUsuario;

import lombok.Builder;
import lombok.Data;

import java.util.UUID; 

@Data
@Builder
public class MedicoResponseDTO {

    private UUID id;
    
    private String nomeUsuario;
    private String emailUsuario;
    private TipoUsuario tipo;
    private String crm;
    private String especialidade;
}