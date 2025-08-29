package br.victor.backprojetoweb.usuario.controller;

import br.victor.backprojetoweb.exception.UsuarioException;
import br.victor.backprojetoweb.login.dto.LoginDTO;
import br.victor.backprojetoweb.login.dto.LoginResponseDTO;
import br.victor.backprojetoweb.perfil.dto.PerfilDTO;
import br.victor.backprojetoweb.usuario.model.Usuario;
import br.victor.backprojetoweb.usuario.dto.UsuarioRequestDTO;
import br.victor.backprojetoweb.usuario.dto.UsuarioResponseDTO;
import br.victor.backprojetoweb.usuario.dto.UsuarioUpdateDTO;
import br.victor.backprojetoweb.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5500") // ajuste se precisar
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Converte Entity -> ResponseDTO
    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setDataCadastro(usuario.getDataCadastro());
        return dto;
    }

    // Converte RequestDTO -> Entity
    private Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return usuario;
    }

    // Criar novo usuário
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioDTO) {
        Usuario usuario = toEntity(usuarioDTO);
        Usuario novoUsuario = usuarioService.salvarUsuario(usuario);
        UsuarioResponseDTO responseDTO = toResponseDTO(novoUsuario);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // Listar todos (usando DTO)
    @GetMapping
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioService.listarUsuarios().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            return ResponseEntity.ok(toResponseDTO(usuario));
        } catch (UsuarioException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // Atualizar (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioUpdateDTO usuarioDTO) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            usuario.setNome(usuarioDTO.getNome());
            usuario.setEmail(usuarioDTO.getEmail());

            Usuario atualizado = usuarioService.salvarUsuario(usuario);
            return ResponseEntity.ok(toResponseDTO(atualizado));
        } catch (UsuarioException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // Login
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

    // Buscar perfil pelo usuário
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
