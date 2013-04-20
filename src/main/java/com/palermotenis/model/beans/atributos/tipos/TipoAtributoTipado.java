/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans.atributos.tipos;


import com.palermotenis.model.beans.valores.Valor;
import com.palermotenis.model.beans.valores.ValorPosible;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 *
 * @author Poly
 */
@Entity
@DiscriminatorValue("T")
public class TipoAtributoTipado extends TipoAtributo {

    @Transient
    private transient final char tipo = 'T';

    @OneToMany(mappedBy = "tipoAtributo", cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE})
    private Collection<ValorPosible> valoresPosibles;


    /**
     * @return the tipo
     */
    @Override
    public char getTipo() {
        return tipo;
    }

    /**
     * @return the valoresPosibles
     */
    public Collection<ValorPosible> getValoresPosibles() {
        return valoresPosibles;
    }

    /**
     * @param valoresPosibles the valoresPosibles to set
     */
    public void setValoresPosibles(Collection<ValorPosible> valoresPosibles) {
        this.valoresPosibles = valoresPosibles;
    }

    public void addValorPosible(ValorPosible valorPosible) {
        if (hasValorPosible(valorPosible)) {
            //TODO handle exception
        }
        valoresPosibles.add(valorPosible);
    }

    public void removeValorPosible(ValorPosible valorPosible) {
        if (!hasValorPosible(valorPosible)) {
            //TODO handle exception
        }
        valoresPosibles.remove(valorPosible);
    }

    public boolean hasValorPosible(Valor valorPosible) {
        return valoresPosibles.contains(valorPosible);
    }

    public boolean hasValoresPosibles() {
        if (valoresPosibles == null) {
            return false;
        } else {
            return !valoresPosibles.isEmpty();
        }
    }


}
