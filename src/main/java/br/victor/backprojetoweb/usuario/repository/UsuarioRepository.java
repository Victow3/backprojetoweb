package br.victor.backprojetoweb.usuario.repository;

import br.victor.backprojetoweb.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Você pode criar consultas customizadas aqui, se quiser

    // Por exemplo, procurar usuário por email:
    Usuario findByEmail(String email);

}
