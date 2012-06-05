/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions;

import com.opensymphony.xwork2.ActionSupport;
import java.io.InputStream;
import com.palermotenis.util.Convertor;
import com.palermotenis.util.StringUtility;

/**
 *
 * @author Poly
 */
public class GetCotizacion extends ActionSupport {

    private Convertor convertor;
    private InputStream inputStream;
    private String to;
    private String from;

    @Override
    public String execute() {
        try {
            Double result = convertor.calculateCotizacion(from, to);
            inputStream = StringUtility.getInputString(result.toString());
        } catch (Exception e){            
            inputStream = StringUtility.getInputString("Error!");
        }       
        return SUCCESS;
    }

    /**
     * @param to the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param currencyConvertor the currencyConvertor to set
     */
    public void setConvertor(Convertor convertor) {
        this.convertor = convertor;
    }
}
