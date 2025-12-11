package com.gabriel.consultasmedicas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pacientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // CPF como chave única, comprimento 14 (para formatação, ex: 123.456.789-00)
    @Column(length = 14, nullable = false, unique = true)
    private String cpf;
    
    @Column(length = 20)
    private String telefone;
    
    // Relacionamento OneToOne: Paciente possui um campo de FK (usuario_id) que aponta para Usuario
    // Este é o lado "dono" (owning side) do relacionamento.
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // NENHUM MÉTODO MANUAL É NECESSÁRIO GRAÇAS AO LOMBOK
}