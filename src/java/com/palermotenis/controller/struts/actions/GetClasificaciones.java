/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.atributos.tipos.clasif.TipoAtributoClasif;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.Collection;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

/**
 *
 * @author Poly
 */
public class GetClasificaciones extends ActionSupport {

    private GenericDao<TipoAtributoClasif, Character> tipoAtributoClasifService;

    private InputStream inputStream;

    @Override
    public String execute() {

        Collection<TipoAtributoClasif> clasificaciones = tipoAtributoClasifService.findAll();

        JSONArray clasifArr = (JSONArray) JSONSerializer.toJSON(clasificaciones);

        inputStream = StringUtility.getInputString(clasifArr.toString());

        return SUCCESS;
    }

    /**
     * @param tipoAtributoClasifService the clasifService to set
     */
    public void setTipoAtributoClasifService(GenericDao<TipoAtributoClasif, Character> tipoAtributoClasifService) {
        this.tipoAtributoClasifService = tipoAtributoClasifService;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }
}
