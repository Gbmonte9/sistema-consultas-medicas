package com.gabriel.consultasmedicas.interfaces;

import java.util.List;

import com.gabriel.consultasmedicas.dto.ConsultaRequestDTO;
import com.gabriel.consultasmedicas.dto.ConsultaResponseDTO;
import com.gabriel.consultasmedicas.model.Consulta;

public interface IConsultaService {
    Consulta agendarConsulta(Consulta consulta);
    void cancelarConsulta(Long id);
    List<Consulta> listarTodas();
    List<Consulta> listarPorMedico(Long medicoId);
    List<Consulta> listarPorPaciente(Long pacienteId);
	ConsultaResponseDTO agendar(ConsultaRequestDTO requestDTO);
	List<ConsultaResponseDTO> listar();
	void cancelar(Long id);
}
