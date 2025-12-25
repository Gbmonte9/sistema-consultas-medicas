package com.gabriel.consultasmedicas.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gabriel.consultasmedicas.model.Historico;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, UUID> {
    
    Optional<Historico> findByConsultaId(UUID consultaId);

    @Query("SELECT h FROM Historico h " +
           "WHERE h.consulta.paciente.id = :pacienteId " +
           "ORDER BY h.consulta.dataHora DESC")
    List<Historico> findByPacienteId(@Param("pacienteId") UUID pacienteId);
}