package com.palermotenis.controller.struts.actions;

import java.io.InputStream;
import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.dao.Dao;
import com.palermotenis.util.StringUtility;

/**
 * 
 * @author Poly
 */
public class GetPresentacionesByTipo extends ActionSupport {

    private static final long serialVersionUID = 3583987664940616484L;

    private InputStream inputStream;

    @Autowired
    private Dao<Presentacion, Integer> presentacionDao;

    @Autowired
    private Dao<TipoPresentacion, Integer> tipoPresentacionDao;

    private Integer tipoPresentacionId;

    @Override
    public String execute() {

        TipoPresentacion tipo = tipoPresentacionDao.find(tipoPresentacionId);
        Collection<Presentacion> presentaciones = presentacionDao.queryBy("Tipo",
            new ImmutableMap.Builder<String, Object>().put("tipo", tipo).build());

        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]
            { "tipoPresentacion", "productos", "stocks", "precios" });

        JSONArray jObject = (JSONArray) JSONSerializer.toJSON(presentaciones, config);

        inputStream = StringUtility.getInputString(jObject.toString());

        return SUCCESS;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param tipoPresentacionId
     *            the tipoPresentacionId to set
     */
    public void setTipoPresentacionId(Integer tipoPresentacionId) {
        this.tipoPresentacionId = tipoPresentacionId;
    }

}
