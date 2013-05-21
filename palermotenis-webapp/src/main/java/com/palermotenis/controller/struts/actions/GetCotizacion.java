package com.palermotenis.controller.struts.actions;

import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.model.service.precios.impl.PrecioService;

public class GetCotizacion extends InputStreamActionSupport {

    private static final long serialVersionUID = -3131323335420585748L;

    private String to;
    private String from;

    @Autowired
    private PrecioService precioService;

    @Override
    public String execute() {
        try {
            Double result = precioService.calculateCotizacion(from, to);
            writeResponse(result.toString());
        } catch (Exception ex) {
            failure(ex);
        }
        return SUCCESS;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

}
