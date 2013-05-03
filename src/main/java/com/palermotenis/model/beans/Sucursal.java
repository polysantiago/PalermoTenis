/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */

package com.palermotenis.model.beans;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author poly
 */
@Entity
@Table(name = "sucursales")
@NamedQueries(
    {
            @NamedQuery(name = "Sucursal.findAll", query = "SELECT s FROM Sucursal s"),
            @NamedQuery(name = "Sucursal.findById", query = "SELECT s FROM Sucursal s WHERE s.id = :id"),
            @NamedQuery(name = "Sucursal.findByNombre", query = "SELECT s FROM Sucursal s WHERE s.nombre = :nombre"),
            @NamedQuery(name = "Sucursal.findByTelefono",
                    query = "SELECT s FROM Sucursal s WHERE s.telefono = :telefono"),
            @NamedQuery(name = "Sucursal.findByDireccion",
                    query = "SELECT s FROM Sucursal s WHERE s.direccion = :direccion") })
public class Sucursal implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Telefono")
    private String telefono;

    @Column(name = "Direccion")
    private String direccion;

    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Stock> stocks;

    public Sucursal() {
    }

    public Sucursal(Integer id) {
        this.id = id;
    }

    public Sucursal(String nombre) {
        this.nombre = nombre;
    }

    public Sucursal(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Sucursal(String nombre, String telefono, String direccion) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Collection<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(Collection<Stock> stocks) {
        this.stocks = stocks;
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
        if (!(object instanceof Sucursal)) {
            return false;
        }
        Sucursal other = (Sucursal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sucursal[nombre=" + nombre + "]";
    }

}
