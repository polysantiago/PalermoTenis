/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.model.beans.atributos.tipos.clasif;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Poly
 */
@Entity
@Table(name = "tipo_atributos_clasif")
@NamedQueries({
    @NamedQuery(name = "TipoAtributoClasif.findAll", query = "SELECT t FROM TipoAtributoClasif t"),
    @NamedQuery(name = "TipoAtributoClasif.findByClasif", query = "SELECT t FROM TipoAtributoClasif t WHERE t.clasif = :clasif"),
    @NamedQuery(name = "TipoAtributoClasif.findByNombre", query = "SELECT t FROM TipoAtributoClasif t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoAtributoClasif.findByClase", query = "SELECT t FROM TipoAtributoClasif t WHERE t.clase = :clase")
})
public class TipoAtributoClasif implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "Clasif")
    private Character clasif;

    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;

    @Basic(optional = false)
    @Column(name = "Clase")
    private String clase;
    
    @Column(name = "Descripcion")
    private String descripcion;


    public TipoAtributoClasif() {
    }

    public TipoAtributoClasif(Character clasif) {
        this.clasif = clasif;
    }

    public TipoAtributoClasif(Character clasif, String nombre) {
        this.clasif = clasif;
        this.nombre = nombre;
    }

    public Character getClasif() {
        return clasif;
    }

    public void setClasif(Character clasif) {
        this.clasif = clasif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the clase
     */
    public String getClase() {
        return clase;
    }

    /**
     * @param clase the clase to set
     */
    public void setClase(String clase) {
        this.clase = clase;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clasif != null ? clasif.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoAtributoClasif)) {
            return false;
        }
        TipoAtributoClasif other = (TipoAtributoClasif) object;
        if ((this.clasif == null && other.clasif != null) || (this.clasif != null && !this.clasif.equals(other.clasif))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.palermotenis.model.beans.atributos.tipos.clasif.TipoAtributoClasif[clasif=" + clasif + "]";
    }

}
