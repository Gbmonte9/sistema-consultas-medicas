package com.gabriel.consultasmedicas.interfaces;

import com.gabriel.consultasmedicas.dto.auth.AuthRequestDTO;
import com.gabriel.consultasmedicas.dto.auth.AuthResponseDTO;
import com.gabriel.consultasmedicas.dto.auth.LoginSuccessDTO; 
import com.gabriel.consultasmedicas.model.Usuario;


public interface IAuthService {
	
	AuthResponseDTO autenticarEGerarToken(AuthRequestDTO loginDTO);

	Usuario autenticar(AuthRequestDTO requestDTO);
}