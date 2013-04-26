package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.Collection;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.JsonActionSupport;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.Dao;

/**
 * 
 * @author Poly
 */
public class TipoProductoAction extends JsonActionSupport {

    private static final long serialVersionUID = 5098335306548843831L;

    private final String SHOW = "show";

    private Collection<TipoProducto> tiposProducto;

    private Integer tipoProductoId;
    private String nombre;
    private Boolean presentable;

    @Autowired
    private Dao<TipoProducto, Integer> tipoProductoDao;

    public String show() {
        tiposProducto = tipoProductoDao.findAll();
        return SHOW;
    }

    public String create() {
        tipoProductoDao.create(new TipoProducto(nombre, presentable));
        success();
        return JSON;
    }

    public String edit() {
        try {
            TipoProducto tipoProducto = tipoProductoDao.find(tipoProductoId);
            tipoProducto.setNombre(nombre);
            tipoProducto.setPresentable(presentable);

            tipoProductoDao.edit(tipoProducto);
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
            tipoProductoDao.destroy(tipoProductoDao.find(tipoProductoId));
            success();
        } catch (HibernateException ex) {
            failure(ex);
        } catch (Exception ex) {
            failure(ex);
        }
        return JSON;
    }

    /**
     * @return the tiposProducto
     */
    public Collection<TipoProducto> getTiposProducto() {
        return tiposProducto;
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
     * @param presentable
     *            the presentable to set
     */
    public void setPresentable(Boolean presentable) {
        this.presentable = presentable;
    }

}
