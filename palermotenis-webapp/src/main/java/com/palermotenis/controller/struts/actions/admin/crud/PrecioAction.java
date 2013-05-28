package com.palermotenis.controller.struts.actions.admin.crud;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.model.service.precios.PrecioService;

public class PrecioAction extends InputStreamActionSupport {

    private static final long serialVersionUID = -4112606099516303261L;

    private Integer productoId;
    private Integer newPagoId;
    private Integer newMonedaId;
    private Integer newPresentacionId;
    private Integer newCuotas;

    private Double valor;
    private boolean enOferta;
    private Double valorOferta;

    private Integer pagoId;
    private Integer monedaId;
    private Integer presentacionId;
    private Integer cuotas;
    private Integer productoOrden;
    private Integer productoRgtId;
    private Integer productoRgtOrden;

    @Autowired
    private PrecioService precioService;

    public String create() {
        try {
            precioService.create(productoId, pagoId, monedaId, presentacionId, valor, valorOferta, cuotas, enOferta);
            success();
        } catch (Exception ex) {
            failure(ex);
        }
        return STREAM;
    }

    public String edit() {
        try {
            precioService.update(productoId, pagoId, monedaId, presentacionId, valor, valorOferta, cuotas, enOferta,
                newPagoId, newMonedaId, newPresentacionId, newCuotas);
            success();
        } catch (Exception ex) {
            failure(ex);
        }
        return STREAM;
    }

    public String destroy() {
        try {
            precioService.destroy(productoId, pagoId, monedaId, presentacionId, cuotas);
            success();
        } catch (Exception ex) {
            failure(ex);
        }
        return STREAM;
    }

    public String move() {
        try {
            precioService.moveOffer(productoId, productoOrden, productoRgtId, productoRgtOrden);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return STREAM;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public void setNewPagoId(Integer newPagoId) {
        this.newPagoId = newPagoId;
    }

    public void setNewMonedaId(Integer newMonedaId) {
        this.newMonedaId = newMonedaId;
    }

    public void setNewPresentacionId(Integer newPresentacionId) {
        this.newPresentacionId = newPresentacionId;
    }

    public void setNewCuotas(Integer newCuotas) {
        this.newCuotas = newCuotas;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setEnOferta(boolean enOferta) {
        this.enOferta = enOferta;
    }

    public void setValorOferta(Double valorOferta) {
        this.valorOferta = valorOferta;
    }

    public void setPagoId(Integer pagoId) {
        this.pagoId = pagoId;
    }

    public void setMonedaId(Integer monedaId) {
        this.monedaId = monedaId;
    }

    public void setPresentacionId(Integer presentacionId) {
        this.presentacionId = presentacionId;
    }

    public void setCuotas(Integer cuotas) {
        this.cuotas = cuotas;
    }

    public void setProductoOrden(Integer productoOrden) {
        this.productoOrden = productoOrden;
    }

    public void setProductoRgtId(Integer productoRgtId) {
        this.productoRgtId = productoRgtId;
    }

    public void setProductoRgtOrden(Integer productoRgtOrden) {
        this.productoRgtOrden = productoRgtOrden;
    }
}
