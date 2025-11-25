package com.gabriel.consultasmedicas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.consultasmedicas.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Medico findByCrm(String crm);
}
