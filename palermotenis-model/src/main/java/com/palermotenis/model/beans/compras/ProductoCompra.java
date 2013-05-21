/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */

package com.palermotenis.model.beans.compras;

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

import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.proveedores.Proveedor;

/**
 * 
 * @author Poly
 */
@Entity
@Table(name = "productos_compras")
@NamedQueries(
    {
            @NamedQuery(name = "ProductoCompra.findAll", query = "SELECT p FROM ProductoCompra p"),
            @NamedQuery(name = "ProductoCompra.findById", query = "SELECT p FROM ProductoCompra p WHERE p.id = :id"),
            @NamedQuery(name = "ProductoCompra.findByCosto",
                    query = "SELECT p FROM ProductoCompra p WHERE p.costo = :costo") })
public class ProductoCompra implements Serializable {
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
    @Column(name = "Costo")
    private double costo;

    @Basic(optional = false)
    @Column(name = "Cantidad")
    private Integer cantidad;

    @JoinColumn(name = "Compra", referencedColumnName = "ID")
    @ManyToOne
    private Compra compra;

    @JoinColumn(name = "Proveedor", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Proveedor proveedor;

    @JoinColumn(name = "Stock", referencedColumnName = "ID")
    @ManyToOne
    private Stock stock;

    public ProductoCompra() {
    }

    public ProductoCompra(Integer id) {
        this.id = id;
    }

    public ProductoCompra(Integer id, String producto, double costo) {
        this.id = id;
        this.producto = producto;
        this.costo = costo;
    }

    public ProductoCompra(String producto, double costo, Integer cantidad, Proveedor proveedor, Stock stock) {
        this.producto = producto;
        this.costo = costo;
        this.cantidad = cantidad;
        this.proveedor = proveedor;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
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
        if (!(object instanceof ProductoCompra)) {
            return false;
        }
        ProductoCompra other = (ProductoCompra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProductoCompra[id=" + id + "]";
    }

}
