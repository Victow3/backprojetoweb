package br.victor.backprojetoweb.usuario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "perfil_usuarios")
public class PerfilUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Descrição do perfil é obrigatória")
    @Column(nullable = false)
    private String descricao;

    @NotBlank(message = "Nível de acesso é obrigatório")
    @Column(nullable = false)
    private String nivelAcesso; // ex: ADMIN, USUARIO

    @OneToOne(mappedBy = "perfilUsuario")
    private Usuario usuario;

    public PerfilUsuario() {}

    public PerfilUsuario(String descricao, String nivelAcesso) {
        this.descricao = descricao;
        this.nivelAcesso = nivelAcesso;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getNivelAcesso() { return nivelAcesso; }
    public void setNivelAcesso(String nivelAcesso) { this.nivelAcesso = nivelAcesso; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}

