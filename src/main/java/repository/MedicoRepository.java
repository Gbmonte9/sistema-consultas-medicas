package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Medico findByCrm(String crm);
}
