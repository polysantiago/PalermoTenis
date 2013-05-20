package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.service.pagos.PagoService;

public class PagoAction extends InputStreamActionSupport {

    private static final long serialVersionUID = 2529039104976965428L;

    private final static String SHOW = "show";

    private Integer pagoId;
    private String nombre;
    private List<Pago> pagos;

    @Autowired
    private PagoService pagoService;

    public String show() {
        return SHOW;
    }

    public String list() {
        return JSON;
    }

    public String create() {
        pagoService.createPago(nombre);
        success();
        return STREAM;
    }

    public String edit() {
        try {
            pagoService.deletePago(pagoId);
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
            pagoService.deletePago(pagoId);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return STREAM;
    }

    public void setPagoId(Integer pagoId) {
        this.pagoId = pagoId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Pago> getPagos() {
        if (pagos == null) {
            pagos = pagoService.getAllPagos();
        }
        return pagos;
    }
}
