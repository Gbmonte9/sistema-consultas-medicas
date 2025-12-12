package com.gabriel.consultasmedicas.repository;

import java.util.Optional;
import java.util.UUID; 

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.consultasmedicas.model.Historico;

public interface HistoricoRepository extends JpaRepository<Historico, UUID> {
    
    Optional<Historico> findByConsultaId(UUID consultaId);

}