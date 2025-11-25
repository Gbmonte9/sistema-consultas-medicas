package com.gabriel.consultasmedicas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.consultasmedicas.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByEmail(String email);
}
