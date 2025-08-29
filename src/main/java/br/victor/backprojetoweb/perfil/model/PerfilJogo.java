package br.victor.backprojetoweb.perfil.model;

import br.victor.backprojetoweb.game.model.StatusJogo;
import br.victor.backprojetoweb.usuario.model.PerfilUsuario;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class PerfilJogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // === Associação com usuário ===
    @ManyToOne
    @JoinColumn(name = "perfil_id", nullable = false)
    private PerfilUsuario perfilUsuario;

    // === Identificação do jogo ===
    private Long gameId; // ID do jogo (pode vir da IGDB)
    private String gameName; // redundância opcional para exibir mais rápido

    // === Status obrigatório ===
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusJogo status; // JOGOU, QUER_JOGAR, ABANDONOU

    // === Campos opcionais ===
    private Integer horasJogadas;
    private LocalDate dataInicio;
    private LocalDate dataUltimaVez;
    private LocalDate dataConclusao;
    private Double nota; // 0.5 a 5.0
    private String comentario;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PerfilUsuario getPerfilUsuario() { return perfilUsuario; }
    public void setPerfilUsuario(PerfilUsuario perfilUsuario) { this.perfilUsuario = perfilUsuario; }

    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }

    public String getGameName() { return gameName; }
    public void setGameName(String gameName) { this.gameName = gameName; }

    public StatusJogo getStatus() { return status; }
    public void setStatus(StatusJogo status) { this.status = status; }

    public Integer getHorasJogadas() { return horasJogadas; }
    public void setHorasJogadas(Integer horasJogadas) { this.horasJogadas = horasJogadas; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataUltimaVez() { return dataUltimaVez; }
    public void setDataUltimaVez(LocalDate dataUltimaVez) { this.dataUltimaVez = dataUltimaVez; }

    public LocalDate getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(LocalDate dataConclusao) { this.dataConclusao = dataConclusao; }

    public Double getNota() { return nota; }
    public void setNota(Double nota) { this.nota = nota; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}
