package com.gabriel.consultasmedicas.dto;

import java.time.LocalDateTime;
import java.util.UUID; 

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
    private UUID pacienteId;

    @NotNull(message = "O ID do médico é obrigatório")
    private UUID medicoId;

    @NotNull(message = "A data e hora são obrigatórias")
    @Future(message = "A data e hora do agendamento devem ser futuras")
    private LocalDateTime dataHora;
    
}