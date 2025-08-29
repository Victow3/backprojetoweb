package br.victor.backprojetoweb.repository;

import br.victor.backprojetoweb.model.PerfilJogo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerfilJogoRepository extends JpaRepository<PerfilJogo, Long> {
    List<PerfilJogo> findByPerfilUsuarioId(Long perfilId);
    List<PerfilJogo> findByPerfilUsuario_Usuario_Id(Long usuarioId);

}
