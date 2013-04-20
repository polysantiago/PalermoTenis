/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.model.beans.imagenes.tipos;

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
@Table(name = "tipo_imagenes")
@NamedQueries({
    @NamedQuery(name = "TipoImagen.findAll", query = "SELECT t FROM TipoImagen t"),
    @NamedQuery(name = "TipoImagen.findByTipo", query = "SELECT t FROM TipoImagen t WHERE t.tipo = :tipo"),
    @NamedQuery(name = "TipoImagen.findByNombre", query = "SELECT t FROM TipoImagen t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoImagen.findByHeight", query = "SELECT t FROM TipoImagen t WHERE t.height = :height"),
    @NamedQuery(name = "TipoImagen.findByWidth", query = "SELECT t FROM TipoImagen t WHERE t.width = :width")
})
public class TipoImagen implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Tipo")
    private Character tipo;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Height")
    private int height;
    @Basic(optional = false)
    @Column(name = "Width")
    private int width;

    public TipoImagen() {
    }

    public TipoImagen(Character tipo) {
        this.tipo = tipo;
    }

    public TipoImagen(Character tipo, String nombre, int height, int width) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.height = height;
        this.width = width;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipo != null ? tipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoImagen)) {
            return false;
        }
        TipoImagen other = (TipoImagen) object;
        if ((this.tipo == null && other.tipo != null) || (this.tipo != null && !this.tipo.equals(other.tipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.palermotenis.model.beans.imagenes.tipos.TipoImagen[tipo=" + tipo + "]";
    }

}
