package br.victor.backprojetoweb.game.dto;

import java.time.LocalDate;

public class GameDTO {
    private Long id;
    private String name;
    private String coverUrl;
    private LocalDate releaseDate;
    private String summary;

    // Construtor vazio (necessário para Jackson/JSON)
    public GameDTO() {}

    // Construtor completo
    public GameDTO(Long id, String name, String coverUrl, LocalDate releaseDate, String summary) {
        this.id = id;
        this.name = name;
        this.coverUrl = coverUrl;
        this.releaseDate = releaseDate;
        this.summary = summary;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
}
