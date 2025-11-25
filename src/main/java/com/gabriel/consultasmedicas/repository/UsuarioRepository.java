package com.gabriel.consultasmedicas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.consultasmedicas.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	// CORRIGIDO: Removido findByEmail1, mantido apenas o método correto.
	/**
	 * Busca um usuário no banco de dados com base no email fornecido.
	 * O Spring Data JPA cria a consulta automaticamente.
	 * @param email O email a ser buscado.
	 * @return Um Optional contendo o Usuario, se encontrado.
	 */
	Optional<Usuario> findByEmail(String email);
}