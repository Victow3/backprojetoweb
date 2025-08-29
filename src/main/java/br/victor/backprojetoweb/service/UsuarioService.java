package br.victor.backprojetoweb.service;

import br.victor.backprojetoweb.dto.LoginDTO;
import br.victor.backprojetoweb.dto.PerfilDTO;
import br.victor.backprojetoweb.dto.UsuarioUpdateDTO;
import br.victor.backprojetoweb.model.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario salvarUsuario(Usuario usuario);
    List<Usuario> listarUsuarios();
    Usuario buscarPorId(Long id);
    void deletarUsuario(Long id);
    Long buscarPerfilId(Long usuarioId);

    // 🔑 Novos métodos
    Usuario buscarPorEmail(String email);
    Usuario login(LoginDTO loginDTO);
    PerfilDTO buscarPerfil(Long id);
    Usuario atualizarUsuario(Long id, UsuarioUpdateDTO dto);
}
