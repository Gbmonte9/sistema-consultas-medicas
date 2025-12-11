package com.gabriel.consultasmedicas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MedicoCadastroDTO {

    @NotBlank(message = "O CRM é obrigatório")
    @Size(max = 50, message = "O CRM deve ter no máximo 50 caracteres")
    private String crm;

    @NotBlank(message = "A especialidade é obrigatória")
    @Size(max = 100, message = "A especialidade deve ter no máximo 100 caracteres")
    private String especialidade;
}