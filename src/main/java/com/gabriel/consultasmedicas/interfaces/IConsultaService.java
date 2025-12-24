package com.gabriel.consultasmedicas.interfaces;

import com.gabriel.consultasmedicas.dto.ConsultaAgendamentoDTO;
import com.gabriel.consultasmedicas.dto.ConsultaResponseDTO;
import com.gabriel.consultasmedicas.dto.PacienteResponseDTO;
import com.gabriel.consultasmedicas.model.StatusConsulta;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IConsultaService {
    
    // MÉTODOS QUE ESTAVAM FALTANDO:
    List<ConsultaResponseDTO> buscarAgendaDoDia(UUID medicoId);
    
    Map<String, Long> buscarEstatisticasDash(UUID medicoId);

    // DEMAIS MÉTODOS:
    ConsultaResponseDTO agendar(ConsultaAgendamentoDTO dto);
    
    ConsultaResponseDTO agendarEFinalizar(ConsultaAgendamentoDTO dto);
    
    void cancelar(UUID id);
    
    void finalizar(UUID id);
    
    ConsultaResponseDTO atualizarStatus(UUID id, StatusConsulta novoStatus);
    
    ConsultaResponseDTO buscarPorId(UUID id);
    
    List<ConsultaResponseDTO> listarTodas();
    
    List<ConsultaResponseDTO> listarPorMedicoId(UUID id);
    
    List<PacienteResponseDTO> listarPacientesAtendidosPorMedico(UUID id);
    
    List<ConsultaResponseDTO> listarPorMedicoEStatus(UUID medicoId, StatusConsulta status);
    
    List<ConsultaResponseDTO> listarPorPacienteId(UUID id);
    
    List<ConsultaResponseDTO> listarPorPacienteEStatus(UUID pacienteId, StatusConsulta status);
    
    void remover(UUID id);
}