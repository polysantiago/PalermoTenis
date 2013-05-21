package com.palermotenis.controller.results;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.json.JSONResult;
import org.apache.struts2.json.JSONUtil;
import org.apache.struts2.json.SerializationParams;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;
import com.palermotenis.controller.struts.actions.exceptions.JsonErrorResponse;
import com.palermotenis.controller.struts.actions.exceptions.JsonException;

public class JSONExceptionResult extends JSONResult {

    private static final long serialVersionUID = 3352633317604174849L;

    private static final Logger LOGGER = Logger.getLogger(JSONExceptionResult.class);

    private int statusCode;

    @Override
    public void execute(ActionInvocation invocation) throws Exception {
        ActionContext actionContext = invocation.getInvocationContext();
        HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);

        try {
            ValueStack stack = invocation.getStack();
            JsonException exception = (JsonException) stack.findValue("exception");

            String errorMessage = exception.getMessage();
            if (exception.getCause() != null) {
                errorMessage = exception.getCause().getLocalizedMessage();
            }

            JsonErrorResponse errorResponse = new JsonErrorResponse(statusCode, exception.getLocalizedMessage(),
                errorMessage);
            String json = JSONUtil.serialize(errorResponse);

            JSONUtil.writeJSONToResponse(new SerializationParams(response, getEncoding(), isWrapWithComments(), json,
                isEnableSMD(), isEnableGZIP(), isNoCache(), statusCode, 500, false, response.getContentType(),
                getWrapSuffix(), getWrapSuffix()));
        } catch (IOException exception) {
            LOGGER.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    /**
     * Status code to be set in the response
     * 
     * @param statusCode
     */
    @Override
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
