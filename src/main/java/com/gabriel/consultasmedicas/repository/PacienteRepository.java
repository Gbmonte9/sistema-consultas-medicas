package com.gabriel.consultasmedicas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.consultasmedicas.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Paciente findByCpf(String cpf);
}
