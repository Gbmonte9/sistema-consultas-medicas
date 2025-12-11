package com.gabriel.consultasmedicas.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HistoricoResponseDTO {

    private Long id;
    private Long consultaId; 
    private String observacoes;
    private String receita;
    private LocalDateTime dataRegistro; 
    
}