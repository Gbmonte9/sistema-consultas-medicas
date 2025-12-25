package com.gabriel.consultasmedicas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "pacientes")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Paciente {
    
    @Id
    @GeneratedValue 
    private UUID id; 

    @Column(name = "cpf", nullable = false, unique = true, length = 255) 
    private String cpf;
    
    @Column(length = 20)
    private String telefone;
    
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

}