/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.results;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;


/**
 *
 * @author Poly
 */
public class BytesResult implements Result {


    public void execute(ActionInvocation invocation) throws Exception {
        ImageCapable action = (ImageCapable)invocation.getAction();
        HttpServletResponse response = ServletActionContext.getResponse();

        response.setContentType(action.getContentType());
        response.setContentLength(action.getContentLength());
        
        response.getOutputStream().write(action.getImageInBytes());
        response.getOutputStream().flush();
    }

}
