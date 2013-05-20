package com.palermotenis.model.beans.atributos;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
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
import javax.persistence.Table;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.annotations.Type;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.valores.Valor;
import com.palermotenis.model.exceptions.IllegalValueException;

@Entity
@Table(name = "atributos", catalog = "palermotenis")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("(SELECT t.tipo FROM tipo_atributos t WHERE t.ID = TipoAtributo)")
@DiscriminatorValue("X")
public abstract class Atributo implements Serializable {

    private static final long serialVersionUID = -8957129475855610452L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @JoinColumn(name = "tipoatributo", insertable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TipoAtributo tipoAtributo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Producto")
    private Producto producto;

    @Type(type = "com.palermotenis.model.hibernate.types.ValorType")
    @Columns(columns =
        { @Column(name = "valor"), @Column(name = "tipoAtributo") })
    private Valor valor;

    public Atributo() {
    }

    public Atributo(TipoAtributo tipoAtributo) {
        this.tipoAtributo = tipoAtributo;
    }

    public Atributo(TipoAtributo tipoAtributo, Valor valor) {
        this.tipoAtributo = tipoAtributo;
        this.valor = valor;
    }

    public Atributo(TipoAtributo tipoAtributo, Producto producto) {
        this.tipoAtributo = tipoAtributo;
        this.producto = producto;
    }

    public Atributo(TipoAtributo tipoAtributo, Producto producto, Valor valor) {
        this.tipoAtributo = tipoAtributo;
        this.producto = producto;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public Valor getValor() {
        return valor;
    }

    public TipoAtributo getTipoAtributo() {
        return tipoAtributo;
    }

    public void setTipoAtributo(TipoAtributo tipoAtributo) {
        this.tipoAtributo = tipoAtributo;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setValor(Valor valor) throws IllegalValueException {
        this.valor = valor;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

}
