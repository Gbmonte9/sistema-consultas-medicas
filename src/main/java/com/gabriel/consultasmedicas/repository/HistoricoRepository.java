package com.gabriel.consultasmedicas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.consultasmedicas.model.Historico;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {
    
    Optional<Historico> findByConsultaId(Long consultaId);

}