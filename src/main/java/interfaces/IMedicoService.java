package interfaces;

import java.util.List;

import model.Medico;

public interface IMedicoService {
    Medico salvar(Medico medico);
    List<Medico> listarTodos();
    Medico buscarPorId(Long id);
    void remover(Long id);
}
