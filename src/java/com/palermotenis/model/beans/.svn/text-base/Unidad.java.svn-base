/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.model.beans;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
 * @author Poly
 */
@Entity
@Table(name = "tipo_atributos_unidades")
@NamedQueries({
    @NamedQuery(name = "Unidad.findAll", query = "SELECT u FROM Unidad u"),
    @NamedQuery(name = "Unidad.findById", query = "SELECT u FROM Unidad u WHERE u.id = :id")
})
public class Unidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "unidad")
    private Collection<TipoAtributo> tipoAtributoCollection;

    public Unidad() {
    }

    public Unidad(Integer id) {
        this.id = id;
    }

    public Unidad(Integer id, String nombre, String desc) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = desc;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Collection<TipoAtributo> getTipoAtributoCollection() {
        return tipoAtributoCollection;
    }

    public void setTipoAtributoCollection(Collection<TipoAtributo> tipoAtributoCollection) {
        this.tipoAtributoCollection = tipoAtributoCollection;
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
        if (!(object instanceof Unidad)) {
            return false;
        }
        Unidad other = (Unidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "test.Unidad[id=" + id + "]";
    }

}
