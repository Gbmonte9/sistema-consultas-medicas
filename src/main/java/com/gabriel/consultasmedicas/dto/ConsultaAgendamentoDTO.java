package com.gabriel.consultasmedicas.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat; // 🚨 IMPORTANTE
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaAgendamentoDTO {

    @NotBlank(message = "O ID do paciente é obrigatório")
    private String pacienteId;

    @NotBlank(message = "O ID do médico é obrigatório")
    private String medicoId;

    @NotNull(message = "A data e hora são obrigatórias")
    @Future(message = "A data e hora do agendamento devem ser futuras")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") 
    private LocalDateTime dataHora;

}