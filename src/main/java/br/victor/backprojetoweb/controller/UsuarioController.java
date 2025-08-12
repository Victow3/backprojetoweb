package br.victor.backprojetoweb.controller;

import br.victor.backprojetoweb.dto.UsuarioRequestDTO;
import br.victor.backprojetoweb.dto.UsuarioResponseDTO;
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

    // Mapeamento helper: Usuario -> UsuarioResponseDTO
    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setDataCadastro(usuario.getDataCadastro());
        return dto;
    }

    // Mapeamento helper: UsuarioRequestDTO -> Usuario
    private Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return usuario;
    }

    // Criar novo usuário (POST /api/usuarios)
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioDTO) {
        Usuario usuario = toEntity(usuarioDTO);
        Usuario novoUsuario = usuarioService.salvarUsuario(usuario);
        UsuarioResponseDTO responseDTO = toResponseDTO(novoUsuario);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // Listar todos usuários (GET /api/usuarios)
    @GetMapping
    public List<UsuarioResponseDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return usuarios.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Buscar usuário por id (GET /api/usuarios/{id})
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            UsuarioResponseDTO dto = toResponseDTO(usuario);
            return ResponseEntity.ok(dto);
        } catch (UsuarioException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar usuário (DELETE /api/usuarios/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
