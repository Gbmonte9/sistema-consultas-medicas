package com.gabriel.consultasmedicas.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gabriel.consultasmedicas.model.Consulta;
import com.gabriel.consultasmedicas.model.Paciente; // Importante importar a entidade Paciente
import com.gabriel.consultasmedicas.model.StatusConsulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, UUID> {

    List<Consulta> findByMedicoIdAndStatus(UUID medicoId, StatusConsulta status);

    List<Consulta> findByPacienteIdAndStatus(UUID pacienteId, StatusConsulta status);

    List<Consulta> findByMedicoIdAndDataHora(UUID medicoId, LocalDateTime dataHora);
    
    List<Consulta> findByMedicoId(UUID medicoId);    
    
    List<Consulta> findByPacienteId(UUID pacienteId);

    /**
     * Busca todos os pacientes distintos que já tiveram consultas com um médico específico.
     * O 'DISTINCT' garante que a lista não tenha duplicatas.
     */
    @Query("SELECT DISTINCT c.paciente FROM Consulta c WHERE c.medico.id = :medicoId")
    List<Paciente> findDistinctPacientesByMedicoId(@Param("medicoId") UUID medicoId);
    
    /**
     * Verifica se o médico possui consultas AGENDADAS que conflitam com o horário solicitado.
     */
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