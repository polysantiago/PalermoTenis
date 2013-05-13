package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.Collection;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;

public class TipoProductoAction extends InputStreamActionSupport {

    private static final long serialVersionUID = 5098335306548843831L;

    private Collection<TipoProducto> tiposProducto;

    private Integer tipoProductoId;
    private String nombre;
    private Boolean presentable;

    @Autowired
    private TipoProductoService tipoProductoService;

    public String show() {
        return SHOW;
    }

    public String create() {
        tipoProductoService.createNewTipoProducto(nombre, presentable);
        success();
        return JSON;
    }

    public String edit() {
        try {
            tipoProductoService.updateTipoProducto(tipoProductoId, nombre, presentable);
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
            tipoProductoService.deleteTipoProducto(tipoProductoId);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        } catch (Exception ex) {
            failure(ex);
        }
        return JSON;
    }

    public Collection<TipoProducto> getTiposProducto() {
        if (tiposProducto == null) {
            tiposProducto = tipoProductoService.getAllTipoProducto();
        }
        return tiposProducto;
    }

    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPresentable(Boolean presentable) {
        this.presentable = presentable;
    }

}
