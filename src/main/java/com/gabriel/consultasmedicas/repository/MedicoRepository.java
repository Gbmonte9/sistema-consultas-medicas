package com.gabriel.consultasmedicas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.consultasmedicas.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    
    // 1. Usando Optional para lidar com a ausência do Médico com aquele CRM
    Optional<Medico> findByCrm(String crm);

    // 2. Busca pelo ID do Usuário associado (útil após o login)
    Optional<Medico> findByUsuarioId(Long usuarioId);

    // 3. Busca para listagem e filtros na tela de agendamento
    List<Medico> findByEspecialidade(String especialidade);
}