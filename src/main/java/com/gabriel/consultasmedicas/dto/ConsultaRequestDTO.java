package com.gabriel.consultasmedicas.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Data
public class ConsultaRequestDTO {
    // ID do Paciente logado ou passado na requisição
    @NotNull
    private Long pacienteId;
    
    // ID do Médico escolhido
    @NotNull
    private Long medicoId;
    
    @NotNull
    private LocalDateTime dataHora; // Data e hora do agendamento
    
    @NotBlank
    private String motivo; // Breve descrição
}