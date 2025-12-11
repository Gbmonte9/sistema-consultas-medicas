package com.gabriel.consultasmedicas.interfaces;

import java.util.List;
import com.gabriel.consultasmedicas.dto.PacienteRequestDTO;
import com.gabriel.consultasmedicas.dto.PacienteResponseDTO;
import jakarta.validation.Valid;

public interface IPacienteService {
    
    PacienteResponseDTO criar(@Valid PacienteRequestDTO requestDTO);

    PacienteResponseDTO buscarPorId(Long id);

    PacienteResponseDTO buscarPorCpf(String cpf);

    PacienteResponseDTO atualizar(Long id, @Valid PacienteRequestDTO requestDTO);

    List<PacienteResponseDTO> listarTodos();

    void remover(Long id);
}
