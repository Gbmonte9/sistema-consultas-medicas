package com.gabriel.consultasmedicas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.consultasmedicas.model.Historico;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {
    
    // 1. Busca pelo ID da consulta. Usando Optional se a regra for 1 Histórico por Consulta.
    // Se a regra for N históricos por consulta, mantenha List<Historico>
    Optional<Historico> findByConsultaId(Long consultaId);

    // 2. Método útil para buscar todos os históricos de um Paciente (necessita de @Query)
    // List<Historico> findByConsultaPacienteId(Long pacienteId); 
    // ^ Este requer que você defina a navegação Paciente -> Consulta -> Historico
}