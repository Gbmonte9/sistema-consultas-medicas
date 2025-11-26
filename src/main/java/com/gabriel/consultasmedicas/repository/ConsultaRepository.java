package com.gabriel.consultasmedicas.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.consultasmedicas.model.Consulta;
import com.gabriel.consultasmedicas.model.StatusConsulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
    
    // 1. Buscar todas as consultas de um paciente
    List<Consulta> findByPacienteId(Long pacienteId);
    
    // 2. Buscar todas as consultas de um médico
    List<Consulta> findByMedicoId(Long medicoId);
    
    // 3. Buscar agenda de um médico dentro de um período (para ver disponibilidade)
    List<Consulta> findByMedicoIdAndDataHoraBetween(Long medicoId, LocalDateTime inicio, LocalDateTime fim);
    
    // 4. NOVO: Buscar consultas de um médico por status (ex: AGENDADA)
    List<Consulta> findByMedicoIdAndStatus(Long medicoId, StatusConsulta status);

    // 5. NOVO: Buscar consultas de um paciente por status (ex: CANCELADA)
    List<Consulta> findByPacienteIdAndStatus(Long pacienteId, StatusConsulta status);
    
    // 6. NOVO: Buscar consultas agendadas em um slot de tempo para verificar conflitos (ideal para Service)
    List<Consulta> findByMedicoIdAndDataHora(Long medicoId, LocalDateTime dataHora);
}