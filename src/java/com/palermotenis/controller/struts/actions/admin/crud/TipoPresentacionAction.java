/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.crud;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
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
public class TipoPresentacionAction extends ActionSupport {

    private final String CREATE = "create";
    private final String EDIT = "edit";
    private final String DESTROY = "destroy";
    private final String SHOW = "show";
    private final String LIST = "list";
    private final String JSON = "json";

    private Integer tipoPresentacionId;
    private Integer tipoProductoId;
    private String nombre;
    private GenericDao<TipoPresentacion, Integer> tipoPresentacionService;
    private GenericDao<TipoProducto, Integer> tipoProductoService;
    private Collection<TipoPresentacion> tiposPresentacion;
    private InputStream inputStream;

    public String show() {

        TipoProducto tipoProducto = tipoProductoService.find(tipoProductoId);
        tiposPresentacion = tipoPresentacionService.queryBy("TipoProducto",
                new ImmutableMap.Builder<String, Object>().put("tipoProducto", tipoProducto).build());

        return SHOW;
    }

    public String list() {
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"tipoProducto", "presentaciones", "presentacionesByProd"});
        JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(tipoPresentacionService.findAll(), config);
        inputStream = StringUtility.getInputString(jsonArray.toString());
        return JSON;
    }

    public String listByTipoProducto() {
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"tipoProducto", "presentaciones", "presentacionesByProd"});

        TipoProducto tipoProducto = tipoProductoService.find(tipoProductoId);
        tiposPresentacion = tipoPresentacionService.queryBy("TipoProducto",
                new ImmutableMap.Builder<String, Object>().put("tipoProducto", tipoProducto).build());

        JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(tiposPresentacion, config);
        inputStream = StringUtility.getInputString(jsonArray.toString());
        return JSON;
    }

    public String create() {
        
        TipoProducto tipoProducto = tipoProductoService.find(tipoProductoId);
        TipoPresentacion tipoPresentacion = new TipoPresentacion(nombre, tipoProducto);
        tipoPresentacionService.create(tipoPresentacion);

        inputStream = StringUtility.getInputString("OK");

        return JSON;
    }

    public String edit() {
        try {
            TipoPresentacion tipoPresentacion = tipoPresentacionService.find(tipoPresentacionId);
            tipoPresentacion.setNombre(nombre);
            tipoPresentacionService.edit(tipoPresentacion);
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(TipoPresentacionAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(TipoPresentacionAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getMessage());
        }
        return JSON;
    }

    public String destroy() {
        try {
            tipoPresentacionService.destroy(tipoPresentacionService.find(tipoPresentacionId));
            inputStream = StringUtility.getInputString("OK");        
        } catch (HibernateException ex) {
            Logger.getLogger(TipoPresentacionAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getMessage());
        }
        return JSON;
    }

    /**
     * @param tipoPresentacionId the tipoPresentacionId to set
     */
    public void setTipoPresentacionId(Integer tipoPresentacionId) {
        this.tipoPresentacionId = tipoPresentacionId;
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
     * @param tipoPresentacionService the tipoPresentacionService to set
     */
    public void setTipoPresentacionService(GenericDao<TipoPresentacion, Integer> tipoPresentacionService) {
        this.tipoPresentacionService = tipoPresentacionService;
    }

    /**
     * @param tipoProductoService the tiposProductoService to set
     */
    public void setTipoProductoService(GenericDao<TipoProducto, Integer> tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    /**
     * @return the tiposPresentacion
     */
    public Collection<TipoPresentacion> getTiposPresentacion() {
        return tiposPresentacion;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }
}
