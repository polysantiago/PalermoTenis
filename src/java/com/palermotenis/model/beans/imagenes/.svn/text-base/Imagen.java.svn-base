/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans.imagenes;

import com.palermotenis.model.beans.Modelo;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

/**
 *
 * @author Poly
 */
@Entity
@Table(name = "imagenes")
@NamedQueries({
    @NamedQuery(name = "Imagen.findAll", query = "SELECT i FROM Imagen i"),
    @NamedQuery(name = "Imagen.findById", query = "SELECT i FROM Imagen i WHERE i.id = :id"),
    @NamedQuery(name = "Imagen.findByHashKey", query = "SELECT i FROM Imagen i WHERE i.hashKey = :hashKey"),
    @NamedQuery(name = "Imagen.findByTipo", query = "SELECT i FROM Imagen i WHERE i.tipo = :tipo"),
    @NamedQuery(name = "Imagen.findByTamanio", query = "SELECT i FROM Imagen i WHERE i.tamanio = :tamanio"),
    @NamedQuery(name = "Imagen.findByNombre", query = "SELECT i FROM Imagen i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "Imagen.findByCategoria", query = "SELECT i FROM Imagen i WHERE i.categoria = :categoria")

})
@Immutable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Imagen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = true)
    @Column(name = "HashKey")
    private String hashKey;

    @Basic(optional = false)
    @Column(name = "Tipo")
    private String tipo;

    @Basic(optional = false)
    @Column(name = "Tamanio")
    private long tamanio;

    @Basic(optional = false)
    @Column(name = "Ancho")
    private int ancho;

    @Basic(optional = false)
    @Column(name = "Alto")
    private int alto;

    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Categoria")
    private String categoria;

    @OneToMany(mappedBy = "imagenOriginal", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<ImagenEscalada> imagenesEscaladas;

    @JoinColumn(name = "Modelo", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Modelo modelo;


    public Imagen() {
    }

    public Imagen(Integer id) {
        this.id = id;
    }

    public Imagen(Integer id, String tipo, long tamanio, String nombre, String categoria) {
        this.id = id;
        this.tipo = tipo;
        this.tamanio = tamanio;
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public long getTamanio() {
        return tamanio;
    }

    public void setTamanio(long tamanio) {
        this.tamanio = tamanio;
    }

    /**
     * @return the ancho
     */
    public int getAncho() {
        return ancho;
    }

    /**
     * @param ancho the ancho to set
     */
    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    /**
     * @return the alto
     */
    public int getAlto() {
        return alto;
    }

    /**
     * @param alto the alto to set
     */
    public void setAlto(int alto) {
        this.alto = alto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the modelo
     */
    public Modelo getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the imagenesEscaladas
     */
    public Collection<ImagenEscalada> getImagenesEscaladas() {
        return imagenesEscaladas;
    }

    /**
     * @param imagenesEscaladas the imagenesEscaladas to set
     */
    public void setImagenesEscaladas(Collection<ImagenEscalada> imagenesEscaladas) {
        this.imagenesEscaladas = imagenesEscaladas;
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
        if (!(object instanceof Imagen)) {
            return false;
        }
        Imagen other = (Imagen) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.palermotenis.model.beans.Imagen[id=" + id + "]";
    }
}
