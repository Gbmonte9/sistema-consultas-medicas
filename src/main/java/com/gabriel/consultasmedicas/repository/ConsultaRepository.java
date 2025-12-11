package com.gabriel.consultasmedicas.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.consultasmedicas.model.Consulta;
import com.gabriel.consultasmedicas.model.StatusConsulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findByPacienteId(Long pacienteId);

    List<Consulta> findByMedicoId(Long medicoId);

    List<Consulta> findByMedicoIdAndDataHoraBetween(Long medicoId, LocalDateTime inicio, LocalDateTime fim);

    List<Consulta> findByMedicoIdAndStatus(Long medicoId, StatusConsulta status);

    List<Consulta> findByPacienteIdAndStatus(Long pacienteId, StatusConsulta status);

    List<Consulta> findByMedicoIdAndDataHora(Long medicoId, LocalDateTime dataHora);
}