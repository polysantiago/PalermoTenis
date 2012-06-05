/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.model.beans.precios;

import com.palermotenis.model.beans.precios.pks.PrecioPK;
import com.palermotenis.model.beans.precios.pks.PrecioProductoPK;
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


/**
 *
 * @author Poly
 */
@Entity
@Table(name = "precios_unidad", catalog = "palermotenis", schema = "")
@AssociationOverrides({
    @AssociationOverride(name = "id.producto", joinColumns = @JoinColumn(name = "producto")),
    @AssociationOverride(name = "id.pago", joinColumns = @JoinColumn(name = "pago")),
    @AssociationOverride(name = "id.moneda", joinColumns = @JoinColumn(name = "moneda")),
    @AssociationOverride(name = "id.cuotas", joinColumns = @JoinColumn(name = "cuotas"))
})
@NamedQueries({
    @NamedQuery(name = "PrecioUnidad.findByProducto",
    query = "SELECT p FROM PrecioUnidad p WHERE p.id.producto = :producto"),
    @NamedQuery(name = "PrecioUnidad.findByProducto,Pago",
    query = "SELECT p FROM PrecioUnidad p WHERE p.id.pago = :pago AND p.id.producto = :producto")
})
public class PrecioUnidad extends Precio implements Serializable{

    @EmbeddedId
    @Target(PrecioProductoPK.class)
    protected PrecioPK id;

    public PrecioUnidad(){}

    public PrecioUnidad(PrecioPK id){
        this.id = id;
    }

    public PrecioUnidad(PrecioPK id, Double valor){
        this.id = id;
        setValor(valor);
    }

    /**
     * @return the id
     */
    public PrecioPK getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(PrecioPK id) {
        this.id = id;
    }

}
