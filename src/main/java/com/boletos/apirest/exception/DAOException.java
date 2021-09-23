package com.boletos.apirest.exception;

public class DAOException extends Exception {

	private static final long serialVersionUID = 1L;


	/**
     * Construtor da Classe.
     */
    public DAOException() {
        super();
    }

    /**
     * Construtor que recebe a mensagem.
     *
     * @param message
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Construtor que recebe a throwable.
     *
     * @param throwable
     */
    public DAOException(Throwable throwable) {
        super(throwable);
    }
    
    
    public DAOException(Exception e) {
        super(e);
    }

    /**
     * Construtor que recebe a mensagem e a exception.
     *
     * @param message
     * @param throwable
     */
    public DAOException(String message, Throwable exception) {
        super(message, exception);
    }
}
