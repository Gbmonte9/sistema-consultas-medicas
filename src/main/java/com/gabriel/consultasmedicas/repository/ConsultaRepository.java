package com.gabriel.consultasmedicas.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gabriel.consultasmedicas.model.Consulta;
import com.gabriel.consultasmedicas.model.StatusConsulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findByMedicoIdAndStatus(Long medicoId, StatusConsulta status);

    List<Consulta> findByPacienteIdAndStatus(Long pacienteId, StatusConsulta status);

    List<Consulta> findByMedicoIdAndDataHora(Long medicoId, LocalDateTime dataHora);
    
    List<Consulta> findByMedicoId(Long medicoId);    
    
    List<Consulta> findByPacienteId(Long pacienteId);
    
   
    @Query("SELECT c FROM Consulta c " +
           "WHERE c.medico.id = :medicoId " +
           "AND c.status = 'AGENDADA' " +
           "AND c.dataHora < :dataHoraFim " + 
           "AND c.dataFim > :dataHoraInicio")
    List<Consulta> checarDisponibilidade(
        @Param("medicoId") Long medicoId, 
        @Param("dataHoraInicio") LocalDateTime dataHoraInicio, 
        @Param("dataHoraFim") LocalDateTime dataHoraFim
    );
}