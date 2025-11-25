package com.gabriel.consultasmedicas.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "historicos")
public class Historico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "consulta_id", nullable = false)
    private Consulta consulta;
    @Column(columnDefinition = "TEXT")
    private String observacoes;
    @Column(columnDefinition = "TEXT")
    private String receita;
    @Column(name = "data_registro", nullable = false)
    private LocalDateTime dataRegistro;
    
    public Historico() {
        super();
        //TODO Auto-generated constructor stub
    }
    public Historico(Long id, Consulta consulta, String observacoes, String receita, LocalDateTime dataRegistro) {
        super();
        this.id = id;
        this.consulta = consulta;
        this.observacoes = observacoes;
        this.receita = receita;
        this.dataRegistro = dataRegistro;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Consulta getConsulta() {
        return consulta;
    }
    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }
    public String getObservacoes() {
        return observacoes;
    }
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    public String getReceita() {
        return receita;
    }
    public void setReceita(String receita) {
        this.receita = receita;
    }
    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }
    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
    
    
    
}
