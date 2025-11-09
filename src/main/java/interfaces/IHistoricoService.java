package interfaces;

import java.io.File;
import java.util.List;

import model.Historico;

public interface IHistoricoService {
    Historico salvar(Historico historico);
    Historico atualizar(Long id, Historico historico);
    List<Historico> listarTodos();
    List<Historico> listarPorConsulta(Long consultaId);
    Historico buscarPorId(Long id);
    void remover(Long id);
}
