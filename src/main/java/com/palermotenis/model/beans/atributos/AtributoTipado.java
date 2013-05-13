package com.palermotenis.model.beans.atributos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.palermotenis.model.beans.valores.ValorPosible;

@Entity
@DiscriminatorValue("T")
public class AtributoTipado extends AtributoSimple {

    private static final long serialVersionUID = 3025928027714859529L;

    @JoinColumn(name = "ValorPosible", referencedColumnName = "ID")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private ValorPosible valorPosible;

    public ValorPosible getValorPosible() {
        return valorPosible;
    }

    public void setValorPosible(ValorPosible valorPosible) {
        setValor(valorPosible);
        this.valorPosible = valorPosible;
    }
}
