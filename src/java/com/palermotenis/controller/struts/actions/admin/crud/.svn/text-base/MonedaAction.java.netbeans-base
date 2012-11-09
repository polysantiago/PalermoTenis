/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.crud;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Moneda;
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
public class MonedaAction extends ActionSupport {

    private final static String CREATE = "create";
    private final static String EDIT = "edit";
    private final static String DESTROY = "destroy";
    private final static String SHOW = "show";
    private final static String JSON = "json";
    private GenericDao<Moneda, Integer> monedaService;
    private Integer monedaId;
    private String simbolo;
    private String nombre;
    private Integer contrarioId;
    private Collection<Moneda> monedas;
    private InputStream inputStream;

    public String show() {
        monedas = monedaService.findAll();
        return SHOW;
    }

    public String list() {
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"nombre", "contrario", "paises", "locale", "formatter"});

        JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(monedaService.findAll(), config);

        inputStream = StringUtility.getInputString(jsonArray.toString());

        return JSON;
    }

    public String create() {
        try {
            Moneda moneda = new Moneda();
            Moneda contrario = monedaService.find(contrarioId);

            moneda.setNombre(nombre);
            moneda.setSimbolo(simbolo);
            moneda.setContrario(contrario);

            monedaService.create(moneda);

            inputStream = StringUtility.getInputString("OK");
        } catch (Exception ex) {
            Logger.getLogger(MonedaAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    public String edit() {
        try {
            Moneda moneda = monedaService.find(monedaId);
            Moneda contrario = monedaService.find(contrarioId);

            moneda.setNombre(nombre);
            moneda.setSimbolo(simbolo);

            if (contrario != moneda.getContrario()) {
                moneda.setContrario(contrario);
            }

            monedaService.edit(moneda);

            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(MonedaAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        } catch (Exception ex) {
            Logger.getLogger(MonedaAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    public String destroy() {
        try {
            monedaService.destroy(monedaService.find(monedaId));
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(MonedaAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    /**
     * @param monedaService the monedasService to set
     */
    public void setMonedaService(GenericDao<Moneda, Integer> monedaService) {
        this.monedaService = monedaService;
    }

    /**
     * @param monedaId the monedaId to set
     */
    public void setMonedaId(Integer monedaId) {
        this.monedaId = monedaId;
    }

    /**
     * @param simbolo the simbolo to set
     */
    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param contrarioId the contrarioId to set
     */
    public void setContrarioId(Integer contrarioId) {
        this.contrarioId = contrarioId;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @return the monedas
     */
    public Collection<Moneda> getMonedas() {
        return monedas;
    }
}
