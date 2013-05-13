package com.palermotenis.model.beans.atributos.tipos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.palermotenis.model.beans.atributos.AtributoSimple;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;

@Entity
@DiscriminatorValue("M")
public class TipoAtributoMultipleValores extends TipoAtributoTipado {

    private static final long serialVersionUID = -9137479882547964535L;

    public TipoAtributoMultipleValores() {
        super();
    }

    public TipoAtributoMultipleValores(String nombre, Class<String> clase, TipoProducto tipoProducto) {
        super(nombre, clase, tipoProducto);
    }

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
