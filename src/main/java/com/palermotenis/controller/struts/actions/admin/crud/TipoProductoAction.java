/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.crud;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;

/**
 *
 * @author Poly
 */
public class TipoProductoAction extends ActionSupport {

    private final String CREATE = "create";
    private final String EDIT = "edit";
    private final String DESTROY = "destroy";
    private final String SHOW = "show";
    private final String JSON = "json";

    private GenericDao<TipoProducto, Integer> tipoProductoService;
    private Collection<TipoProducto> tiposProducto;

    private Integer tipoProductoId;
    private String nombre;
    private Boolean presentable;

    private InputStream inputStream;

    public String show(){
        tiposProducto = tipoProductoService.findAll();
        return SHOW;
    }

    public String create(){
        TipoProducto tipoProducto = new TipoProducto(nombre,presentable);
        tipoProductoService.create(tipoProducto);

        inputStream = StringUtility.getInputString("OK");
        return JSON;
    }

    public String edit(){
        try {
            TipoProducto tipoProducto = tipoProductoService.find(tipoProductoId);
            tipoProducto.setNombre(nombre);
            tipoProducto.setPresentable(presentable);

            tipoProductoService.edit(tipoProducto);
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(MarcaAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        } catch (Exception ex) {
            Logger.getLogger(MarcaAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    public String destroy(){
        try {
            tipoProductoService.destroy(tipoProductoService.find(tipoProductoId));
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(MarcaAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        } catch (Exception ex) {
            Logger.getLogger(MarcaAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    /**
     * @param tipoProductoService the tiposProductoService to set
     */
    public void setTipoProductoService(GenericDao<TipoProducto, Integer> tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    /**
     * @return the tiposProducto
     */
    public Collection<TipoProducto> getTiposProducto() {
        return tiposProducto;
    }

    /**
     * @param tipoProductoId the tipoProductoId to set
     */
    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
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
     * @param presentable the presentable to set
     */
    public void setPresentable(Boolean presentable) {
        this.presentable = presentable;
    }


}
