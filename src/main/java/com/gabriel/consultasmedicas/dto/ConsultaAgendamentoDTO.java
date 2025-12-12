package com.gabriel.consultasmedicas.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor; 
import lombok.AllArgsConstructor; 

@Data 
@NoArgsConstructor
@AllArgsConstructor 
public class ConsultaAgendamentoDTO {

    @NotNull(message = "O ID do paciente é obrigatório")
    private Long pacienteId;

    @NotNull(message = "O ID do médico é obrigatório")
    private Long medicoId;

    @NotNull(message = "A data e hora são obrigatórias")
    @Future(message = "A data e hora do agendamento devem ser futuras")
    private LocalDateTime dataHora;
    
}