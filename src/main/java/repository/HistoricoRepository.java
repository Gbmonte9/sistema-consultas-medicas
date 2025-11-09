package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import model.Historico;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {
    List<Historico> findByConsultaId(Long consultaId);
}
