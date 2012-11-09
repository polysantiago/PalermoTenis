/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.model.beans.atributos;

import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.valores.Valor;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import com.palermotenis.model.exceptions.IllegalValueException;
import javax.persistence.DiscriminatorValue;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.hibernate.annotations.DiscriminatorFormula;

/**
 *
 * @author Poly
 */
@Entity
@Table(name = "atributos", catalog = "palermotenis", schema = "")
@NamedQueries({
    @NamedQuery(name = "Atributo.findByProducto,Tipo",
    query = "SELECT a FROM Atributo a WHERE a.tipoAtributo = :tipoAtributo AND a.producto = :producto")
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("(SELECT t.tipo FROM tipo_atributos t WHERE t.ID = TipoAtributo)")
@DiscriminatorValue("S")
public class Atributo implements Serializable {

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
    @Columns(columns = {
        @Column(name = "valor"),
        @Column(name = "tipoAtributo")
    })
    private Valor valor;


    public Atributo(){
    }

    public Atributo(TipoAtributo tipoAtributo){
        this.tipoAtributo = tipoAtributo;
    }

    public Atributo(TipoAtributo tipoAtributo, Producto producto) {
        this.tipoAtributo = tipoAtributo;
        this.producto = producto;
    }

    public Atributo(TipoAtributo tipoAtributo, Valor valor){
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
    public TipoAtributo getTipoAtributo() {
        return tipoAtributo;
    }

    /**
     * @param tipoAtributo the tipoAtributo to set
     */
    public void setTipoAtributo(TipoAtributo tipoAtributo) {
        this.tipoAtributo = tipoAtributo;
    }

    /**
     * @return the producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Valor valor) throws IllegalValueException {
        this.valor = valor;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
