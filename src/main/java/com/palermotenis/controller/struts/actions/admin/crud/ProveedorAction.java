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

import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.controller.struts.actions.JsonActionSupport;
import com.palermotenis.model.beans.proveedores.Proveedor;

/**
 * 
 * @author Poly
 */
public class ProveedorAction extends JsonActionSupport {

    private static final long serialVersionUID = -5774790471392620713L;
    private final String SHOW = "show";
    private Integer proveedorId;
    private String nombre;
    private Collection<Proveedor> proveedores;

    @Autowired
    private GenericDao<Proveedor, Integer> proveedorDao;

    public String show() {
        proveedores = proveedorDao.findAll();
        return SHOW;
    }

    public String list() {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]
            { "costos" });
        writeResponse((JSONArray) JSONSerializer.toJSON(proveedorDao.findAll(), jsonConfig));
        return JSON;
    }

    public String create() {
        proveedorDao.create(new Proveedor(nombre));
        success();
        return JSON;
    }

    public String edit() {
        try {
            Proveedor proveedor = proveedorDao.find(proveedorId);
            proveedor.setNombre(nombre);
            proveedorDao.edit(proveedor);
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
            proveedorDao.destroy(proveedorDao.find(proveedorId));
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
    }

    /**
     * @param proveedorId
     *            the proveedorId to set
     */
    public void setProveedorId(Integer proveedorId) {
        this.proveedorId = proveedorId;
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the proveedores
     */
    public Collection<Proveedor> getProveedores() {
        return proveedores;
    }
}
