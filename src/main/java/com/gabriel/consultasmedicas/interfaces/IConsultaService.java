package com.gabriel.consultasmedicas.interfaces;

import java.util.List;
import java.util.UUID;
import com.gabriel.consultasmedicas.dto.ConsultaAgendamentoDTO;
import com.gabriel.consultasmedicas.dto.ConsultaResponseDTO;
import com.gabriel.consultasmedicas.model.StatusConsulta;

public interface IConsultaService {
    
    ConsultaResponseDTO agendar(ConsultaAgendamentoDTO dto);

    ConsultaResponseDTO agendarEFinalizar(ConsultaAgendamentoDTO dto);
    
    void cancelar(UUID id);

    void remover(UUID id);

    void finalizar(UUID id);

    ConsultaResponseDTO atualizarStatus(UUID id, StatusConsulta novoStatus);

    ConsultaResponseDTO buscarPorId(UUID id);

    List<ConsultaResponseDTO> listarTodas();

    List<ConsultaResponseDTO> listarPorMedicoId(UUID medicoId);

    List<ConsultaResponseDTO> listarPorMedicoEStatus(UUID medicoId, StatusConsulta status);

    List<ConsultaResponseDTO> listarPorPacienteId(UUID id); 

    List<ConsultaResponseDTO> listarPorPacienteEStatus(UUID pacienteId, StatusConsulta status);
}