/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans.atributos.tipos;

import com.palermotenis.model.beans.atributos.AtributoSimple;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 *
 * @author Poly
 */
@Entity
@DiscriminatorValue("M")
public class TipoAtributoMultipleValores extends TipoAtributoTipado {

    @Transient
    private transient final char tipo = 'M';

    @Override
    public char getTipo() {
        return tipo;
    }

    public String getValores() {
        StringBuilder valor = new StringBuilder();
        for (AtributoSimple a : getAtributos()) {
            valor.append(a.getValor().getNombre()).append(", ");
        }
        valor.delete(valor.lastIndexOf(", "), valor.length() - 1);
        return valor.toString();
    }
}
