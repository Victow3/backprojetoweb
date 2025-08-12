package br.victor.backprojetoweb.service;

import br.victor.backprojetoweb.model.Usuario;
import br.victor.backprojetoweb.repository.UsuarioRepository;
import br.victor.backprojetoweb.exception.UsuarioException;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // Injeção pelo construtor (recomendado)
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        // Aqui você pode adicionar regras antes de salvar, ex: criptografar senha
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioException("Usuário não encontrado com id: " + id));
    }

    @Override
    @Transactional
    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}