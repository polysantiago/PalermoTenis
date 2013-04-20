/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans.pedidos;

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
@Table(name = "pedidos_productos")
@NamedQueries({
    @NamedQuery(name = "PedidoProducto.findByPedido", query = "SELECT p FROM PedidoProducto p WHERE p.id.pedido = :pedido"),
    @NamedQuery(name = "PedidoProducto.findByProducto", query = "SELECT p FROM PedidoProducto p WHERE p.id.stock.producto = :producto")
})
@AssociationOverrides({
    @AssociationOverride(name = "id.stock", joinColumns = @JoinColumn(name = "stock")),
    @AssociationOverride(name = "id.pedido", joinColumns = @JoinColumn(name = "pedido"))
})
public class PedidoProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PedidoProductoPK id;
    
    @Basic(optional = false)
    @Column(name = "Cantidad")
    private int cantidad;

    @Basic(optional = false)
    @Column(name = "Subtotal")
    private double subtotal;

    public PedidoProducto() {
    }

    public PedidoProducto(PedidoProductoPK id) {
        this.id = id;
    }

    public PedidoProducto(PedidoProductoPK id, int cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }

    public PedidoProductoPK getId() {
        return id;
    }

    public void setId(PedidoProductoPK id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PedidoProducto)) {
            return false;
        }
        PedidoProducto other = (PedidoProducto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.palermotenis.model.beans.pedidos.PedidoProducto[pedidoProductoPK=" + id + "]";
    }
}
