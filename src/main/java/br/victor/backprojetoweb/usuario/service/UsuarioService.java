package br.victor.backprojetoweb.usuario.service;

import br.victor.backprojetoweb.login.dto.LoginDTO;
import br.victor.backprojetoweb.perfil.dto.PerfilDTO;
import br.victor.backprojetoweb.usuario.model.Usuario;
import br.victor.backprojetoweb.usuario.dto.UsuarioUpdateDTO;

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
