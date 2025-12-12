package com.gabriel.consultasmedicas.repository;

import java.util.Optional;
import java.util.UUID; 

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.consultasmedicas.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, UUID> {
    
    Optional<Paciente> findByCpf(String cpf);

    Optional<Paciente> findByUsuarioId(UUID usuarioId);
}