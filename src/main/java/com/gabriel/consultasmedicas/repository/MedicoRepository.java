package com.gabriel.consultasmedicas.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID; 

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.consultasmedicas.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, UUID> {
    
    Optional<Medico> findByCrm(String crm);

    Optional<Medico> findByUsuarioId(UUID usuarioId);

    List<Medico> findByEspecialidade(String especialidade);
}