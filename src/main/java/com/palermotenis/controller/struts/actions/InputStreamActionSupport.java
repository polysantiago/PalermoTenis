package com.palermotenis.controller.struts.actions;

import java.io.InputStream;

import net.sf.json.JSON;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.results.GZIPCapable;
import com.palermotenis.util.StringUtility;

public class InputStreamActionSupport extends ActionSupport implements GZIPCapable {

    private static final long serialVersionUID = -8022723052520248208L;

    private final Logger logger = Logger.getLogger(getClass());

    protected static final String JSON = "json";
    protected static final String SHOW = "show";
    protected static final String OK = "OK";

    private InputStream inputStream;

    protected void success() {
        writeResponse(OK);
    }

    protected void writeResponse(JSON json) {
        writeResponse(json.toString());
    }

    protected void writeResponse(String response) {
        inputStream = StringUtility.getInputString(response);
    }

    protected void failure(Exception ex) {
        logger.error(ex.getMessage(), ex);
        writeResponse(ex.getLocalizedMessage());
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

}
