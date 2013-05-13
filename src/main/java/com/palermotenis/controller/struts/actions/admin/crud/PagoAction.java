package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.dao.Dao;

/**
 * 
 * @author Poly
 */
public class PagoAction extends InputStreamActionSupport {

    private static final long serialVersionUID = 2529039104976965428L;

    private final static String SHOW = "show";

    private Integer pagoId;
    private String nombre;
    private Collection<Pago> pagos;

    @Autowired
    private Dao<Pago, Integer> pagoDao;

    public String show() {
        pagos = pagoDao.findAll();
        return SHOW;
    }

    public String list() {
        writeResponse((JSONArray) JSONSerializer.toJSON(pagoDao.findAll()));
        return JSON;
    }

    public String create() {

        Pago pago = new Pago();
        pago.setNombre(nombre);
        pagoDao.create(pago);

        success();
        return JSON;
    }

    public String edit() {
        try {
            Pago pago = pagoDao.find(pagoId);
            pago.setNombre(nombre);

            pagoDao.edit(pago);
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
            pagoDao.destroy(pagoDao.find(pagoId));
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
    }

    /**
     * @param pagoId
     *            the pagoId to set
     */
    public void setPagoId(Integer pagoId) {
        this.pagoId = pagoId;
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the pagos
     */
    public Collection<Pago> getPagos() {
        return pagos;
    }
}
