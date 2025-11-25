package com.gabriel.consultasmedicas.interfaces;

import java.util.List;

import com.gabriel.consultasmedicas.model.Consulta;

public interface IConsultaService {
    Consulta agendarConsulta(Consulta consulta);
    void cancelarConsulta(Long id);
    List<Consulta> listarTodas();
    List<Consulta> listarPorMedico(Long medicoId);
    List<Consulta> listarPorPaciente(Long pacienteId);
}
