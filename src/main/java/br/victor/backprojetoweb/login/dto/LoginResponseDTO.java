package br.victor.backprojetoweb.login.dto;

public class LoginResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private Long perfilId;

    public LoginResponseDTO(Long id, String nome, String email, Long perfilId) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.perfilId = perfilId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getPerfilId() { return perfilId; }
    public void setPerfilId(Long perfilId) { this.perfilId = perfilId; }
}
