package com.boletos.apirest.exception;

public class APIException extends Exception {

	private static final long serialVersionUID = 1L;


	/**
     * Construtor da Classe.
     */
    public APIException() {
        super();
    }

    /**
     * Construtor que recebe a mensagem.
     *
     * @param message
     */
    public APIException(String message) {
        super(message);
    }

    /**
     * Construtor que recebe a throwable.
     *
     * @param throwable
     */
    public APIException(Throwable throwable) {
        super(throwable);
    }
    
    
    public APIException(Exception e) {
        super(e);
    }

    /**
     * Construtor que recebe a mensagem e a exception.
     *
     * @param message
     * @param throwable
     */
    public APIException(String message, Throwable exception) {
        super(message, exception);
    }
}
