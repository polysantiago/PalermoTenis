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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.annotations.Type;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributoSimple;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.valores.Valor;
import com.palermotenis.model.exceptions.IllegalValueException;

@Entity
@Table(name = "atributos", catalog = "palermotenis", schema = "")
@NamedQueries(
    { @NamedQuery(name = "AtributoSimple.findByProducto,Tipo",
            query = "SELECT a FROM AtributoSimple a WHERE a.tipoAtributo = :tipoAtributo AND a.producto = :producto") })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("(SELECT t.tipo FROM tipo_atributos t WHERE t.ID = TipoAtributo)")
@DiscriminatorValue("S")
public class AtributoSimple implements Serializable {

    private static final long serialVersionUID = 2543183923873746514L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @JoinColumn(name = "tipoatributo", insertable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TipoAtributoSimple tipoAtributo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Producto")
    private Producto producto;

    @Type(type = "com.palermotenis.model.hibernate.types.ValorType")
    @Columns(columns =
        { @Column(name = "valor"), @Column(name = "tipoAtributo") })
    private Valor valor;

    public AtributoSimple() {
    }

    public AtributoSimple(TipoAtributoSimple tipoAtributo) {
        this.tipoAtributo = tipoAtributo;
    }

    public AtributoSimple(TipoAtributoSimple tipoAtributo, Producto producto) {
        this.tipoAtributo = tipoAtributo;
        this.producto = producto;
    }

    public AtributoSimple(TipoAtributoSimple tipoAtributo, Valor valor) {
        this(tipoAtributo);
        this.valor = valor;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the valor
     */
    public Valor getValor() {
        return valor;
    }

    /**
     * @return the tipoAtributo
     */
    public TipoAtributoSimple getTipoAtributo() {
        return tipoAtributo;
    }

    /**
     * @param tipoAtributo
     *            the tipoAtributo to set
     */
    public void setTipoAtributo(TipoAtributoSimple tipoAtributo) {
        this.tipoAtributo = tipoAtributo;
    }

    /**
     * @return the producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param valor
     *            the valor to set
     */
    public void setValor(Valor valor) throws IllegalValueException {
        this.valor = valor;
    }

    /**
     * @param producto
     *            the producto to set
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
