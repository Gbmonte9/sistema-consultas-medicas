package com.gabriel.consultasmedicas.interfaces;

import java.util.List;

import com.gabriel.consultasmedicas.dto.PacienteResponseDTO;
import com.gabriel.consultasmedicas.model.Paciente;

public interface IPacienteService {
    Paciente salvar(Paciente paciente);
    Paciente buscarPorId(Long id);
    void remover(Long id);
	Paciente cadastrarPaciente(Paciente paciente);
	List<PacienteResponseDTO> listarTodos();
}
