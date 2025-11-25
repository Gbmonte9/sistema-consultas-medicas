package com.gabriel.consultasmedicas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nome;
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    @Column(nullable = false, length = 255)
    private String senha;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoUsuario tipo;
    
    public enum TipoUsuario {
        ADMIN,
        MEDICO,
        PACIENTE
    }
    
    public Usuario() {
        super();
        //TODO Auto-generated constructor stub
    }

    public Usuario(Long id, String nome, String email, String senha, TipoUsuario tipo) {
        super();
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }
    
    public boolean isAdmin() {
        return this.tipo == TipoUsuario.ADMIN;
    }

    public boolean isMedico() {
        return this.tipo == TipoUsuario.MEDICO;
    }

    public boolean isPaciente() {
        return this.tipo == TipoUsuario.PACIENTE;
    }
    
    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nome='" + nome + "', email='" + email + "', tipo=" + tipo + "}";
    }

	public boolean isPresent() {
		// TODO Auto-generated method stub
		return false;
	}

}
