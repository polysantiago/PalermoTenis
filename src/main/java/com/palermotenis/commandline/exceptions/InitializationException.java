package com.palermotenis.commandline.exceptions;

public class InitializationException extends RuntimeException {

    private static final long serialVersionUID = 6242772088368474500L;

    public InitializationException() {
        super();
    }

    public InitializationException(String message) {
        super(message);
    }

    public InitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InitializationException(Throwable cause) {
        super(cause);
    }

}
