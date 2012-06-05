/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans.compras;

import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.proveedores.Proveedor;
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
@Table(name = "costos")
@NamedQueries({
    @NamedQuery(name = "Costo.findById",
    query = "SELECT c FROM Costo c WHERE c.id = :id"),
    @NamedQuery(name = "Costo.findByProducto",
    query = "SELECT c FROM Costo c WHERE c.producto = :producto"),
    @NamedQuery(name = "Costo.findByCosto",
    query = "SELECT c FROM Costo c WHERE c.costo = :costo"),
    @NamedQuery(name = "Costo.findByProducto,Proveedor",
    query = "SELECT c FROM Costo c WHERE c.proveedor = :proveedor and c.producto = :producto"),
    @NamedQuery(name = "Costo.findByProducto,Proveedor,Presentacion",
    query = "SELECT c FROM Costo c WHERE c.proveedor = :proveedor and c.producto = :producto and c.presentacion = :presentacion")
})
public class Costo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Costo")
    private Double costo;
    @JoinColumn(name = "Producto", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Producto producto;
    @JoinColumn(name = "Presentacion", referencedColumnName = "ID")
    @ManyToOne(optional = true)
    private Presentacion presentacion;
    @JoinColumn(name = "Proveedor", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Proveedor proveedor;
    @JoinColumn(name = "Moneda", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Moneda moneda;

    public Costo() {
    }

    public Costo(Double costo, Producto producto, Proveedor proveedor, Moneda moneda) {
        this.costo = costo;
        this.producto = producto;
        this.proveedor = proveedor;
        this.moneda = moneda;
    }

    public Costo(Double costo, Producto producto, Presentacion presentacion, Proveedor proveedor, Moneda moneda) {
        this.costo = costo;
        this.producto = producto;
        this.presentacion = presentacion;
        this.proveedor = proveedor;
        this.moneda = moneda;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Presentacion getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(Presentacion presentacion) {
        this.presentacion = presentacion;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
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
        if (!(object instanceof Costo)) {
            return false;
        }
        Costo other = (Costo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "$" + costo;
    }
}
