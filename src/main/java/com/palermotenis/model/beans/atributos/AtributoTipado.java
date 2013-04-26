/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans.atributos;

import com.palermotenis.model.beans.valores.ValorPosible;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Poly
 */
@Entity
@DiscriminatorValue("T")
public class AtributoTipado extends AtributoSimple {
    
    @JoinColumn(name = "ValorPosible", referencedColumnName = "ID")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private ValorPosible valorPosible;

    /**
     * @return the valorPosible
     */
    public ValorPosible getValorPosible() {
        return valorPosible;
    }

    /**
     * @param valorPosible the valorPosible to set
     */
    public void setValorPosible(ValorPosible valorPosible) {
        setValor(valorPosible);
        this.valorPosible = valorPosible;
    }
}
