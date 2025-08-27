package br.victor.backprojetoweb.dto;

import java.time.LocalDate;

public class GameDTO {
    private String name;
    private String coverUrl;
    private LocalDate releaseDate;

    public GameDTO() {}

    public GameDTO(String name, String coverUrl, LocalDate releaseDate) {
        this.name = name;
        this.coverUrl = coverUrl;
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}