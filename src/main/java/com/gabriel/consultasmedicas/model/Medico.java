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
import org.hibernate.annotations.GenericGenerator; 


@Entity
@Table(name = "medicos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Medico {
    
    @Id
    @GeneratedValue(generator = "UUID") 
    @GenericGenerator(
        name = "UUID", 
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id; 
    
    @Column(length = 50, nullable = false, unique = true)
    private String crm;
    
    @Column(length = 100, nullable = false)
    private String especialidade;
    
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    
}