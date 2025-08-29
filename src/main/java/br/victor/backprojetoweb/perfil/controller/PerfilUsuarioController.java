package br.victor.backprojetoweb.perfil.controller;

import br.victor.backprojetoweb.usuario.model.PerfilUsuario;
import br.victor.backprojetoweb.perfil.service.PerfilUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfis")
public class PerfilUsuarioController {

    private final PerfilUsuarioService perfilUsuarioService;

    public PerfilUsuarioController(PerfilUsuarioService perfilUsuarioService) {
        this.perfilUsuarioService = perfilUsuarioService;
    }

    @PostMapping
    public ResponseEntity<PerfilUsuario> criarPerfil(@RequestBody PerfilUsuario perfil) {
        PerfilUsuario novoPerfil = perfilUsuarioService.salvar(perfil);
        return new ResponseEntity<>(novoPerfil, HttpStatus.CREATED);
    }

    @GetMapping
    public List<PerfilUsuario> listarPerfis() {
        return perfilUsuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilUsuario> buscarPorId(@PathVariable Long id) {
        try {
            PerfilUsuario perfil = perfilUsuarioService.buscarPorId(id);
            return ResponseEntity.ok(perfil);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfilUsuario> atualizarPerfil(@PathVariable Long id, @RequestBody PerfilUsuario perfilAtualizado) {
        try {
            PerfilUsuario perfilExistente = perfilUsuarioService.buscarPorId(id);
            perfilExistente.setDescricao(perfilAtualizado.getDescricao());
            perfilExistente.setNivelAcesso(perfilAtualizado.getNivelAcesso());
            PerfilUsuario perfilSalvo = perfilUsuarioService.salvar(perfilExistente);
            return ResponseEntity.ok(perfilSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPerfil(@PathVariable Long id) {
        try {
            PerfilUsuario perfil = perfilUsuarioService.buscarPorId(id);
            perfilUsuarioService.deletar(perfil);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
