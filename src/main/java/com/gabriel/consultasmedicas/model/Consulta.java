package com.gabriel.consultasmedicas.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "consultas")
public class Consulta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConsulta status;
    
    public Consulta() {
        super();
        //TODO Auto-generated constructor stub
    }

    public Consulta(Long id, Medico medico, Paciente paciente, LocalDateTime dataHora, StatusConsulta status) {
        super();
        this.id = id;
        this.medico = medico;
        this.paciente = paciente;
        this.dataHora = dataHora;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public void setStatus(StatusConsulta status) {
        this.status = status;
    }

    // Enum para status da consulta
    public enum StatusConsulta {
        AGENDADA,
        CANCELADA,
        REALIZADA
    }

	public void setStatus(String string) {
		// TODO Auto-generated method stub
		
	}
    
}
