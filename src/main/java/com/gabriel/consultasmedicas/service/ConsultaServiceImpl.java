package com.gabriel.consultasmedicas.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.gabriel.consultasmedicas.interfaces.IConsultaService;
import com.gabriel.consultasmedicas.model.Consulta;
import com.gabriel.consultasmedicas.repository.ConsultaRepository;

@Service
public class ConsultaServiceImpl implements IConsultaService {

    private final ConsultaRepository consultaRepository;

    public ConsultaServiceImpl(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    @Override
    public Consulta agendarConsulta(Consulta consulta) {
        return consultaRepository.save(consulta);
    }

    @Override
    public void cancelarConsulta(Long id) {
        Consulta consulta = consultaRepository.findById(id).orElse(null);
        if (consulta != null) {
            consulta.setStatus("CANCELADA");
            consultaRepository.save(consulta);
        }
    }

    @Override
    public List<Consulta> listarTodas() {
        return consultaRepository.findAll();
    }

    @Override
    public List<Consulta> listarPorMedico(Long medicoId) {
        return consultaRepository.findByMedicoId(medicoId);
    }

    @Override
    public List<Consulta> listarPorPaciente(Long pacienteId) {
        return consultaRepository.findByPacienteId(pacienteId);
    }
}
