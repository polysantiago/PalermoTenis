package com.palermotenis.controller.struts.actions;

import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.service.monedas.MonedaService;

public class GetMonedas extends InputStreamActionSupport {

    private static final long serialVersionUID = 4337274661669247525L;

    private Collection<Moneda> monedas;

    private String resultType;

    @Autowired
    private MonedaService monedaService;

    @Override
    public String execute() {
        if (StringUtils.equalsIgnoreCase(JSON, resultType)) {
            JsonConfig config = new JsonConfig();
            config.setExcludes(new String[]
                { "nombre", "contrario", "paises", "locale", "formatter" });
            JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(getMonedas(), config);
            writeResponse(jsonArray);
            return JSON;
        }
        return SUCCESS;

    }

    public Collection<Moneda> getMonedas() {
        if (monedas != null) {
            monedas = monedaService.getAllMonedas();
        }
        return monedas;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

}
