package com.gabriel.consultasmedicas.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.gabriel.consultasmedicas.interfaces.IPacienteService;
import com.gabriel.consultasmedicas.model.Paciente;
import com.gabriel.consultasmedicas.repository.PacienteRepository;

@Service
public class PacienteServiceImpl implements IPacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente cadastrarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public List listarTodos() {
        return pacienteRepository.findAll();
    }

    @Override
    public Paciente buscarPorId(Long id) {
        return pacienteRepository.findById(id).orElse(null);
    }

    @Override
    public Paciente salvar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public void remover(Long id) {
        pacienteRepository.deleteById(id);
    }
}
