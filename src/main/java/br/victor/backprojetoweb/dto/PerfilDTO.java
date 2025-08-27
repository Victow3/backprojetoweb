package br.victor.backprojetoweb.dto;

import java.time.LocalDateTime;

public class PerfilDTO {
    private String nome;
    private String email;
    private String descricaoPerfil;
    private String nivelAcesso;
    private LocalDateTime dataCadastro;

    public PerfilDTO() {}

    public PerfilDTO(String nome, String email, String descricaoPerfil, String nivelAcesso, LocalDateTime dataCadastro) {
        this.nome = nome;
        this.email = email;
        this.descricaoPerfil = descricaoPerfil;
        this.nivelAcesso = nivelAcesso;
        this.dataCadastro = dataCadastro;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDescricaoPerfil() { return descricaoPerfil; }
    public void setDescricaoPerfil(String descricaoPerfil) { this.descricaoPerfil = descricaoPerfil; }

    public String getNivelAcesso() { return nivelAcesso; }
    public void setNivelAcesso(String nivelAcesso) { this.nivelAcesso = nivelAcesso; }

    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }
}