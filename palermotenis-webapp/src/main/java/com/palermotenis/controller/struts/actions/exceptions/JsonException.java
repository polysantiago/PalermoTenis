package com.palermotenis.controller.struts.actions.exceptions;

public class JsonException extends RuntimeException {

    private static final long serialVersionUID = 971354561389625424L;

    public JsonException(String message, Throwable cause) {
        super(message, cause);
    }

}
