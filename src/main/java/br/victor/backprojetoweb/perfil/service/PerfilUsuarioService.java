package br.victor.backprojetoweb.perfil.service;

import br.victor.backprojetoweb.usuario.model.PerfilUsuario;
import br.victor.backprojetoweb.perfil.repository.PerfilUsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PerfilUsuarioService {

    private final PerfilUsuarioRepository perfilUsuarioRepository;

    public PerfilUsuarioService(PerfilUsuarioRepository perfilUsuarioRepository) {
        this.perfilUsuarioRepository = perfilUsuarioRepository;
    }

    public PerfilUsuario salvar(PerfilUsuario perfilUsuario) {
        return perfilUsuarioRepository.save(perfilUsuario);
    }

    public List<PerfilUsuario> listarTodos() {
        return perfilUsuarioRepository.findAll();
    }

    public PerfilUsuario buscarPorId(Long id) {
        return perfilUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
    }

    public void deletar(PerfilUsuario perfil) {
        perfilUsuarioRepository.delete(perfil);
    }

}
