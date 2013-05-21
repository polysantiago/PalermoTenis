package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.model.beans.Unidad;
import com.palermotenis.model.service.unidades.UnidadService;

public class UnidadAction extends InputStreamActionSupport {

    private static final long serialVersionUID = 9051985059330393430L;

    private List<Unidad> unidades;

    private Integer unidadId;

    private String nombre;
    private String descripcion;

    @Autowired
    private UnidadService unidadService;

    public String show() {
        unidades = unidadService.getAllUnidades();
        return SHOW;
    }

    public String getJson() {
        unidades = unidadService.getAllUnidades();
        return JSON;
    }

    public String create() {
        unidadService.createNewUnidad(nombre, descripcion);
        success();
        return STREAM;
    }

    public String edit() {
        try {
            unidadService.updateUnidad(unidadId, nombre, descripcion);
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
            unidadService.deleteUnidad(unidadId);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return STREAM;
    }

    public Collection<Unidad> getUnidades() {
        return unidades;
    }

    public void setUnidadId(Integer unidadId) {
        this.unidadId = unidadId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
