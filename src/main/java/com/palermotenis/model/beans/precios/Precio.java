package com.palermotenis.model.beans.precios;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.palermotenis.model.beans.precios.pks.PrecioPK;

@MappedSuperclass
public abstract class Precio implements Serializable {

    private static final long serialVersionUID = 1409011188545320514L;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "enOferta")
    @Basic(optional = false)
    private boolean enOferta;

    @Column(name = "valorOferta")
    private Double valorOferta;

    @Column(name = "orden")
    private Integer orden = 0;

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public boolean isEnOferta() {
        return enOferta;
    }

    public void setEnOferta(boolean enOferta) {
        this.enOferta = enOferta;
    }

    public Double getValorOferta() {
        return valorOferta;
    }

    public void setValorOferta(Double valorOferta) {
        this.valorOferta = valorOferta;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public abstract PrecioPK getId();

    public abstract void setId(PrecioPK id);

}
