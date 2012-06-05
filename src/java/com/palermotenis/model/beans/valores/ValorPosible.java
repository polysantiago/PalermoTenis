/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans.valores;

import com.palermotenis.model.beans.atributos.AtributoTipado;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoTipado;
import com.palermotenis.model.exceptions.IllegalValueException;
import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.beanutils.ConvertUtils;
import org.hibernate.annotations.DiscriminatorFormula;

/**
 *
 * @author Poly
 */
@Entity
@Table(name = "valores_posibles", catalog = "palermotenis", schema = "")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
    @NamedQuery(name = "ValorPosible.findByTipoAtributo",
    query = "SELECT v FROM ValorPosible v WHERE v.tipoAtributo = :tipoAtributo ORDER BY v.valor")
})
@DiscriminatorFormula("(SELECT t.tipo FROM tipo_atributos t WHERE t.ID = TipoAtributo AND t.tipo NOT IN ('S'))")
@DiscriminatorValue("T")
public class ValorPosible extends Valor implements Serializable, Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @JoinColumn(name = "TipoAtributo", referencedColumnName = "ID")
    @ManyToOne
    private TipoAtributoTipado tipoAtributo;

    @Basic(optional = false)
    @Column(name = "valor")
    private String valor;

    @Column(name = "orden", insertable = false)
    private Integer orden;

    @OneToMany(mappedBy = "valorPosible", fetch = FetchType.LAZY)
    private Collection<AtributoTipado> atributos;

    @Transient
    private transient Object unidad;

    public ValorPosible(){}

    public ValorPosible(String valor) {
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the tipoAtributo
     */
    @Override
    public TipoAtributoTipado getTipoAtributo() {
        return tipoAtributo;
    }

    /**
     * @param tipoAtributo the tipoAtributo to set
     */
    public void setTipoAtributo(TipoAtributoTipado tipoAtributo) {
        this.tipoAtributo = tipoAtributo;
    }

    /**
     * @return the unidad
     */
    @Override
    public Object getUnidad() {
        return ConvertUtils.convert(valor, tipoAtributo.getClase());
    }

    /**
     * @param unidad the unidad to set
     */
    @Override
    public void setUnidad(Object unidad) throws IllegalValueException {
        if (!(unidad.getClass().isAssignableFrom(tipoAtributo.getClase()))) {
            throw new IllegalValueException("El valor no es correcto");
        } else {
            this.unidad = unidad;
            valor = unidad.toString();
        }
    }

    /**
     * @return the orden
     */
    public Integer getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public int compareTo(Object o) {
        ValorPosible otherValorPosible = (ValorPosible)o;
        int anotherVal = otherValorPosible.getOrden();
        return this.orden.compareTo(anotherVal);
    }

    /**
     * @return the atributos
     */
    public Collection<AtributoTipado> getAtributos() {
        return atributos;
    }

    /**
     * @param atributos the atributos to set
     */
    public void setAtributos(Collection<AtributoTipado> atributos) {
        this.atributos = atributos;
    }
}
