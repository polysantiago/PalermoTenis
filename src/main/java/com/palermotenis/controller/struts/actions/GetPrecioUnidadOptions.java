/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.Collection;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 *
 * @author Poly
 */
public class GetPrecioUnidadOptions extends ActionSupport {

    private GenericDao<Pago, Integer> pagoService;
    private GenericDao<Moneda, Integer> monedaService;

    private InputStream inputStream;

    @Override
    public String execute() {

        Collection<Pago> pagos = pagoService.findAll();
        Collection<Moneda> monedas = monedaService.findAll();

        JSONObject jsonObject = new JSONObject();

        JsonConfig jsonConfigMonedas = new JsonConfig();
        jsonConfigMonedas.setExcludes(new String[]{"nombre","contrario","paises","formatter","locale"});

        jsonObject.element("monedas", monedas, jsonConfigMonedas);
        jsonObject.element("pagos", pagos);

        inputStream = StringUtility.getInputString(jsonObject.toString());

        return SUCCESS;
    }

    /**
     * @param pagoService the pagosService to set
     */
    public void setPagoService(GenericDao<Pago, Integer> pagoService) {
        this.pagoService = pagoService;
    }

    /**
     * @param monedaService the monedasService to set
     */
    public void setMonedaService(GenericDao<Moneda, Integer> monedaService) {
        this.monedaService = monedaService;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }
}
