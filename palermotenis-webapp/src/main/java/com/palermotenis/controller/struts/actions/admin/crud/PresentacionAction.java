package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.Collection;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.service.presentaciones.PresentacionService;

public class PresentacionAction extends InputStreamActionSupport {

    private static final long serialVersionUID = -1202633900380924622L;

    private Integer tipoPresentacionId;
    private Integer presentacionId;
    private Double cantidad;
    private String unidad;
    private String nombre;

    private Collection<Presentacion> presentaciones;

    @Autowired
    private PresentacionService presentacionService;

    public String show() {
        return SHOW;
    }

    public String create() {
        presentacionService.createNewPresentacion(tipoPresentacionId, cantidad, unidad, nombre);
        success();
        return JSON;
    }

    public String edit() {
        try {
            presentacionService.updatePresentacion(presentacionId, cantidad, nombre, unidad);
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
            presentacionService.delete(presentacionId);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
    }

    public void setTipoPresentacionId(Integer tipoPresentacionId) {
        this.tipoPresentacionId = tipoPresentacionId;
    }

    public void setPresentacionId(Integer presentacionId) {
        this.presentacionId = presentacionId;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Collection<Presentacion> getPresentaciones() {
        if (presentaciones == null) {
            presentaciones = presentacionService.getPresentacionesByTipo(tipoPresentacionId);
        }
        return presentaciones;
    }

}
