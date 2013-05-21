package com.palermotenis.model.beans.atributos;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributoSimple;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.valores.Valor;

@Entity
@DiscriminatorValue("S")
@NamedQueries(
    { @NamedQuery(name = "AtributoSimple.findByProducto,Tipo",
            query = "SELECT a FROM AtributoSimple a WHERE a.tipoAtributo = :tipoAtributo AND a.producto = :producto") })
public class AtributoSimple extends Atributo implements Serializable {

    private static final long serialVersionUID = 2543183923873746514L;

    public AtributoSimple() {
    }

    public AtributoSimple(TipoAtributoSimple tipoAtributoSimple) {
        super(tipoAtributoSimple);
    }

    public AtributoSimple(TipoAtributoSimple tipoAtributoSimple, Producto producto) {
        super(tipoAtributoSimple, producto);
    }

    public AtributoSimple(TipoAtributoSimple tipoAtributoSimple, Valor valor) {
        super(tipoAtributoSimple, valor);
    }

    public AtributoSimple(TipoAtributoSimple tipoAtributoSimple, Producto producto, Valor valor) {
        super(tipoAtributoSimple, producto, valor);
    }

}
