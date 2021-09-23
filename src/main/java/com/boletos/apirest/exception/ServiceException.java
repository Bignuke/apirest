package com.boletos.apirest.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;


	/**
     * Construtor da Classe.
     */
    public ServiceException() {
        super();
    }

    /**
     * Construtor que recebe a mensagem.
     *
     * @param message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Construtor que recebe a throwable.
     *
     * @param throwable
     */
    public ServiceException(Throwable throwable) {
        super(throwable);
    }
    
    
    public ServiceException(Exception e) {
        super(e);
    }

    /**
     * Construtor que recebe a mensagem e a exception.
     *
     * @param message
     * @param throwable
     */
    public ServiceException(String message, Throwable exception) {
        super(message, exception);
    }
}
