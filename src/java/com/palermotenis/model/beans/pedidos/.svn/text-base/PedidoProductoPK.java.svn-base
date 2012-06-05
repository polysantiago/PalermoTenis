/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans.pedidos;

import com.palermotenis.model.beans.Stock;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Poly
 */
@Embeddable
public class PedidoProductoPK implements Serializable {

    @JoinColumn(name = "Pedido")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pedido pedido;
    
    @JoinColumn(name = "Stock")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Stock stock;

    public PedidoProductoPK() {
    }

    public PedidoProductoPK(Pedido pedido, Stock stock) {
        this.pedido = pedido;
        this.stock = stock;
    }

    /**
     * @return the pedido
     */
    public Pedido getPedido() {
        return pedido;
    }

    /**
     * @param pedido the pedido to set
     */
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    /**
     * @return the stock
     */
    public Stock getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
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
        final PedidoProductoPK other = (PedidoProductoPK) obj;
        if (this.pedido != other.pedido && (this.pedido == null || !this.pedido.equals(other.pedido))) {
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
        hash = 47 * hash + (this.pedido != null ? this.pedido.hashCode() : 0);
        hash = 47 * hash + (this.stock != null ? this.stock.hashCode() : 0);
        return hash;
    }



}
