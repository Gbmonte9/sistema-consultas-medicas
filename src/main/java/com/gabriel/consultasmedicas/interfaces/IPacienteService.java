package com.gabriel.consultasmedicas.interfaces;

import java.util.List;
import com.gabriel.consultasmedicas.dto.PacienteCadastroDTO; // NOVO IMPORT!
import com.gabriel.consultasmedicas.dto.PacienteResponseDTO;
import jakarta.validation.Valid;

public interface IPacienteService {
	
	PacienteResponseDTO criar(@Valid PacienteCadastroDTO dto);

	PacienteResponseDTO buscarPorId(Long id);

	PacienteResponseDTO buscarPorCpf(String cpf);

	PacienteResponseDTO atualizar(Long id, @Valid PacienteCadastroDTO dto);

	List<PacienteResponseDTO> listarTodos();

	void remover(Long id);

}