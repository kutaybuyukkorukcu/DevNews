package com.scalx.devnews.exception;

public class UserExistsException extends RuntimeException {
    private static final long serialVersionUID = 5861310537366287163L;

    public UserExistsException() {
        super();
    }

    public UserExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserExistsException(final String message) {
        super(message);
    }

    public UserExistsException(final Throwable cause) {
        super(cause);
    }
}
