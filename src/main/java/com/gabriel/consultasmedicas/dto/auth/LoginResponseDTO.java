package com.gabriel.consultasmedicas.dto.auth;

import java.util.UUID;

public class LoginResponseDTO {
    private String token;
    private UUID id;
    private String nome;
    private String email;
    private String role;
    private String telefone;
    
    private String cpf;
    
    private String crm;
    private String especialidade;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, UUID id, String nome, String email, String role, 
                            String telefone, String cpf, String crm, String especialidade) {
        this.token = token;
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.role = role;
        this.telefone = telefone;
        this.cpf = cpf;
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
}