package br.victor.backprojetoweb.perfil.dto;

import br.victor.backprojetoweb.perfil.model.PerfilJogo;
import br.victor.backprojetoweb.game.model.StatusJogo;
import java.time.LocalDate;

public class PerfilJogoDTO {
    private Long id;
    private String gameName;
    private StatusJogo status;
    private Integer horasJogadas;
    private Double nota;
    private String comentario;
    private LocalDate dataInicio;
    private LocalDate dataUltimaVez;
    private LocalDate dataConclusao;

    public PerfilJogoDTO(PerfilJogo pj) {
        this.id = pj.getId();
        this.gameName = pj.getGameName();
        this.status = pj.getStatus();
        this.horasJogadas = pj.getHorasJogadas();
        this.nota = pj.getNota();
        this.comentario = pj.getComentario();
        this.dataInicio = pj.getDataInicio();
        this.dataUltimaVez = pj.getDataUltimaVez();
        this.dataConclusao = pj.getDataConclusao();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public String getGameName() { return gameName; }
    public StatusJogo getStatus() { return status; }
    public Integer getHorasJogadas() { return horasJogadas; }
    public Double getNota() { return nota; }
    public String getComentario() { return comentario; }
    public LocalDate getDataInicio() { return dataInicio; }
    public LocalDate getDataUltimaVez() { return dataUltimaVez; }
    public LocalDate getDataConclusao() { return dataConclusao; }
}
