/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.model.beans.ventas;

import com.palermotenis.model.beans.Stock;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Poly
 */
@Embeddable
public class StockListadoPK implements Serializable {
    @JoinColumn(name = "Listado", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Listado listado;

    @JoinColumn(name = "Stock", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Stock stock;

    public StockListadoPK(){}

    public StockListadoPK(Listado listado, Stock stock) {
        this.listado = listado;
        this.stock = stock;
    }

    public Listado getListado() {
        return listado;
    }

    public void setListado(Listado listado) {
        this.listado = listado;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StockListadoPK other = (StockListadoPK) obj;
        if (this.listado != other.listado && (this.listado == null || !this.listado.equals(other.listado))) {
            return false;
        }
        if (this.stock != other.stock && (this.stock == null || !this.stock.equals(other.stock))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.listado != null ? this.listado.hashCode() : 0);
        hash = 97 * hash + (this.stock != null ? this.stock.hashCode() : 0);
        return hash;
    }

}
