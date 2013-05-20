package com.palermotenis.model.beans.atributos.tipos;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.palermotenis.model.beans.productos.tipos.TipoProducto;

@Entity
@DiscriminatorValue("S")
@AttributeOverride(name = "Tipo", column = @Column(insertable = false, updatable = false))
public class TipoAtributoSimple extends TipoAtributo implements Serializable {

    private static final long serialVersionUID = 4600267127044258775L;

    @Transient
    private transient final char tipo = 'S';

    public TipoAtributoSimple() {
        super();
    }

    public TipoAtributoSimple(String nombre, Class<?> clase, TipoProducto tipoProducto) {
        super(nombre, clase, tipoProducto);
    }

    public char getTipo() {
        return tipo;
    }

}
