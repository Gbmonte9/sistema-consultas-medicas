package com.gabriel.consultasmedicas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.UUID;

@Data
public class MedicoCadastroDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome; 

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String senha;

    @NotBlank(message = "O CRM é obrigatório")
    @Size(max = 50, message = "O CRM deve ter no máximo 50 caracteres")
    private String crm;

    @NotBlank(message = "A especialidade é obrigatória")
    @Size(max = 100, message = "A especialidade deve ter no máximo 100 caracteres")
    private String especialidade;
}