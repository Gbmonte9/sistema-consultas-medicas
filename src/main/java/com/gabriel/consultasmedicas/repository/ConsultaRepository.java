package com.gabriel.consultasmedicas.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gabriel.consultasmedicas.model.Consulta;
import com.gabriel.consultasmedicas.model.Paciente;
import com.gabriel.consultasmedicas.model.StatusConsulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, UUID> {

    List<Consulta> findByMedicoId(UUID medicoId);
    
    List<Consulta> findByPacienteId(UUID pacienteId);

    List<Consulta> findByMedicoIdAndStatus(UUID medicoId, StatusConsulta status);

    List<Consulta> findByPacienteIdAndStatus(UUID pacienteId, StatusConsulta status);

    long countByMedicoIdAndDataHoraBetween(UUID medicoId, LocalDateTime inicio, LocalDateTime fim);

    long countByMedicoIdAndStatus(UUID medicoId, StatusConsulta status);

    @Query("SELECT c FROM Consulta c WHERE c.medico.id = :medicoId " +
           "AND c.dataHora >= :inicio AND c.dataHora <= :fim " +
           "AND c.status = :status ORDER BY c.dataHora ASC")
    List<Consulta> findAgendaDoDia(
        @Param("medicoId") UUID medicoId, 
        @Param("inicio") LocalDateTime inicio, 
        @Param("fim") LocalDateTime fim,
        @Param("status") StatusConsulta status
    );


    @Query("SELECT DISTINCT c.paciente FROM Consulta c WHERE c.medico.id = :medicoId")
    List<Paciente> findDistinctPacientesByMedicoId(@Param("medicoId") UUID medicoId);
    
    @Query("SELECT c FROM Consulta c " +
           "WHERE c.medico.id = :medicoId " +
           "AND c.status = 'AGENDADA' " +
           "AND c.dataHora < :dataHoraFim " + 
           "AND c.dataFim > :dataHoraInicio")
    List<Consulta> checarDisponibilidade(
        @Param("medicoId") UUID medicoId, 
        @Param("dataHoraInicio") LocalDateTime dataHoraInicio, 
        @Param("dataHoraFim") LocalDateTime dataHoraFim
    );
}