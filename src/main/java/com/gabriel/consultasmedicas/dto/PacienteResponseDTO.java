package com.gabriel.consultasmedicas.dto;

import com.gabriel.consultasmedicas.model.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PacienteResponseDTO {
    private UUID id;
    private String nomeUsuario;
    private String emailUsuario;
    private TipoUsuario tipo;
    private String cpf;
    private String telefone;
}