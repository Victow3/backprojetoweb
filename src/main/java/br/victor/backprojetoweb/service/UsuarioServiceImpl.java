package br.victor.backprojetoweb.service;

import br.victor.backprojetoweb.dto.LoginDTO;
import br.victor.backprojetoweb.dto.PerfilDTO;
import br.victor.backprojetoweb.dto.UsuarioUpdateDTO;
import br.victor.backprojetoweb.exception.UsuarioException;
import br.victor.backprojetoweb.model.PerfilUsuario;
import br.victor.backprojetoweb.model.Usuario;
import br.victor.backprojetoweb.repository.PerfilUsuarioRepository;
import br.victor.backprojetoweb.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilUsuarioRepository perfilUsuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              PerfilUsuarioRepository perfilUsuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.perfilUsuarioRepository = perfilUsuarioRepository;
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        // Cria automaticamente um PerfilUsuario se não houver
        if (usuario.getPerfilUsuario() == null) {
            PerfilUsuario perfil = new PerfilUsuario();
            perfil.setUsuario(usuario);

            // valores padrão
            perfil.setDescricao("Perfil padrão");
            perfil.setNivelAcesso("USUARIO");

            usuario.setPerfilUsuario(perfil);
        }

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

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) throw new UsuarioException("Usuário não encontrado com email: " + email);
        return usuario;
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario login(LoginDTO loginDTO) {
        Usuario usuario = buscarPorEmail(loginDTO.getEmail());
        if (!usuario.getSenha().equals(loginDTO.getSenha())) {
            throw new UsuarioException("Senha incorreta");
        }
        return usuario;
    }

    @Override
    @Transactional(readOnly = true)
    public PerfilDTO buscarPerfil(Long usuarioId) {
        Usuario usuario = buscarPorId(usuarioId);
        if (usuario.getPerfilUsuario() == null)
            throw new UsuarioException("Usuário não possui perfil associado");

        return new PerfilDTO(
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfilUsuario().getDescricao(),
                usuario.getPerfilUsuario().getNivelAcesso(),
                usuario.getDataCadastro()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Long buscarPerfilId(Long usuarioId) {
        return perfilUsuarioRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new UsuarioException("Perfil não encontrado"))
                .getId();
    }

    @Override
    @Transactional
    public Usuario atualizarUsuario(Long id, UsuarioUpdateDTO dto) {
        Usuario usuario = buscarPorId(id); // busca no banco
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        return usuarioRepository.save(usuario);
    }
}
