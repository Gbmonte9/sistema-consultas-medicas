package interfaces;

import java.util.List;

import model.Paciente;

public interface IPacienteService {
    Paciente salvar(Paciente paciente);
    List<Paciente> listarTodos();
    Paciente buscarPorId(Long id);
    void remover(Long id);
}
