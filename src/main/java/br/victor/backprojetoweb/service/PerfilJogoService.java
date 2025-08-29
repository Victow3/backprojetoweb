package br.victor.backprojetoweb.service;

import br.victor.backprojetoweb.dto.PerfilJogoUpdateDTO;
import br.victor.backprojetoweb.model.PerfilJogo;
import br.victor.backprojetoweb.model.PerfilUsuario;
import br.victor.backprojetoweb.model.StatusJogo;
import br.victor.backprojetoweb.repository.PerfilJogoRepository;
import br.victor.backprojetoweb.repository.PerfilUsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PerfilJogoService {

    private final PerfilJogoRepository perfilJogoRepository;
    private final PerfilUsuarioRepository perfilUsuarioRepository;

    public PerfilJogoService(PerfilJogoRepository perfilJogoRepository,
                             PerfilUsuarioRepository perfilUsuarioRepository) {
        this.perfilJogoRepository = perfilJogoRepository;
        this.perfilUsuarioRepository = perfilUsuarioRepository;
    }

    @Transactional
    public PerfilJogo salvar(PerfilJogo perfilJogo) {
        Long perfilId = perfilJogo.getPerfilUsuario().getId();
        PerfilUsuario perfil = perfilUsuarioRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado com id: " + perfilId));
        perfilJogo.setPerfilUsuario(perfil);
        return perfilJogoRepository.save(perfilJogo);
    }

    @Transactional(readOnly = true)
    public List<PerfilJogo> listarPorPerfil(Long perfilId) {
        return perfilJogoRepository.findByPerfilUsuarioId(perfilId);
    }

    @Transactional(readOnly = true)
    public List<PerfilJogo> listarPorUsuario(Long usuarioId) {
        return perfilJogoRepository.findByPerfilUsuario_Usuario_Id(usuarioId);
    }

    @Transactional
    public void deletar(Long id) {
        perfilJogoRepository.deleteById(id);
    }

    @Transactional
    public PerfilJogo atualizar(Long id, PerfilJogoUpdateDTO dto) {
        PerfilJogo jogo = perfilJogoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado com id: " + id));

        if (dto.getStatus() != null && !dto.getStatus().isBlank()) {
            jogo.setStatus(StatusJogo.valueOf(dto.getStatus().trim().toUpperCase()));
        }
        if (dto.getHorasJogadas() != null) jogo.setHorasJogadas(dto.getHorasJogadas());
        if (dto.getNota() != null) jogo.setNota(dto.getNota());
        if (dto.getComentario() != null) jogo.setComentario(dto.getComentario());

        return perfilJogoRepository.save(jogo);
    }
}
