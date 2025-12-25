package com.gabriel.consultasmedicas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.consultasmedicas.model.TipoUsuario;
import com.gabriel.consultasmedicas.model.Usuario;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
	
	Optional<Usuario> findByEmail(String email);
	
	List<Usuario> findByTipo(TipoUsuario tipo);
}