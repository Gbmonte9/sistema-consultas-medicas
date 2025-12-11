package com.gabriel.consultasmedicas.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO de Requisição de Autenticação.
 * Usado para receber o email e a senha do usuário no endpoint de login.
 */
@Data
public class AuthRequestDTO {

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    private String senha;

	public AuthRequestDTO(
			@NotBlank(message = "O email é obrigatório") @Email(message = "Formato de email inválido") String email,
			@NotBlank(message = "A senha é obrigatória") String senha) {
		super();
		this.email = email;
		this.senha = senha;
	}

	public AuthRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}	

}