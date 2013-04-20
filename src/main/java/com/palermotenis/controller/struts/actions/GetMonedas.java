package com.palermotenis.controller.struts.actions;

import java.io.InputStream;
import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.util.StringUtility;

/**
 *
 * @author Poly
 */
public class GetMonedas extends ActionSupport {

	private static final long serialVersionUID = 4337274661669247525L;

	private static final String JSON = "json";

    private Collection<Moneda> monedas;

    private String resultType;

    private InputStream inputStream;
    
    @Autowired
    private GenericDao<Moneda, Integer> monedaDao;

    @Override
    public String execute(){

        monedas = monedaDao.findAll();

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
