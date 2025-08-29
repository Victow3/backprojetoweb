package br.victor.backprojetoweb.controller;

import br.victor.backprojetoweb.dto.GameDTO;
import br.victor.backprojetoweb.service.IgdbService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final IgdbService igdbService;

    public GameController(IgdbService igdbService) {
        this.igdbService = igdbService;
    }

    @GetMapping
    public List<GameDTO> listarJogos(
            @RequestParam(defaultValue = "60") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        return igdbService.buscarJogos(limit, offset);
    }

    @GetMapping("/buscar")
    public List<GameDTO> buscarJogo(
            @RequestParam String nome,
            @RequestParam(defaultValue = "60") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        return igdbService.buscarJogosPorNome(nome, limit, offset);
    }

    @GetMapping("/{id}")
    public GameDTO buscarPorId(@PathVariable Long id) {
        return igdbService.buscarPorId(id);
    }
}
