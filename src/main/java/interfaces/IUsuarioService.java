package interfaces;

import model.Usuario;
import java.util.List;

public interface IUsuarioService {
    Usuario registrar(Usuario usuario);
    Usuario autenticar(String email, String senha);
    List<Usuario> listarTodos();
    void remover(Long id);
}
