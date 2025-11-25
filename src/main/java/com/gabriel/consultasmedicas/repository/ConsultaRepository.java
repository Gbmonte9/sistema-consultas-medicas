package com.gabriel.consultasmedicas.repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.consultasmedicas.model.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
    List<Consulta> findByPacienteId(Long pacienteId);
    List<Consulta> findByMedicoIdAndDataHoraBetween(Long medicoId, LocalDateTime inicio, LocalDateTime fim);
	List<Consulta> findByMedicoId(Long medicoId);
}
