package com.gabriel.consultasmedicas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.consultasmedicas.model.Historico;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {
    List<Historico> findByConsultaId(Long consultaId);
}
