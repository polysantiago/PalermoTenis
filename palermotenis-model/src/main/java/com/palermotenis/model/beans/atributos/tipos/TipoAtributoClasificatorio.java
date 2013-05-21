package com.palermotenis.model.beans.atributos.tipos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.palermotenis.model.beans.productos.tipos.TipoProducto;

@Entity
@DiscriminatorValue("C")
public class TipoAtributoClasificatorio extends TipoAtributoTipado {

    private static final long serialVersionUID = 2414253261673908366L;

    public TipoAtributoClasificatorio() {
        super();
    }

    public TipoAtributoClasificatorio(String nombre, Class<String> clase, TipoProducto tipoProducto) {
        super(nombre, clase, tipoProducto);
    }

    @Transient
    private transient final char tipo = 'C';

    @Override
    public char getTipo() {
        return tipo;
    }
}
