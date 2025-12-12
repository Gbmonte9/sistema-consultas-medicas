package com.gabriel.consultasmedicas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.consultasmedicas.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    
    Optional<Medico> findByCrm(String crm);

    Optional<Medico> findByUsuarioId(Long usuarioId);

    List<Medico> findByEspecialidade(String especialidade);
}