package com.palermotenis.controller.struts.actions;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.service.pagos.PagoService;

public class GetPagos extends ActionSupport {

    private static final long serialVersionUID = 8264803144351552901L;

    private Collection<Pago> pagos;

    @Autowired
    private PagoService pagoService;

    @Override
    public String execute() {
        return SUCCESS;
    }

    public Collection<Pago> getPagos() {
        if (pagos == null) {
            pagos = pagoService.getAllPagos();
        }
        return pagos;
    }

}
