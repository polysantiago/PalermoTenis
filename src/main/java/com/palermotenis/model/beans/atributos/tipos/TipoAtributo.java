package com.palermotenis.model.beans.atributos.tipos;

import java.io.Serializable;
import java.util.List;

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

import org.hibernate.annotations.Type;

import com.palermotenis.model.beans.Unidad;
import com.palermotenis.model.beans.atributos.Atributo;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;

@Entity
@Table(name = "tipo_atributos", catalog = "palermotenis", schema = "")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Tipo", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue(value = "X")
@NamedQueries(
    {
            @NamedQuery(name = "TipoAtributo.findAll", query = "SELECT t FROM TipoAtributo t"),
            @NamedQuery(name = "TipoAtributo.findByNombre",
                    query = "SELECT t FROM TipoAtributo t WHERE t.nombre = :nombre"),
            @NamedQuery(name = "TipoAtributo.findByTipoProducto",
                    query = "SELECT t FROM TipoAtributo t WHERE t.tipoProducto = :tipoProducto ORDER BY t.nombre") })
public abstract class TipoAtributo implements Serializable {

    private static final long serialVersionUID = -6172356379161375640L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "clase")
    @Type(type = "class")
    private Class<?> clase;

    @ManyToOne
    @JoinColumn(name = "unidad", referencedColumnName = "ID")
    private Unidad unidad;

    @JoinColumn(name = "tipoProducto", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoProducto tipoProducto;

    @OneToMany(mappedBy = "tipoAtributo", fetch = FetchType.LAZY)
    private List<Atributo> atributos;

    public TipoAtributo() {
    }

    public TipoAtributo(String nombre, Class<?> clase, TipoProducto tipoProducto) {
        this.nombre = nombre;
        this.clase = clase;
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

    public Class<?> getClase() {
        return clase;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setClase(Class<?> tipo) {
        this.clase = tipo;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public List<Atributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<Atributo> atributos) {
        this.atributos = atributos;
    }

}
