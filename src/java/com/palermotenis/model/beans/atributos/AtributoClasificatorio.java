/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans.atributos;

import com.palermotenis.model.beans.valores.ValorPosible;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Poly
 */
@Entity
@DiscriminatorValue("C")
public class AtributoClasificatorio extends AtributoTipado {

    /**
     * @return the valorPosible
     */
    @Override
    public ValorPosible getValorPosible() {
        return super.getValorPosible();
    }

    /**
     * @param valorPosible the valorPosible to set
     */
    @Override
    public void setValorPosible(ValorPosible valorPosible) {
        super.setValorPosible(valorPosible);
    }
}
