/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.palermotenis.model.beans.geograficos;

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

import com.palermotenis.model.beans.Idioma;
import com.palermotenis.model.beans.Moneda;

/**
 * 
 * @author Poly
 */
@Entity
@Table(name = "paises")
@NamedQueries(
    { @NamedQuery(name = "Pais.findAll", query = "SELECT p FROM Pais p"),
            @NamedQuery(name = "Pais.findById", query = "SELECT p FROM Pais p WHERE p.id = :id"),
            @NamedQuery(name = "Pais.findByCodigo", query = "SELECT p FROM Pais p WHERE p.codigo = :codigo"),
            @NamedQuery(name = "Pais.findByNombre", query = "SELECT p FROM Pais p WHERE p.nombre = :nombre") })
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

    public Pais(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Pais(String codigo, String nombre, Idioma idioma, Moneda moneda) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.idioma = idioma;
        this.moneda = moneda;
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
     * @param provincias
     *            the provincias to set
     */
    public void setProvincias(Collection<Provincia> provincias) {
        this.provincias = provincias;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
        result = prime * result + ((idioma == null) ? 0 : idioma.hashCode());
        result = prime * result + ((moneda == null) ? 0 : moneda.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Pais other = (Pais) obj;
        if (codigo == null) {
            if (other.codigo != null) {
                return false;
            }
        } else if (!codigo.equals(other.codigo)) {
            return false;
        }
        if (idioma == null) {
            if (other.idioma != null) {
                return false;
            }
        } else if (!idioma.equals(other.idioma)) {
            return false;
        }
        if (moneda == null) {
            if (other.moneda != null) {
                return false;
            }
        } else if (!moneda.equals(other.moneda)) {
            return false;
        }
        if (nombre == null) {
            if (other.nombre != null) {
                return false;
            }
        } else if (!nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "test.Pais[id=" + id + "]";
    }
}
