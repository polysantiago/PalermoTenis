/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.model.beans.imagenes;

import com.palermotenis.model.beans.imagenes.tipos.TipoImagen;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

/**
 *
 * @author Poly
 */
@Entity
@Table(name = "imagenes_escaladas")
@NamedQueries({
    @NamedQuery(name = "ImagenEscalada.findAll", query = "SELECT i FROM ImagenEscalada i"),
    @NamedQuery(name = "ImagenEscalada.findById", query = "SELECT i FROM ImagenEscalada i WHERE i.id = :id"),
    @NamedQuery(name = "ImagenEscalada.findByContentType", query = "SELECT i FROM ImagenEscalada i WHERE i.contentType = :contentType"),
    @NamedQuery(name = "ImagenEscalada.findByTamanio", query = "SELECT i FROM ImagenEscalada i WHERE i.tamanio = :tamanio"),
    @NamedQuery(name = "ImagenEscalada.findByNombre", query = "SELECT i FROM ImagenEscalada i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "ImagenEscalada.findByTipoImagen", query = "SELECT i FROM ImagenEscalada i WHERE i.imagenOriginal = :imagen and i.tipo = :tipoImagen")
})
@Immutable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class ImagenEscalada implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "ContentType")
    private String contentType;

    @Basic(optional = false)
    @Lob
    @Column(name = "Imagen")
    private byte[] imagen;

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

    @JoinColumn(name = "Tipo", referencedColumnName = "Tipo")
    @ManyToOne(optional = false)
    private TipoImagen tipo;
    
    @JoinColumn(name = "ImagenOriginal", referencedColumnName = "ID")
    @OneToOne(optional = false)
    private Imagen imagenOriginal;

    public ImagenEscalada() {
    }

    public ImagenEscalada(Integer id) {
        this.id = id;
    }

    public ImagenEscalada(Integer id, String contentType, byte[] imagen, long tamanio, String nombre) {
        this.id = id;
        this.contentType = contentType;
        this.imagen = imagen;
        this.tamanio = tamanio;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public long getTamanio() {
        return tamanio;
    }

    public void setTamanio(long tamanio) {
        this.tamanio = tamanio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoImagen getTipo() {
        return tipo;
    }

    public void setTipo(TipoImagen tipo) {
        this.tipo = tipo;
    }

    public Imagen getImagenOriginal() {
        return imagenOriginal;
    }

    public void setImagenOriginal(Imagen imagenOriginal) {
        this.imagenOriginal = imagenOriginal;
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
        if (!(object instanceof ImagenEscalada)) {
            return false;
        }
        ImagenEscalada other = (ImagenEscalada) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.palermotenis.model.beans.imagenes.ImagenEscalada[id=" + id + "]";
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

}
