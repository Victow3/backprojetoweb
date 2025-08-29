package br.victor.backprojetoweb.repository;

import br.victor.backprojetoweb.model.PerfilUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilUsuarioRepository extends JpaRepository<PerfilUsuario, Long> {
        Optional<PerfilUsuario> findByUsuarioId(Long usuarioId);

}
