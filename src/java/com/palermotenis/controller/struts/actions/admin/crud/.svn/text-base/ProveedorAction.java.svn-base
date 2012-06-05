/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.crud;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.proveedores.Proveedor;
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
public class ProveedorAction extends ActionSupport {

    private final String CREATE = "create";
    private final String EDIT = "edit";
    private final String DESTROY = "destroy";
    private final String SHOW = "show";
    private final String LIST = "list";
    private final String JSON = "json";
    
    private GenericDao<Proveedor, Integer> proveedorService;
    private Integer proveedorId;
    private String nombre;
    private Collection<Proveedor> proveedores;
    private InputStream inputStream;

    public String show() {
        proveedores = proveedorService.findAll();
        return SHOW;
    }

    public String list() {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"costos"});
        JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(proveedorService.findAll(),jsonConfig);
        inputStream = StringUtility.getInputString(jsonArray.toString());
        return JSON;
    }

    public String create() {
        proveedorService.create(new Proveedor(nombre));
        inputStream = StringUtility.getInputString("OK");
        return JSON;
    }

    public String edit() {
        try {
            Proveedor proveedor = proveedorService.find(proveedorId);
            proveedor.setNombre(nombre);
            proveedorService.edit(proveedor);
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(ProveedorAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        } catch (Exception ex) {
            Logger.getLogger(ProveedorAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    public String destroy() {
        try {
            proveedorService.destroy(proveedorService.find(proveedorId));
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(ProveedorAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    /**
     * @param proveedorService the proveedorService to set
     */
    public void setProveedorService(GenericDao<Proveedor, Integer> proveedorService) {
        this.proveedorService = proveedorService;
    }

    /**
     * @param proveedorId the proveedorId to set
     */
    public void setProveedorId(Integer proveedorId) {
        this.proveedorId = proveedorId;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @return the proveedores
     */
    public Collection<Proveedor> getProveedores() {
        return proveedores;
    }
}
