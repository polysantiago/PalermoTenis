/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans.atributos.tipos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 *
 * @author Poly
 */
@Entity
@DiscriminatorValue("C")
public class TipoAtributoClasificatorio extends TipoAtributoTipado {

    @Transient
    private transient final char tipo = 'C';

    @Override
    public char getTipo() {
        return tipo;
    }
}
