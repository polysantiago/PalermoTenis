/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans.atributos.tipos;

import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.*;
import com.palermotenis.model.beans.atributos.AtributoSimple;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Type;

/**
 *
 * @author Poly
 */
@Entity
@Table(name = "tipo_atributos", catalog = "palermotenis", schema = "")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="Tipo",
    discriminatorType=DiscriminatorType.CHAR
)
@DiscriminatorValue("S")
@NamedQueries({
    @NamedQuery(name = "TipoAtributo.findAll", query = "SELECT t FROM TipoAtributo t"),
    @NamedQuery(name = "TipoAtributo.findByNombre", query = "SELECT t FROM TipoAtributo t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoAtributo.findByTipoProducto", query = "SELECT t FROM TipoAtributo t WHERE t.tipoProducto = :tipoProducto ORDER BY t.nombre")
})
public class TipoAtributo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "clase")
    @Type(type = "class")
    private Class clase;

    @ManyToOne
    @JoinColumn(name = "unidad", referencedColumnName = "ID")
    private Unidad unidad;

    @JoinColumn(name = "tipoProducto", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoProducto tipoProducto;

    @OneToMany(mappedBy = "tipoAtributo", fetch = FetchType.LAZY)
    private Collection<AtributoSimple> atributos;

    @Transient
    private transient final char tipo = 'S';

    /**
     * @return the tipo
     */
    public char getTipo() {
        return tipo;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the tipo
     */
    public Class getClase() {
        return clase;
    }

    /**
     * @return the tipoProducto
     */
    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }


    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setClase(Class tipo) {
        this.clase = tipo;
    }

    /**
     * @return the unidad
     */
    public Unidad getUnidad() {
        return unidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    /**
     * @param tipoProducto the tipoProducto to set
     */
    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    /**
     * @return the atributos
     */
    public Collection<AtributoSimple> getAtributos() {
        return atributos;
    }

    /**
     * @param atributos the atributos to set
     */
    public void setAtributos(Collection<AtributoSimple> atributos) {
        this.atributos = atributos;
    }

}
