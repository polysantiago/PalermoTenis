package com.palermotenis.model.beans.atributos.tipos;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.palermotenis.model.beans.atributos.AtributoSimple;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;

@Entity
@DiscriminatorValue("S")
@AttributeOverride(name = "Tipo", column = @Column(insertable = false, updatable = false))
public class TipoAtributoSimple extends TipoAtributo implements Serializable {

    private static final long serialVersionUID = 4600267127044258775L;

    @OneToMany(mappedBy = "tipoAtributo", fetch = FetchType.LAZY)
    private Collection<AtributoSimple> atributos;

    @Transient
    private transient final char tipo = 'S';

    public TipoAtributoSimple() {
    }

    public TipoAtributoSimple(String nombre, Class<?> clase, TipoProducto tipoProducto) {
        setNombre(nombre);
        setClase(clase);
        setTipoProducto(tipoProducto);
    }

    public char getTipo() {
        return tipo;
    }

    public Collection<AtributoSimple> getAtributos() {
        return atributos;
    }

    public void setAtributos(Collection<AtributoSimple> atributos) {
        this.atributos = atributos;
    }

}
