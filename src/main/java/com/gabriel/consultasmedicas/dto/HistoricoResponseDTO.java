package com.gabriel.consultasmedicas.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

import java.util.UUID; 

@Data
@Builder
public class HistoricoResponseDTO {

    private UUID id;
    
    private UUID consultaId; 
    
    private String observacoes;
    private String receita;
    private LocalDateTime dataRegistro;
    
}