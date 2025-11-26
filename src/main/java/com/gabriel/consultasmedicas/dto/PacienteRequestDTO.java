package com.gabriel.consultasmedicas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO de Requisição para Paciente.
 * Usado nos endpoints POST e PUT do PacienteController.
 */
@Data
public class PacienteRequestDTO {

    // CPF (Obrigatório e único)
    @NotBlank(message = "O CPF é obrigatório")
    @Size(min = 11, max = 14, message = "O CPF deve ter entre 11 e 14 caracteres (com ou sem pontuação)")
    @Pattern(regexp = "^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$|^\\d{11}$", message = "Formato de CPF inválido")
    private String cpf;

    // Telefone
    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$", message = "Formato de telefone/celular inválido")
    private String telefone;

    // Nota: Campos como 'nome' e 'email' do usuário associado
    // serão atualizados separadamente através do UsuarioController, se necessário.
}