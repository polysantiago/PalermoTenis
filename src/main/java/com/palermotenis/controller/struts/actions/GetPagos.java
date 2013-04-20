/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.controller.struts.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Pago;
import java.util.Collection;

/**
 *
 * @author Poly
 */
public class GetPagos extends ActionSupport {

    private GenericDao<Pago, Integer> pagoService;
    private Collection<Pago> pagos;

    @Override
    public String execute(){
        pagos = pagoService.findAll();
        return SUCCESS;
    }

    /**
     * @param pagoService the pagosService to set
     */
    public void setPagoService(GenericDao<Pago, Integer> pagoService) {
        this.pagoService = pagoService;
    }

    /**
     * @return the pagos
     */
    public Collection<Pago> getPagos() {
        return pagos;
    }

}
