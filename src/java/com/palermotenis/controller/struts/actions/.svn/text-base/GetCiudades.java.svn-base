/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.geograficos.Ciudad;
import com.palermotenis.model.beans.geograficos.Provincia;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;

/**
 *
 * @author Poly
 */
public class GetCiudades extends ActionSupport {

    private GenericDao<Ciudad, Integer> ciudadService;
    private InputStream inputStream;
    private String q;

    @Override
    public String execute() {
        StringBuilder sb = new StringBuilder();
        for(Ciudad c : ciudadService.queryBy("Nombre",
                new ImmutableMap.Builder<String, Object>().put("nombre", "%" + q + "%").build())){
            Provincia p = c.getProvincia();
            sb.append(c.getId()).append("|");
            sb.append(c.getNombre()).append('|');
            sb.append(c.getCodigoPostal()).append('|');
            sb.append(p.getNombre()).append('|');
            sb.append(p.getPais().getNombre()).append("\n");
        }
        inputStream = StringUtility.getInputString(sb.toString());
        return SUCCESS;
    }

    /**
     * @param ciudadService the ciudadesService to set
     */
    public void setCiudadService(GenericDao<Ciudad, Integer> ciudadService) {
        this.ciudadService = ciudadService;
    }

    /**
     * @param q the q to set
     */
    public void setQ(String q) {
        this.q = q;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }
}
