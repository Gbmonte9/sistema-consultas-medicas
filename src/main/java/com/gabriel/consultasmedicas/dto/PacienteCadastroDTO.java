package com.gabriel.consultasmedicas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

// O DTO de Cadastro do Paciente é usado quando o 'tipo' no UsuarioCadastroDTO é PACIENTE.
// Ele armazena os dados específicos da entidade Paciente.
@Data
public class PacienteCadastroDTO {

    // CPF (Obrigatório e único)
    @NotBlank(message = "O CPF é obrigatório")
    @Size(min = 11, max = 14, message = "O CPF deve ter entre 11 e 14 caracteres (com ou sem pontuação)")
    // Validação básica de formato. A validação de CPF válido deve ser feita na camada Service.
    @Pattern(regexp = "^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$|^\\d{11}$", message = "Formato de CPF inválido")
    private String cpf;

    // Telefone (Recomendado, mas pode ser opcional dependendo da regra de negócio)
    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$", message = "Formato de telefone/celular inválido")
    private String telefone;
}