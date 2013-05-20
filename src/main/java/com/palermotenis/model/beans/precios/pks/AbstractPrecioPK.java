package com.palermotenis.model.beans.precios.pks;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.productos.Producto;

@MappedSuperclass
public abstract class AbstractPrecioPK implements PrecioPK {

    private static final long serialVersionUID = -2333534000247095607L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Producto")
    private Producto producto;

    @ManyToOne
    private Moneda moneda;

    @ManyToOne
    private Pago pago;

    @Column(name = "Cuotas")
    private Integer cuotas;

    public AbstractPrecioPK() {
    }

    public AbstractPrecioPK(Producto producto, Moneda moneda, Pago pago, Integer cuotas) {
        this.producto = producto;
        this.moneda = moneda;
        this.pago = pago;
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

    @Override
    public Integer getCuotas() {
        return cuotas;
    }

    @Override
    public void setCuotas(Integer cuotas) {
        this.cuotas = cuotas;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cuotas == null) ? 0 : cuotas.hashCode());
        result = prime * result + ((moneda == null) ? 0 : moneda.hashCode());
        result = prime * result + ((pago == null) ? 0 : pago.hashCode());
        result = prime * result + ((producto == null) ? 0 : producto.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractPrecioPK other = (AbstractPrecioPK) obj;
        if (cuotas == null) {
            if (other.cuotas != null) {
                return false;
            }
        } else if (!cuotas.equals(other.cuotas)) {
            return false;
        }
        if (moneda == null) {
            if (other.moneda != null) {
                return false;
            }
        } else if (!moneda.equals(other.moneda)) {
            return false;
        }
        if (pago == null) {
            if (other.pago != null) {
                return false;
            }
        } else if (!pago.equals(other.pago)) {
            return false;
        }
        if (producto == null) {
            if (other.producto != null) {
                return false;
            }
        } else if (!producto.equals(other.producto)) {
            return false;
        }
        return true;
    }

}
