package com.gabriel.consultasmedicas.interfaces;

import java.util.List;
import com.gabriel.consultasmedicas.dto.PacienteCadastroDTO;
import com.gabriel.consultasmedicas.dto.PacienteResponseDTO;
import jakarta.validation.Valid;

import java.util.UUID; 

public interface IPacienteService {
	
	PacienteResponseDTO criar(@Valid PacienteCadastroDTO dto);

	PacienteResponseDTO buscarPorId(UUID id);

	PacienteResponseDTO buscarPorCpf(String cpf);

	PacienteResponseDTO atualizar(UUID id, @Valid PacienteCadastroDTO dto);

	List<PacienteResponseDTO> listarTodos();

	void remover(UUID id);

}