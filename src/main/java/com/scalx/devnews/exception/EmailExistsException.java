package com.scalx.devnews.exception;

public class EmailExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;


    public EmailExistsException() {
        super();
    }

    public EmailExistsException(String message) {
        super(message);
    }

    public EmailExistsException(Throwable e) {
        super(e);
    }

    public EmailExistsException(String message, Throwable e) {
        super(message, e);
    }

}
