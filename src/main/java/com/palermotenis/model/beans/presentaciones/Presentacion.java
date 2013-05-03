/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */

package com.palermotenis.model.beans.presentaciones;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.precios.PrecioPresentacion;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.beans.productos.Producto;

/**
 * 
 * @author Poly
 */
@Entity
@Table(name = "presentaciones")
@NamedQueries(
    {
            @NamedQuery(name = "Presentacion.findAll", query = "SELECT p FROM Presentacion p"),
            @NamedQuery(name = "Presentacion.findById", query = "SELECT p FROM Presentacion p WHERE p.id = :id"),
            @NamedQuery(name = "Presentacion.findByCantidad",
                    query = "SELECT p FROM Presentacion p WHERE p.cantidad = :cantidad"),
            @NamedQuery(name = "Presentacion.findByUnidad",
                    query = "SELECT p FROM Presentacion p WHERE p.unidad = :unidad"),
            @NamedQuery(name = "Presentacion.findByNombre",
                    query = "SELECT p FROM Presentacion p WHERE p.nombre = :nombre"),
            @NamedQuery(name = "Presentacion.findByTipo",
                    query = "SELECT p FROM Presentacion p WHERE p.tipoPresentacion = :tipo"),
            @NamedQuery(name = "Presentacion.findByTipoList",
                    query = "SELECT p FROM Presentacion p WHERE p.tipoPresentacion IN (:tipoList)") })
public class Presentacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "Cantidad")
    private double cantidad;

    @Column(name = "Unidad")
    private String unidad;

    @Column(name = "Nombre")
    private String nombre;

    @JoinColumn(name = "TipoPresentacion", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TipoPresentacion tipoPresentacion;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "precios_presentaciones", joinColumns = @JoinColumn(name = "presentacion"),
            inverseJoinColumns = @JoinColumn(name = "producto"))
    private Collection<Producto> productos;

    @OneToMany(mappedBy = "presentacion", fetch = FetchType.LAZY)
    private Collection<Stock> stocks;

    @OneToMany(mappedBy = "id.presentacion", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<PrecioPresentacion> precios;

    public Presentacion() {
    }

    public Presentacion(Integer id) {
        this.id = id;
    }

    public Presentacion(Integer id, double cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getNombre() {
        if (nombre == null) {
            return "x" + cantidad + " " + unidad;
        }
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoPresentacion getTipoPresentacion() {
        return tipoPresentacion;
    }

    public void setTipoPresentacion(TipoPresentacion tipoPresentacion) {
        this.tipoPresentacion = tipoPresentacion;
    }

    /**
     * @return the productos
     */
    public Collection<Producto> getProductos() {
        return productos;
    }

    /**
     * @param productos
     *            the productos to set
     */
    public void setProductos(Collection<Producto> productos) {
        this.productos = productos;
    }

    /**
     * @return the precios
     */
    public Collection<PrecioPresentacion> getPrecios() {
        return precios;
    }

    /**
     * @param precios
     *            the precios to set
     */
    public void setPrecios(Collection<PrecioPresentacion> precios) {
        this.precios = precios;
    }

    /**
     * @return the stocks
     */
    public Collection<Stock> getStocks() {
        return stocks;
    }

    /**
     * @param stocks
     *            the stocks to set
     */
    public void setStocks(Collection<Stock> stocks) {
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        return "Presentacion[unidad=" + unidad + ",cantidad=" + cantidad + "]";
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
        if (!(object instanceof Presentacion)) {
            return false;
        }
        Presentacion other = (Presentacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
