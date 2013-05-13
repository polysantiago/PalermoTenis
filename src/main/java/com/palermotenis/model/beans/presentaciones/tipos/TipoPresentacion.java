package com.palermotenis.model.beans.presentaciones.tipos;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Transient;

import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;

@Entity
@Table(name = "tipo_presentaciones")
@NamedQueries(
    {
            @NamedQuery(name = "TipoPresentacion.findAll", query = "SELECT t FROM TipoPresentacion t"),
            @NamedQuery(name = "TipoPresentacion.findById", query = "SELECT t FROM TipoPresentacion t WHERE t.id = :id"),
            @NamedQuery(name = "TipoPresentacion.findByTipoProducto",
                    query = "SELECT t FROM TipoPresentacion t WHERE t.tipoProducto = :tipoProducto"),
            @NamedQuery(name = "TipoPresentacion.findByNombre",
                    query = "SELECT t FROM TipoPresentacion t WHERE t.nombre = :nombre") })
public class TipoPresentacion implements Serializable, Comparable<TipoPresentacion> {

    private static final long serialVersionUID = 6576518147981650652L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;

    @JoinColumn(name = "TipoProducto", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TipoProducto tipoProducto;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "tipoPresentacion")
    private Collection<Presentacion> presentaciones;

    @Transient
    private Collection<Presentacion> presentacionesByProd = new ArrayList<Presentacion>();

    public TipoPresentacion() {
    }

    public TipoPresentacion(String nombre, TipoProducto tipoProducto) {
        this.nombre = nombre;
        this.tipoProducto = tipoProducto;
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

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public Collection<Presentacion> getPresentaciones() {
        return presentaciones;
    }

    public void setPresentaciones(Collection<Presentacion> presentaciones) {
        this.presentaciones = presentaciones;
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
        if (!(object instanceof TipoPresentacion)) {
            return false;
        }
        TipoPresentacion other = (TipoPresentacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TipoPresentacion[id=" + id + "]";
    }

    public Collection<Presentacion> getPresentacionesByProd() {
        return presentacionesByProd;
    }

    public void setPresentacionesByProd(Collection<Presentacion> presentacionesByProd) {
        this.presentacionesByProd = presentacionesByProd;
    }

    public void addPresentacionByProd(Presentacion presentacion) {
        if (hasPresentacionByProd(presentacion)) {
            return;
        }
        presentacionesByProd.add(presentacion);
    }

    public void removePresentacionByProd(Presentacion presentacion) {
        if (!hasPresentacionByProd(presentacion)) {
            return;
        }
        presentacionesByProd.remove(presentacion);
    }

    public boolean hasPresentacionByProd(Presentacion presentacion) {
        return presentacionesByProd.contains(presentacion);
    }

    @Override
    public int compareTo(TipoPresentacion o) {
        return nombre.compareTo(o.nombre);
    }

}
