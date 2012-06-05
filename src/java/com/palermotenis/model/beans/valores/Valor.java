/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans.valores;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import java.io.Serializable;
import com.palermotenis.model.exceptions.IllegalValueException;

/**
 *
 * @author Poly
 */
public class Valor implements Serializable {

    private Object unidad;
    private TipoAtributo tipoAtributo;

    public Valor() {
    }

    public Valor(TipoAtributo tipoAtributo) {
        this.tipoAtributo = tipoAtributo;
    }

    /**
     * @return the valor
     */
    public Object getUnidad() {
        return unidad;
    }

    /**
     * @param valor the valor to set
     */
    public void setUnidad(Object unidad) throws IllegalValueException {
        this.unidad = unidad;
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

    public String getNombre(){
     if(getTipoAtributo().getUnidad() == null){
         return getUnidad().toString();
     } else {
         return getUnidad().toString() + " " + getTipoAtributo().getUnidad().getNombre();
     }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ValorPosible) {
            ValorPosible va = (ValorPosible) obj;
            return getUnidad().equals(va.getUnidad());
        } else if (getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.unidad != null ? this.unidad.hashCode() : 0);
        hash = 53 * hash + (this.tipoAtributo != null ? this.tipoAtributo.hashCode() : 0);
        return hash;
    }
}
