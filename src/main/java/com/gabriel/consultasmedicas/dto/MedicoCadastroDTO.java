package com.gabriel.consultasmedicas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

// O DTO de Cadastro do Médico é usado quando o 'tipo' no UsuarioCadastroDTO é MEDICO.
// Ele armazena os dados específicos da entidade Medico.
@Data
public class MedicoCadastroDTO {

    // CRM (Obrigatório e único)
    @NotBlank(message = "O CRM é obrigatório")
    @Size(max = 50, message = "O CRM deve ter no máximo 50 caracteres")
    private String crm;

    // Especialidade (Obrigatório)
    @NotBlank(message = "A especialidade é obrigatória")
    @Size(max = 100, message = "A especialidade deve ter no máximo 100 caracteres")
    private String especialidade;
}