package com.gabriel.consultasmedicas.dto;

import com.gabriel.consultasmedicas.model.Usuario.TipoUsuario;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class UsuarioRequestDTO {
    @NotBlank
    private String nome;
    
    @NotBlank
    private String email;
    
    @NotBlank
    private String senha;
    
    @NotNull
    private TipoUsuario tipo; // ADMIN, MEDICO, PACIENTE

	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNome() {
		// TODO Auto-generated method stub
		return null;
	}

	public TipoUsuario getTipo() {
		// TODO Auto-generated method stub
		return null;
	}

	public CharSequence getSenha() {
		// TODO Auto-generated method stub
		return null;
	}
}