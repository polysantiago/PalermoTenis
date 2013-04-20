/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans.precios.pks;

import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.*;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Poly
 */
@Embeddable
public class PrecioPresentacionPK implements Serializable, PrecioPK  {

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

    public PrecioPresentacionPK(Moneda moneda, Pago pago, Producto producto, Presentacion presentacion, Integer cuotas) {
        setMoneda(moneda);
        setPago(pago);
        this.producto = producto;
        this.presentacion = presentacion;
        this.cuotas = cuotas;
    }

    /**
     * @return the producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }


    /**
     * @return the moneda
     */
    public Moneda getMoneda() {
        return moneda;
    }

    /**
     * @param moneda the moneda to set
     */
    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    /**
     * @return the pago
     */
    public Pago getPago() {
        return pago;
    }

    /**
     * @param pago the pago to set
     */
    public void setPago(Pago pago) {
        this.pago = pago;
    }

    /**
     * @return the presentacion
     */
    public Presentacion getPresentacion() {
        return presentacion;
    }

    /**
     * @param presentacion the presentacion to set
     */
    public void setPresentacion(Presentacion presentacion) {
        this.presentacion = presentacion;
    }

    /**
     * @return the cuotas
     */
    public Integer getCuotas() {
        return cuotas;
    }

    /**
     * @param cuotas the cuotas to set
     */
    public void setCuotas(Integer cuotas) {
        this.cuotas = cuotas;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PrecioPresentacionPK other = (PrecioPresentacionPK) obj;
        if (this.moneda != other.moneda && (this.moneda == null || !this.moneda.equals(other.moneda))) {
            return false;
        }
        if (this.pago != other.pago && (this.pago == null || !this.pago.equals(other.pago))) {
            return false;
        }
        if (this.producto != other.getProducto() && (this.producto == null || !this.producto.equals(other.getProducto()))) {
            return false;
        }
        if (this.getPresentacion() != other.getPresentacion() && (this.presentacion == null || !this.presentacion.equals(other.getPresentacion()))) {
            return false;
        }
        if (this.cuotas != other.cuotas && (this.cuotas == null || !this.cuotas.equals(other.cuotas))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.moneda != null ? this.moneda.hashCode() : 0);
        hash = 97 * hash + (this.pago != null ? this.pago.hashCode() : 0);
        hash = 97 * hash + (this.producto != null ? this.producto.hashCode() : 0);
        hash = 97 * hash + (this.presentacion != null ? this.presentacion.hashCode() : 0);
        return hash;
    }

}
