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

import com.palermotenis.model.beans.geograficos.Pais;

/**
 * 
 * @author Poly
 */
@Entity
@Table(name = "idiomas")
@NamedQueries(
    { @NamedQuery(name = "Idioma.findAll", query = "SELECT i FROM Idioma i"),
            @NamedQuery(name = "Idioma.findById", query = "SELECT i FROM Idioma i WHERE i.id = :id"),
            @NamedQuery(name = "Idioma.findByCodigo", query = "SELECT i FROM Idioma i WHERE i.codigo = :codigo"),
            @NamedQuery(name = "Idioma.findByNombre", query = "SELECT i FROM Idioma i WHERE i.nombre = :nombre") })
public class Idioma implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Codigo")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idioma")
    private Collection<Pais> paises;

    public Idioma() {
    }

    public Idioma(Integer id) {
        this.id = id;
    }

    public Idioma(Integer id, String codigo, String nombre) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Idioma(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Collection<Pais> getPaises() {
        return paises;
    }

    public void setPaises(Collection<Pais> paises) {
        this.paises = paises;
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
        if (!(object instanceof Idioma)) {
            return false;
        }
        Idioma other = (Idioma) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "test.Idioma[id=" + id + "]";
    }

}
