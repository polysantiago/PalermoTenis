package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.service.monedas.MonedaService;

public class MonedaAction extends InputStreamActionSupport {

    private static final long serialVersionUID = -5287286859234755308L;

    private Integer monedaId;
    private String simbolo;
    private String nombre;
    private String codigo;
    private Integer contrarioId;
    private List<Moneda> monedas;

    @Autowired
    private MonedaService monedaService;

    public String show() {
        return SHOW;
    }

    public String list() {
        return JSON;
    }

    public String create() {
        try {
            monedaService.createMoneda(contrarioId, nombre, simbolo, codigo);
            success();
        } catch (Exception ex) {
            failure(ex);
        }
        return STREAM;
    }

    public String edit() {
        try {
            monedaService.updateMoneda(monedaId, contrarioId, nombre, simbolo, codigo);
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
            monedaService.deleteMoneda(monedaId);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return STREAM;
    }

    private List<Moneda> getAll() {
        return monedaService.getAllMonedas();
    }

    public void setMonedaId(Integer monedaId) {
        this.monedaId = monedaId;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContrarioId(Integer contrarioId) {
        this.contrarioId = contrarioId;
    }

    public List<Moneda> getMonedas() {
        if (monedas == null) {
            monedas = getAll();
        }
        return monedas;
    }
}
