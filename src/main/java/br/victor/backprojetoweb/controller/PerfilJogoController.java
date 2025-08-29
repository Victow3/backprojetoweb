package br.victor.backprojetoweb.controller;

import br.victor.backprojetoweb.dto.PerfilJogoDTO;
import br.victor.backprojetoweb.dto.PerfilJogoUpdateDTO;
import br.victor.backprojetoweb.model.PerfilJogo;
import br.victor.backprojetoweb.service.PerfilJogoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/perfil-jogos")
@CrossOrigin(origins = "http://localhost:5500") // ajuste conforme necessário
public class PerfilJogoController {

    private final PerfilJogoService service;

    public PerfilJogoController(PerfilJogoService service) {
        this.service = service;
    }

    @PostMapping
    public PerfilJogo cadastrar(@RequestBody PerfilJogo perfilJogo) {
        return service.salvar(perfilJogo);
    }

    @GetMapping("/perfil/{perfilId}")
    public List<PerfilJogoDTO> listarPorPerfil(@PathVariable Long perfilId) {
        return service.listarPorPerfil(perfilId)
                .stream()
                .map(PerfilJogoDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<PerfilJogoDTO> listarPorUsuario(@PathVariable Long usuarioId) {
        return service.listarPorUsuario(usuarioId)
                .stream()
                .map(PerfilJogoDTO::new)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        service.deletar(id);
    }

    @PutMapping("/{id}")
    public PerfilJogoDTO atualizar(@PathVariable Long id, @RequestBody PerfilJogoUpdateDTO dto) {
        PerfilJogo atualizado = service.atualizar(id, dto);
        return new PerfilJogoDTO(atualizado);
    }
}
