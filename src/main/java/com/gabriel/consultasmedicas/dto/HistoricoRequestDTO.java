package com.gabriel.consultasmedicas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor; 
import lombok.Data;
import java.util.UUID; 

@Data
@AllArgsConstructor 
public class HistoricoRequestDTO {

    @NotNull(message = "O ID da consulta é obrigatório")
    private UUID consultaId;

    private String observacoes; 

    private String receita;
}