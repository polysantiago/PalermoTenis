/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.controller.struts.actions.admin.crud;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Unidad;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import org.hibernate.HibernateException;

/**
 *
 * @author Poly
 */
public class UnidadAction extends ActionSupport {

    private final String CREATE = "create";
    private final String EDIT = "edit";
    private final String DESTROY = "destroy";
    private final String SHOW = "show";
    private final String JSON = "json";

    private GenericDao<Unidad, Integer> unidadService;
    private Collection<Unidad> unidades;

    private Integer unidadId;
    private String nombre;
    private String descripcion;

    private InputStream inputStream;

    public String show(){
        unidades = unidadService.findAll();
        return SHOW;
    }

    public String getJson(){
        unidades = unidadService.findAll();
        
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"tipoAtributoCollection"});

        JSONArray uArr = (JSONArray) JSONSerializer.toJSON(unidades, config);
        inputStream = StringUtility.getInputString(uArr.toString());
        return JSON;
    }

    public String create(){
        Unidad u = new Unidad(null, nombre, descripcion);
        unidadService.create(u);
        inputStream = StringUtility.getInputString("OK");
        return JSON;
    }

    public String edit(){
        try {
            Unidad u = unidadService.find(unidadId);
            u.setNombre(nombre);
            u.setDescripcion(descripcion);
            unidadService.edit(u);
            
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(UnidadAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        } catch (Exception ex) {
            Logger.getLogger(UnidadAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    public String destroy(){
        try {
            unidadService.destroy(unidadService.find(unidadId));
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(UnidadAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    /**
     * @param unidadService the unidadesService to set
     */
    public void setUnidadService(GenericDao<Unidad, Integer> unidadService) {
        this.unidadService = unidadService;
    }

    /**
     * @return the unidades
     */
    public Collection<Unidad> getUnidades() {
        return unidades;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param unidadId the unidadId to set
     */
    public void setUnidadId(Integer unidadId) {
        this.unidadId = unidadId;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
