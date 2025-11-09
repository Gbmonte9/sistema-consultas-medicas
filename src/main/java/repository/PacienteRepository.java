package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Paciente findByCpf(String cpf);
}
