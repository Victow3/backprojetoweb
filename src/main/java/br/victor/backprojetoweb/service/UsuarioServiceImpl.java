package br.victor.backprojetoweb.service;

import br.victor.backprojetoweb.dto.LoginDTO;
import br.victor.backprojetoweb.dto.PerfilDTO;
import br.victor.backprojetoweb.model.PerfilUsuario;
import br.victor.backprojetoweb.model.Usuario;
import br.victor.backprojetoweb.repository.UsuarioRepository;
import br.victor.backprojetoweb.exception.UsuarioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        // Aqui poderia criptografar a senha antes de salvar
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioException("Usuário não encontrado com id: " + id));
    }

    @Override
    @Transactional
    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

// Dentro de UsuarioServiceImpl

    // Buscar usuário por email
    @Transactional(readOnly = true)
    public Usuario buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsuarioException("Usuário não encontrado com email: " + email);
        }
        return usuario;
    }

    // Login com DTO
    @Transactional(readOnly = true)
    public Usuario login(LoginDTO loginDTO) {
        Usuario usuario = buscarPorEmail(loginDTO.getEmail());

        if (!usuario.getSenha().equals(loginDTO.getSenha())) {
            throw new UsuarioException("Senha incorreta");
        }

        return usuario; // podemos retornar o usuário, e o controller decide o que expor
    }

    // Buscar apenas o perfil do usuário
    @Transactional(readOnly = true)
    public PerfilDTO buscarPerfil(Long usuarioId) {
        Usuario usuario = buscarPorId(usuarioId);

        if (usuario.getPerfilUsuario() == null) {
            throw new UsuarioException("Usuário não possui perfil associado");
        }

        return new PerfilDTO(
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfilUsuario().getDescricao(),
                usuario.getPerfilUsuario().getNivelAcesso(),
                usuario.getDataCadastro()
        );
    }

}
