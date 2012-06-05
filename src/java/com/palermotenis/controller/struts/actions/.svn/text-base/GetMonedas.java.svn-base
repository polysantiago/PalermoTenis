/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.controller.struts.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.Collection;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

/**
 *
 * @author Poly
 */
public class GetMonedas extends ActionSupport {

    private static final String JSON = "json";

    private GenericDao<Moneda, Integer> monedaService;
    private Collection<Moneda> monedas;

    private String resultType;

    private InputStream inputStream;

    @Override
    public String execute(){

        monedas = monedaService.findAll();

        if(resultType != null && resultType.equalsIgnoreCase("JSON")){
            JsonConfig config = new JsonConfig();
            config.setExcludes(new String[]{"nombre","contrario","paises","locale","formatter"});
            JSONArray jsonArray = (JSONArray)JSONSerializer.toJSON(monedas, config);
            inputStream = StringUtility.getInputString(jsonArray.toString());
            return JSON;
        }
        return SUCCESS;

    }

    /**
     * @param monedaService the monedasService to set
     */
    public void setMonedaService(GenericDao<Moneda, Integer> monedaService) {
        this.monedaService = monedaService;
    }

    /**
     * @return the monedas
     */
    public Collection<Moneda> getMonedas() {
        return monedas;
    }

    /**
     * @param resultType the resultType to set
     */
    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

}
