/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.model.beans.ventas;

import com.palermotenis.model.beans.Stock;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Poly
 */
@Entity
@Table(name = "productos_ventas")
@NamedQueries({
    @NamedQuery(name = "ProductoVenta.findAll", query = "SELECT p FROM ProductoVenta p"),
    @NamedQuery(name = "ProductoVenta.findById", query = "SELECT p FROM ProductoVenta p WHERE p.id = :id"),
    @NamedQuery(name = "ProductoVenta.findByProducto", query = "SELECT p FROM ProductoVenta p WHERE p.producto = :producto"),
    @NamedQuery(name = "ProductoVenta.findByPrecioUnitario", query = "SELECT p FROM ProductoVenta p WHERE p.precioUnitario = :precioUnitario"),
    @NamedQuery(name = "ProductoVenta.findBySubtotal", query = "SELECT p FROM ProductoVenta p WHERE p.subtotal = :subtotal")
})
public class ProductoVenta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "Producto")
    private String producto;

    @Basic(optional = false)
    @Column(name = "PrecioUnitario")
    private double precioUnitario;

    @Basic(optional = false)
    @Column(name = "Subtotal")
    private double subtotal;
    
    @JoinColumn(name = "Venta", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Venta venta;

    @JoinColumn(name = "Stock", referencedColumnName = "ID")
    @ManyToOne
    private Stock stock;

    public ProductoVenta() {
    }

    public ProductoVenta(String producto, double precioUnitario, double subtotal) {
        this.producto = producto;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
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

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
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
        if (!(object instanceof ProductoVenta)) {
            return false;
        }
        ProductoVenta other = (ProductoVenta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.palermotenis.model.beans.ventas.ProductoVenta[id=" + id + "]";
    }

}
