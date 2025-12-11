package com.gabriel.consultasmedicas.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gabriel.consultasmedicas.model.Consulta;
import com.gabriel.consultasmedicas.model.StatusConsulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    // 1. Buscar todas as consultas de um paciente
    List<Consulta> findByPacienteId(Long pacienteId);

    // 2. Buscar todas as consultas de um médico
    List<Consulta> findByMedicoId(Long medicoId);

    // 3. Buscar agenda de um médico dentro de um período (para ver disponibilidade)
    List<Consulta> findByMedicoIdAndDataHoraBetween(Long medicoId, LocalDateTime inicio, LocalDateTime fim);

    // 4. Buscar consultas de um médico por status
    List<Consulta> findByMedicoIdAndStatus(Long medicoId, StatusConsulta status);

    // 5. Buscar consultas de um paciente por status
    List<Consulta> findByPacienteIdAndStatus(Long pacienteId, StatusConsulta status);

    // 6. Buscar consultas agendadas em um slot de tempo para verificar conflitos
    List<Consulta> findByMedicoIdAndDataHora(Long medicoId, LocalDateTime dataHora);

    // 7. Método customizado para checar disponibilidade (usa @Query)
    @Query("SELECT c FROM Consulta c WHERE c.medico.id = :medicoId AND c.dataHora BETWEEN :inicio AND :fim")
    List<Consulta> checarDisponibilidade(
            @Param("medicoId") Long medicoId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );
}
