package com.palermotenis.controller.struts.actions.exceptions;

import org.apache.struts2.json.annotations.JSON;

public class JsonErrorResponse {

    private final int errorCode;

    private final String errorKey;

    private final String errorMessage;

    public JsonErrorResponse(int errorCode, String errorKey, String errorMessage) {
        this.errorCode = errorCode;
        this.errorKey = errorKey;
        this.errorMessage = errorMessage;
    }

    @JSON(name = "error-code")
    public int getErrorCode() {
        return errorCode;
    }

    @JSON(name = "error-key")
    public String getErrorKey() {
        return errorKey;
    }

    @JSON(name = "error-message")
    public String getErrorMessage() {
        return errorMessage;
    }

}
