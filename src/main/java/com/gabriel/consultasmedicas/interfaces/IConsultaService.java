package com.gabriel.consultasmedicas.interfaces;

import java.util.List;

import com.gabriel.consultasmedicas.dto.ConsultaAgendamentoDTO;
import com.gabriel.consultasmedicas.dto.ConsultaResponseDTO;
import com.gabriel.consultasmedicas.model.StatusConsulta;


public interface IConsultaService {
    
    
    ConsultaResponseDTO agendar(ConsultaAgendamentoDTO dto);

    void cancelar(Long id);

    void remover(Long id);

    void finalizar(Long id);

    ConsultaResponseDTO atualizarStatus(Long id, StatusConsulta novoStatus);

    ConsultaResponseDTO buscarPorId(Long id);

    List<ConsultaResponseDTO> listarTodas();

    List<ConsultaResponseDTO> listarPorMedico(Long medicoId);

    List<ConsultaResponseDTO> listarPorMedicoEStatus(Long medicoId, StatusConsulta status);

    List<ConsultaResponseDTO> listarPorPaciente(Long pacienteId);

    List<ConsultaResponseDTO> listarPorPacienteEStatus(Long pacienteId, StatusConsulta status);

	List<ConsultaResponseDTO> listarPorMedicoId(Long medicoId);

	List<ConsultaResponseDTO> listarPorPacienteId(Long pacienteId);
}
