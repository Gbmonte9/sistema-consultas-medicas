package com.gabriel.consultasmedicas.interfaces;

import com.gabriel.consultasmedicas.dto.auth.LoginRequestDTO;
import com.gabriel.consultasmedicas.dto.auth.LoginResponseDTO;
import com.gabriel.consultasmedicas.model.Usuario;

public interface IAuthService {
    
    Usuario autenticar(LoginRequestDTO requestDTO);
    
    LoginResponseDTO autenticarEGerarToken(LoginRequestDTO requestDTO);
}