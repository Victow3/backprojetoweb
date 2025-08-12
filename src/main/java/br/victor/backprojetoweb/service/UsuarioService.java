package br.victor.backprojetoweb.service;

import br.victor.backprojetoweb.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario salvarUsuario(Usuario usuario);
    List<Usuario> listarUsuarios();
    Usuario buscarPorId(Long id);
    void deletarUsuario(Long id);
}
