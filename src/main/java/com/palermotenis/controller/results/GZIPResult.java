package com.palermotenis.controller.results;

import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

public class GZIPResult implements Result {

    private static final long serialVersionUID = -8937598681844745424L;

    private String contentType;

    @Override
    public void execute(ActionInvocation invocation) throws Exception {
        GZIPCapable action = (GZIPCapable) invocation.getAction();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType(contentType);
        response.addHeader("Content-Encoding", "gzip");

        IOUtils.copy(action.getInputStream(), new GZIPOutputStream(response.getOutputStream()));
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

}
