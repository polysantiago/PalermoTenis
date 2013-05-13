package com.palermotenis.model.beans.valores;

import java.io.Serializable;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributoSimple;
import com.palermotenis.model.exceptions.IllegalValueException;

public class Valor implements Serializable {

    private static final long serialVersionUID = -3205633002530019345L;

    private Object unidad;
    private TipoAtributoSimple tipoAtributo;

    public Valor() {
    }

    public Valor(TipoAtributoSimple tipoAtributo) {
        this.tipoAtributo = tipoAtributo;
    }

    public Object getUnidad() {
        return unidad;
    }

    public void setUnidad(Object unidad) throws IllegalValueException {
        this.unidad = unidad;
    }

    public TipoAtributoSimple getTipoAtributo() {
        return tipoAtributo;
    }

    public void setTipoAtributo(TipoAtributoSimple tipoAtributo) {
        this.tipoAtributo = tipoAtributo;
    }

    public String getNombre() {
        if (getTipoAtributo().getUnidad() == null) {
            return getUnidad().toString();
        }
        return getUnidad().toString() + " " + getTipoAtributo().getUnidad().getNombre();
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
