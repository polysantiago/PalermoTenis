/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.controller.struts.actions;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
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
public class GetPresentacionesByTipo extends ActionSupport {

    private GenericDao<TipoPresentacion, Integer> tipoPresentacionService;
    private GenericDao<Presentacion, Integer> presentacionService;

    private Integer tipoPresentacionId;

    private InputStream inputStream;

    @Override
    public String execute(){

        TipoPresentacion tipo = tipoPresentacionService.find(tipoPresentacionId);
        Collection<Presentacion> presentaciones = presentacionService.queryBy("Tipo",
                new ImmutableMap.Builder<String, Object>().put("tipo", tipo).build());

        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"tipoPresentacion","productos","stocks","precios"});

        JSONArray jObject = (JSONArray)JSONSerializer.toJSON(presentaciones, config);

        inputStream = StringUtility.getInputString(jObject.toString());

        return SUCCESS;
    }

    /**
     * @param tipoPresentacionService the tipoPresentacionService to set
     */
    public void setTipoPresentacionService(GenericDao<TipoPresentacion, Integer> tipoPresentacionService) {
        this.tipoPresentacionService = tipoPresentacionService;
    }

    /**
     * @param presentacionService the presentacionService to set
     */
    public void setPresentacionService(GenericDao<Presentacion, Integer> presentacionService) {
        this.presentacionService = presentacionService;
    }

    /**
     * @param tipoPresentacionId the tipoPresentacionId to set
     */
    public void setTipoPresentacionId(Integer tipoPresentacionId) {
        this.tipoPresentacionId = tipoPresentacionId;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

}
