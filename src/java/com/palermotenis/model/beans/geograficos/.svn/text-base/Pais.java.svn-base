/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans.geograficos;

import com.palermotenis.model.beans.*;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author Poly
 */
@Entity
@Table(name = "paises")
@NamedQueries({
    @NamedQuery(name = "Pais.findAll", query = "SELECT p FROM Pais p"),
    @NamedQuery(name = "Pais.findById", query = "SELECT p FROM Pais p WHERE p.id = :id"),
    @NamedQuery(name = "Pais.findByCodigo", query = "SELECT p FROM Pais p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Pais.findByNombre", query = "SELECT p FROM Pais p WHERE p.nombre = :nombre")
})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Pais implements Serializable {

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
    @JoinColumn(name = "Idioma", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Idioma idioma;
    @JoinColumn(name = "Moneda", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Moneda moneda;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pais")
    private Collection<Provincia> provincias;

    public Pais() {
    }

    public Pais(Integer id) {
        this.id = id;
    }

    public Pais(Integer id, String codigo, String nombre) {
        this.id = id;
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

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    /**
     * @return the provincias
     */
    public Collection<Provincia> getProvincias() {
        return provincias;
    }

    /**
     * @param provincias the provincias to set
     */
    public void setProvincias(Collection<Provincia> provincias) {
        this.provincias = provincias;
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
        if (!(object instanceof Pais)) {
            return false;
        }
        Pais other = (Pais) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "test.Pais[id=" + id + "]";
    }
}
