package com.gabriel.consultasmedicas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID; 
import org.hibernate.annotations.GenericGenerator; 

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
    
	@Id
    @GeneratedValue(generator = "UUID") 
    @GenericGenerator(
        name = "UUID", 
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id; 

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoUsuario tipo;
    
}