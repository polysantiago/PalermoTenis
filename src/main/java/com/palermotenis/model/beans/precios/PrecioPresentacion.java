package com.palermotenis.model.beans.precios;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Target;

import com.palermotenis.model.beans.precios.pks.PrecioPK;
import com.palermotenis.model.beans.precios.pks.PrecioPresentacionPK;

@Entity
@Table(name = "precios_presentaciones", catalog = "palermotenis", schema = "")
@AssociationOverrides(
    { @AssociationOverride(name = "id.presentacion", joinColumns = @JoinColumn(name = "presentacion")),
            @AssociationOverride(name = "id.producto", joinColumns = @JoinColumn(name = "producto")),
            @AssociationOverride(name = "id.pago", joinColumns = @JoinColumn(name = "pago")),
            @AssociationOverride(name = "id.moneda", joinColumns = @JoinColumn(name = "moneda")) })
@NamedQueries(
    {
            @NamedQuery(name = "PrecioPresentacion.findByProducto",
                    query = "SELECT p FROM PrecioPresentacion p WHERE p.id.producto = :producto"),
            @NamedQuery(
                    name = "PrecioPresentacion.findByProducto,Presentacion",
                    query = "SELECT p FROM PrecioPresentacion p WHERE p.id.producto = :producto AND p.id.presentacion = :presentacion"),
            @NamedQuery(
                    name = "PrecioPresentacion.findByProducto,Pago,Presentacion",
                    query = "SELECT p FROM PrecioPresentacion p WHERE p.id.pago = :pago AND p.id.producto = :producto AND p.id.presentacion = :presentacion") })
public class PrecioPresentacion extends Precio implements Serializable, Comparable<PrecioPresentacion> {

    private static final long serialVersionUID = 2765475741173528571L;

    @EmbeddedId
    @Target(PrecioPresentacionPK.class)
    protected PrecioPK id;

    public PrecioPresentacion() {
    }

    public PrecioPresentacion(PrecioPK id) {
        this.id = id;
    }

    public PrecioPresentacion(PrecioPK id, Double valor) {
        this.id = id;
        setValor(valor);
    }

    @Override
    public PrecioPK getId() {
        return id;
    }

    @Override
    public void setId(PrecioPK id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PrecioPresentacion other = (PrecioPresentacion) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public int compareTo(PrecioPresentacion o) {
        Double thisVal = (isEnOferta()) ? getValorOferta() : getValor();
        Double otherVal = (o.isEnOferta()) ? o.getValorOferta() : o.getValor();
        return thisVal.compareTo(otherVal);
    }
}
