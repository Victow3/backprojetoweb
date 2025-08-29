package br.victor.backprojetoweb.controller;

import br.victor.backprojetoweb.dto.*;
import br.victor.backprojetoweb.model.Usuario;
import br.victor.backprojetoweb.service.UsuarioService;
import br.victor.backprojetoweb.exception.UsuarioException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setDataCadastro(usuario.getDataCadastro());
        return dto;
    }

    private Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return usuario;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioDTO) {
        Usuario usuario = toEntity(usuarioDTO);
        Usuario novoUsuario = usuarioService.salvarUsuario(usuario);
        UsuarioResponseDTO responseDTO = toResponseDTO(novoUsuario);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioService.listarUsuarios().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            return ResponseEntity.ok(toResponseDTO(usuario));
        } catch (UsuarioException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Login com DTO correto
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            Usuario usuario = usuarioService.login(loginDTO);
            Long perfilId = (usuario.getPerfilUsuario() != null) ? usuario.getPerfilUsuario().getId() : null;

            LoginResponseDTO response = new LoginResponseDTO(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    perfilId
            );

            return ResponseEntity.ok(response);
        } catch (UsuarioException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/{id}/perfil")
    public ResponseEntity<PerfilDTO> getPerfil(@PathVariable Long id) {
        try {
            PerfilDTO perfil = usuarioService.buscarPerfil(id);
            return ResponseEntity.ok(perfil);
        } catch (UsuarioException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
