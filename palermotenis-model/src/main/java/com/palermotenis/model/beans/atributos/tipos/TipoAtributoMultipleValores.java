package com.palermotenis.model.beans.atributos.tipos;

import javax.annotation.Nullable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.palermotenis.model.beans.atributos.Atributo;
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
        return Joiner.on(", ").join(Lists.transform(getAtributos(), new Function<Atributo, String>() {
            @Override
            @Nullable
            public String apply(@Nullable Atributo atributo) {
                return atributo.getValor().getNombre();
            }

        }));
    }
}
