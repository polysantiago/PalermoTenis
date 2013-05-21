package com.palermotenis.controller.struts.actions.exceptions;

public class AlreadySuscribedException extends RuntimeException {

    private static final long serialVersionUID = -9168273885511207147L;

    public AlreadySuscribedException(String message) {
        super(message);
    }

}
