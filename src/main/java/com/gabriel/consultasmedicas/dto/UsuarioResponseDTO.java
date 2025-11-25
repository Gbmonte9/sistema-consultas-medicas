package com.gabriel.consultasmedicas.dto;

import com.gabriel.consultasmedicas.model.Usuario.TipoUsuario;
import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private TipoUsuario tipo;
	public void setId(Long id2) {
		// TODO Auto-generated method stub
		
	}
	public void setNome(String nome2) {
		// TODO Auto-generated method stub
		
	}
	public void setEmail(String email2) {
		// TODO Auto-generated method stub
		
	}
	public void setTipo(TipoUsuario tipo2) {
		// TODO Auto-generated method stub
		
	}
}