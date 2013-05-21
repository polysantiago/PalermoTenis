package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.service.presentaciones.tipos.TipoPresentacionService;

public class TipoPresentacionAction extends InputStreamActionSupport {

    private static final long serialVersionUID = -6672132861920246917L;

    private Integer tipoPresentacionId;
    private Integer tipoProductoId;

    private String nombre;

    private List<TipoPresentacion> tiposPresentacion;

    @Autowired
    private TipoPresentacionService tipoPresentacionService;

    public String show() {
        tiposPresentacion = getByTipoProducto();
        return SHOW;
    }

    public String list() {
        tiposPresentacion = getAll();
        return JSON;
    }

    public String listByTipoProducto() {
        tiposPresentacion = getByTipoProducto();
        return JSON;
    }

    public String create() {
        tipoPresentacionService.createNewTipoPresentacion(tipoProductoId, nombre);
        success();
        return STREAM;
    }

    public String edit() {
        try {
            tipoPresentacionService.updateTipoPresentacion(tipoPresentacionId, nombre);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        } catch (Exception ex) {
            failure(ex);
        }
        return STREAM;
    }

    public String destroy() {
        try {
            tipoPresentacionService.deleteTipoPresentacion(tipoPresentacionId);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return STREAM;
    }

    private List<TipoPresentacion> getByTipoProducto() {
        return tipoPresentacionService.getTiposPresentacionByTipoProducto(tipoProductoId);
    }

    private List<TipoPresentacion> getAll() {
        return tipoPresentacionService.getAllTiposPresentacion();
    }

    public void setTipoPresentacionId(Integer tipoPresentacionId) {
        this.tipoPresentacionId = tipoPresentacionId;
    }

    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<TipoPresentacion> getTiposPresentacion() {
        return tiposPresentacion;
    }

}
