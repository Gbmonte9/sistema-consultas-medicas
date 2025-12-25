package com.gabriel.consultasmedicas.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id; 
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import org.hibernate.annotations.GenericGenerator; 


@Entity
@Table(name = "historicos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Historico {
    
    @Id
    @GeneratedValue(generator = "UUID") 
    @GenericGenerator(
        name = "UUID", 
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id; 
    
    @ManyToOne
    @JoinColumn(name = "consulta_id", nullable = false)
    private Consulta consulta; 

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(columnDefinition = "TEXT")
    private String receita;
    
    @Column(name = "data_registro", nullable = false)
    private LocalDateTime dataRegistro;
}