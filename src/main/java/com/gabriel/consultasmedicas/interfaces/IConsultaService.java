package com.gabriel.consultasmedicas.interfaces;

import java.util.List;

import com.gabriel.consultasmedicas.dto.ConsultaAgendamentoDTO;
import com.gabriel.consultasmedicas.dto.ConsultaResponseDTO;
import com.gabriel.consultasmedicas.model.StatusConsulta;

import java.util.UUID;


public interface IConsultaService {
    
    ConsultaResponseDTO agendar(ConsultaAgendamentoDTO dto);

    ConsultaResponseDTO agendarEFinalizar(ConsultaAgendamentoDTO dto); // Adicione esta linha!
    
    void cancelar(UUID id);

    void remover(UUID id);

    void finalizar(UUID id);

    ConsultaResponseDTO atualizarStatus(UUID id, StatusConsulta novoStatus);

    ConsultaResponseDTO buscarPorId(UUID id);

    List<ConsultaResponseDTO> listarTodas();

    List<ConsultaResponseDTO> listarPorMedicoId(UUID medicoId);

    List<ConsultaResponseDTO> listarPorMedicoEStatus(UUID medicoId, StatusConsulta status);

    List<ConsultaResponseDTO> listarPorPacienteId(UUID pacienteId);

    List<ConsultaResponseDTO> listarPorPacienteEStatus(UUID pacienteId, StatusConsulta status);
}