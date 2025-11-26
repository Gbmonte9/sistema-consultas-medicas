package com.gabriel.consultasmedicas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.consultasmedicas.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    // 1. Usando Optional para lidar com a ausência do Paciente com aquele CPF
    Optional<Paciente> findByCpf(String cpf);

    // 2. Busca pelo ID do Usuário associado (útil após o login)
    Optional<Paciente> findByUsuarioId(Long usuarioId);
}