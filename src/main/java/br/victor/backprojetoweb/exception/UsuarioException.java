package br.victor.backprojetoweb.exception;

public class UsuarioException extends RuntimeException {

    public UsuarioException(String message) {
        super(message); // chama o construtor de RuntimeException que aceita a mensagem
    }
}
