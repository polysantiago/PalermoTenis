package com.palermotenis.model.beans.precios.pks;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;

@Embeddable
public class PrecioPresentacionPK implements PrecioPK, Serializable {

    private static final long serialVersionUID = -8421594106982187787L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Producto")
    private Producto producto;

    @ManyToOne
    private Moneda moneda;

    @ManyToOne
    private Pago pago;

    @ManyToOne
    private Presentacion presentacion;

    @Column(name = "Cuotas")
    private Integer cuotas;

    public PrecioPresentacionPK() {
    }

    public PrecioPresentacionPK(Producto producto, Moneda moneda, Pago pago, Presentacion presentacion, Integer cuotas) {
        this.producto = producto;
        this.moneda = moneda;
        this.pago = pago;
        this.presentacion = presentacion;
        this.cuotas = cuotas;
    }

    @Override
    public Producto getProducto() {
        return producto;
    }

    @Override
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public Moneda getMoneda() {
        return moneda;
    }

    @Override
    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    @Override
    public Pago getPago() {
        return pago;
    }

    @Override
    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Presentacion getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(Presentacion presentacion) {
        this.presentacion = presentacion;
    }

    @Override
    public Integer getCuotas() {
        return cuotas;
    }

    @Override
    public void setCuotas(Integer cuotas) {
        this.cuotas = cuotas;
    }

}
