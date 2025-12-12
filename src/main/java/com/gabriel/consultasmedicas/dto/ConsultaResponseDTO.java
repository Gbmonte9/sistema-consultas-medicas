package com.gabriel.consultasmedicas.dto;

import java.time.LocalDateTime;

import com.gabriel.consultasmedicas.model.StatusConsulta;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConsultaResponseDTO {

    private Long id;
    private LocalDateTime dataHora;
    private LocalDateTime dataFim; 
    private StatusConsulta status;
    private MedicoConsultaDTO medico;
    private PacienteConsultaDTO paciente;

    @Data
    @Builder
    public static class MedicoConsultaDTO {
        private Long id;
        private String nome;
        private String especialidade;
        private String crm;
    }

    @Data
    @Builder
    public static class PacienteConsultaDTO {
        private Long id;
        private String nome;
        private String cpf;
    }
    
}