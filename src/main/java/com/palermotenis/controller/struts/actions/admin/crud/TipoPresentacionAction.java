/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.controller.struts.actions.JsonActionSupport;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;

/**
 * 
 * @author Poly
 */
public class TipoPresentacionAction extends JsonActionSupport {

    private static final long serialVersionUID = -6672132861920246917L;
    private final String SHOW = "show";

    private Integer tipoPresentacionId;
    private Integer tipoProductoId;

    private String nombre;

    private Collection<TipoPresentacion> tiposPresentacion;

    @Autowired
    private GenericDao<TipoPresentacion, Integer> tipoPresentacionDao;

    @Autowired
    private GenericDao<TipoProducto, Integer> tipoProductoDao;

    public String show() {
        TipoProducto tipoProducto = tipoProductoDao.find(tipoProductoId);
        tiposPresentacion = tipoPresentacionDao.queryBy("TipoProducto",
            new ImmutableMap.Builder<String, Object>().put("tipoProducto", tipoProducto).build());
        return SHOW;
    }

    public String list() {
        writeResponse((JSONArray) JSONSerializer.toJSON(tipoPresentacionDao.findAll(), buildJsonConfig()));
        return JSON;
    }

    public String listByTipoProducto() {
        TipoProducto tipoProducto = tipoProductoDao.find(tipoProductoId);
        tiposPresentacion = tipoPresentacionDao.queryBy("TipoProducto",
            new ImmutableMap.Builder<String, Object>().put("tipoProducto", tipoProducto).build());
        writeResponse((JSONArray) JSONSerializer.toJSON(tiposPresentacion, buildJsonConfig()));
        return JSON;
    }

    private JsonConfig buildJsonConfig() {
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]
            { "tipoProducto", "presentaciones", "presentacionesByProd" });
        return config;
    }

    public String create() {
        TipoProducto tipoProducto = tipoProductoDao.find(tipoProductoId);
        TipoPresentacion tipoPresentacion = new TipoPresentacion(nombre, tipoProducto);
        tipoPresentacionDao.create(tipoPresentacion);

        success();
        return JSON;
    }

    public String edit() {
        try {
            TipoPresentacion tipoPresentacion = tipoPresentacionDao.find(tipoPresentacionId);
            tipoPresentacion.setNombre(nombre);
            tipoPresentacionDao.edit(tipoPresentacion);
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
            tipoPresentacionDao.destroy(tipoPresentacionDao.find(tipoPresentacionId));
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
    }

    /**
     * @param tipoPresentacionId
     *            the tipoPresentacionId to set
     */
    public void setTipoPresentacionId(Integer tipoPresentacionId) {
        this.tipoPresentacionId = tipoPresentacionId;
    }

    /**
     * @param tipoProductoId
     *            the tipoProductoId to set
     */
    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the tiposPresentacion
     */
    public Collection<TipoPresentacion> getTiposPresentacion() {
        return tiposPresentacion;
    }

}
