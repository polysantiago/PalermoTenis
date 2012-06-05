/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.results;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.zip.GZIPOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author poly
 */
public class GZIPResult implements Result {

    private String contentType;

    public void execute(ActionInvocation invocation) throws Exception {
        GZIPCapable action = (GZIPCapable) invocation.getAction();
        HttpServletResponse response = ServletActionContext.getResponse();

        response.setContentType(contentType);

        response.addHeader("Content-Encoding", "gzip");
        GZIPOutputStream out = null;
        InputStream in = action.getInputStream();
        try {
            out = new GZIPOutputStream(response.getOutputStream());
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.finish();
                out.close();
            }
        }

    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

}
