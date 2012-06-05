/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.crud;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import org.hibernate.HibernateException;

/**
 *
 * @author Poly
 */
public class PagoAction extends ActionSupport {

    private final static String CREATE = "create";
    private final static String EDIT = "edit";
    private final static String DESTROY = "destroy";
    private final static String SHOW = "show";
    private final static String JSON = "json";
    
    private GenericDao<Pago, Integer> pagoService;
    private Integer pagoId;
    private String nombre;
    private InputStream inputStream;
    private Collection<Pago> pagos;    

    public String show() {
        pagos = pagoService.findAll();
        return SHOW;
    }

    public String list() {        
        JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(pagoService.findAll());
        inputStream = StringUtility.getInputString(jsonArray.toString());
        return JSON;
    }

    public String create() {

        Pago pago = new Pago();
        pago.setNombre(nombre);
        pagoService.create(pago);

        inputStream = StringUtility.getInputString("OK");
        return JSON;
    }

    public String edit() {
        try {
            Pago pago = pagoService.find(pagoId);
            pago.setNombre(nombre);

            pagoService.edit(pago);
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(PagoAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        } catch (Exception ex) {
            Logger.getLogger(PagoAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    public String destroy() {
        try {
            pagoService.destroy(pagoService.find(pagoId));
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(PagoAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    /**
     * @param pagoService the pagosService to set
     */
    public void setPagoService(GenericDao<Pago, Integer> pagoService) {
        this.pagoService = pagoService;
    }

    /**
     * @param pagoId the pagoId to set
     */
    public void setPagoId(Integer pagoId) {
        this.pagoId = pagoId;
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
     * @return the pagos
     */
    public Collection<Pago> getPagos() {
        return pagos;
    }
}
