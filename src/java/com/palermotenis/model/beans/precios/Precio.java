/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.model.beans.precios;

import com.palermotenis.model.beans.precios.pks.PrecioPK;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


/**
 *
 * @author Poly
 */
@MappedSuperclass
public abstract class Precio implements Serializable{

    @Column(name = "valor")
    private Double valor;

    @Column(name = "enOferta")
    @Basic(optional = false)
    private boolean enOferta;

    @Column(name = "valorOferta")
    private Double valorOferta;

    @Column(name = "orden")
    private Integer orden = 0;

    /**
     * @return the precio
     */
    public Double getValor() {
        return valor;
    }

    /**
     * @param precio the precio to set
     */
    public void setValor(Double valor) {
        this.valor = valor;
    }

    /**
     * @return the enOferta
     */
    public boolean isEnOferta() {
        return enOferta;
    }

    /**
     * @param enOferta the enOferta to set
     */
    public void setEnOferta(boolean enOferta) {
        this.enOferta = enOferta;
    }

    /**
     * @return the valorOferta
     */
    public Double getValorOferta() {
        return valorOferta;
    }

    /**
     * @param valorOferta the valorOferta to set
     */
    public void setValorOferta(Double valorOferta) {
        this.valorOferta = valorOferta;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    /**
     * @return the pk
     */
    public abstract PrecioPK getId();

    /**
     * @param pk the pk to set
     */
    public abstract void setId(PrecioPK id);

}
