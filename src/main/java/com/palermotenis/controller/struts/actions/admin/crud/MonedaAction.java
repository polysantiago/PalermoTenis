package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.JsonActionSupport;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.dao.Dao;

/**
 * 
 * @author Poly
 */
public class MonedaAction extends JsonActionSupport {

    private static final long serialVersionUID = -5287286859234755308L;

    private static final String SHOW = "show";

    private Integer monedaId;
    private String simbolo;
    private String nombre;
    private Integer contrarioId;
    private Collection<Moneda> monedas;

    @Autowired
    private Dao<Moneda, Integer> monedaDao;

    public String show() {
        monedas = monedaDao.findAll();
        return SHOW;
    }

    public String list() {
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]
            { "nombre", "contrario", "paises", "locale", "formatter" });

        writeResponse((JSONArray) JSONSerializer.toJSON(monedaDao.findAll(), config));

        return JSON;
    }

    public String create() {
        try {
            Moneda moneda = new Moneda();
            Moneda contrario = monedaDao.find(contrarioId);

            moneda.setNombre(nombre);
            moneda.setSimbolo(simbolo);
            moneda.setContrario(contrario);

            monedaDao.create(moneda);

            success();
        } catch (Exception ex) {
            failure(ex);
        }
        return JSON;
    }

    public String edit() {
        try {
            Moneda moneda = monedaDao.find(monedaId);
            Moneda contrario = monedaDao.find(contrarioId);

            moneda.setNombre(nombre);
            moneda.setSimbolo(simbolo);

            if (contrario != moneda.getContrario()) {
                moneda.setContrario(contrario);
            }

            monedaDao.edit(moneda);

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
            monedaDao.destroy(monedaDao.find(monedaId));
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
    }

    /**
     * @param monedaId
     *            the monedaId to set
     */
    public void setMonedaId(Integer monedaId) {
        this.monedaId = monedaId;
    }

    /**
     * @param simbolo
     *            the simbolo to set
     */
    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param contrarioId
     *            the contrarioId to set
     */
    public void setContrarioId(Integer contrarioId) {
        this.contrarioId = contrarioId;
    }

    /**
     * @return the monedas
     */
    public Collection<Moneda> getMonedas() {
        return monedas;
    }
}
