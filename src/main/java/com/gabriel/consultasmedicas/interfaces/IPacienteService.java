package com.gabriel.consultasmedicas.interfaces;

import java.util.List;

import com.gabriel.consultasmedicas.model.Paciente;

public interface IPacienteService {
    Paciente salvar(Paciente paciente);
    List<Paciente> listarTodos();
    Paciente buscarPorId(Long id);
    void remover(Long id);
	Paciente cadastrarPaciente(Paciente paciente);
}
