package com.palermotenis.model.exceptions;

public class InvalidTokenException extends RuntimeException {

    private static final long serialVersionUID = -4622605840757917818L;

    public InvalidTokenException() {
        super();
    }

    public InvalidTokenException(String message) {
        super(message);
    }

}
