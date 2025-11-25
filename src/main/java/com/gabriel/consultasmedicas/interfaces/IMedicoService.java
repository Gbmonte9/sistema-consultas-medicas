package com.gabriel.consultasmedicas.interfaces;

import java.util.List;

import com.gabriel.consultasmedicas.dto.MedicoResponseDTO;
import com.gabriel.consultasmedicas.model.Medico;

public interface IMedicoService {
    Medico salvar(Medico medico);
    Medico buscarPorId(Long id);
    void remover(Long id);
	Medico cadastrarMedico(Medico medico);
	List<MedicoResponseDTO> listarTodos();
}
