/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans.ventas;

import java.io.Serializable;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Poly
 */
@Entity
@Table(name = "stock_listados")
@NamedQueries({
    @NamedQuery(name = "StockListado.findAll", query = "SELECT s FROM StockListado s"),
    @NamedQuery(name = "StockListado.findByListado", query = "SELECT s FROM StockListado s WHERE s.stockListadoPK.listado = :listado"),
    @NamedQuery(name = "StockListado.findByStock", query = "SELECT s FROM StockListado s WHERE s.stockListadoPK.stock = :stock"),
    @NamedQuery(name = "StockListado.findByCantidad", query = "SELECT s FROM StockListado s WHERE s.cantidad = :cantidad"),
    @NamedQuery(name = "StockListado.findByPrecioUnitario", query = "SELECT s FROM StockListado s WHERE s.precioUnitario = :precioUnitario"),
    @NamedQuery(name = "StockListado.findBySubtotal", query = "SELECT s FROM StockListado s WHERE s.subtotal = :subtotal")
})
@AssociationOverrides({
    @AssociationOverride(name = "stockListadoPK.stock", joinColumns =
    @JoinColumn(name = "stock")),
    @AssociationOverride(name = "stockListadoPK.listado", joinColumns =
    @JoinColumn(name = "listado"))
})
public class StockListado implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected StockListadoPK stockListadoPK;
    @Basic(optional = false)
    @Column(name = "Cantidad")
    private int cantidad;

    @Basic(optional = false)
    @Column(name = "PrecioUnitario")
    private double precioUnitario;

    @Basic(optional = false)
    @Column(name = "Subtotal")
    private double subtotal;


    public StockListado() {
    }

    public StockListado(StockListadoPK stockListadoPK) {
        this.stockListadoPK = stockListadoPK;
    }

    public StockListado(StockListadoPK stockListadoPK, int cantidad, double precioUnitario, double subtotal) {
        this.stockListadoPK = stockListadoPK;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public StockListadoPK getStockListadoPK() {
        return stockListadoPK;
    }

    public void setStockListadoPK(StockListadoPK stockListadoPK) {
        this.stockListadoPK = stockListadoPK;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stockListadoPK != null ? stockListadoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StockListado)) {
            return false;
        }
        StockListado other = (StockListado) object;
        if ((this.stockListadoPK == null && other.stockListadoPK != null) || (this.stockListadoPK != null && !this.stockListadoPK.equals(other.stockListadoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.palermotenis.model.beans.ventas.StockListado[stockListadoPK=" + stockListadoPK + "]";
    }
}
