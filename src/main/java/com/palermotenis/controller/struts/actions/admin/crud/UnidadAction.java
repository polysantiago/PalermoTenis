package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.JsonActionSupport;
import com.palermotenis.model.beans.Unidad;
import com.palermotenis.model.dao.Dao;

/**
 * 
 * @author Poly
 */
public class UnidadAction extends JsonActionSupport {

    private static final long serialVersionUID = 9051985059330393430L;

    private final String SHOW = "show";

    private Collection<Unidad> unidades;

    private Integer unidadId;

    private String nombre;
    private String descripcion;

    @Autowired
    private Dao<Unidad, Integer> unidadDao;

    public String show() {
        unidades = unidadDao.findAll();
        return SHOW;
    }

    public String getJson() {
        unidades = unidadDao.findAll();

        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]
            { "tipoAtributoCollection" });

        writeResponse((JSONArray) JSONSerializer.toJSON(unidades, config));
        return JSON;
    }

    public String create() {
        unidadDao.create(new Unidad(null, nombre, descripcion));
        success();
        return JSON;
    }

    public String edit() {
        try {
            Unidad u = unidadDao.find(unidadId);
            u.setNombre(nombre);
            u.setDescripcion(descripcion);
            unidadDao.edit(u);

            success();
        } catch (HibernateException ex) {
            failure(ex);
        } catch (Exception ex) {
            failure(ex);
        }
        return JSON;
    }

    public String destroy() {
        try {
            unidadDao.destroy(unidadDao.find(unidadId));
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
    }

    /**
     * @return the unidades
     */
    public Collection<Unidad> getUnidades() {
        return unidades;
    }

    /**
     * @param unidadId
     *            the unidadId to set
     */
    public void setUnidadId(Integer unidadId) {
        this.unidadId = unidadId;
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param descripcion
     *            the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
