package br.victor.backprojetoweb.perfil.dto;

public class PerfilJogoUpdateDTO {
    private String status; // será convertida para enum no backend
    private Integer horasJogadas;
    private Double nota;
    private String comentario;

    public PerfilJogoUpdateDTO() {}

    public PerfilJogoUpdateDTO(String status, Integer horasJogadas, Double nota, String comentario) {
        this.status = status;
        this.horasJogadas = horasJogadas;
        this.nota = nota;
        this.comentario = comentario;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getHorasJogadas() { return horasJogadas; }
    public void setHorasJogadas(Integer horasJogadas) { this.horasJogadas = horasJogadas; }

    public Double getNota() { return nota; }
    public void setNota(Double nota) { this.nota = nota; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}
