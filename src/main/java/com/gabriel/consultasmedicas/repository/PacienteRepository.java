package com.gabriel.consultasmedicas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.consultasmedicas.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    Optional<Paciente> findByCpf(String cpf);

    Optional<Paciente> findByUsuarioId(Long usuarioId);
}