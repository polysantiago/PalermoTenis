package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.model.beans.proveedores.Proveedor;
import com.palermotenis.model.service.proveedores.ProveedorService;

public class ProveedorAction extends InputStreamActionSupport {

    private static final long serialVersionUID = -5774790471392620713L;
    private Integer proveedorId;
    private String nombre;
    private List<Proveedor> proveedores;

    @Autowired
    private ProveedorService proveedorService;

    public String show() {
        return SHOW;
    }

    public String list() {
        return JSON;
    }

    public String create() {
        proveedorService.createNewProveedor(nombre);
        success();
        return STREAM;
    }

    public String edit() {
        try {
            proveedorService.updateProveedor(proveedorId, nombre);
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
            proveedorService.deleteProveedor(proveedorId);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
    }

    public void setProveedorId(Integer proveedorId) {
        this.proveedorId = proveedorId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Proveedor> getProveedores() {
        if (proveedores == null) {
            proveedores = proveedorService.getAllProveedores();
        }
        return proveedores;
    }
}
