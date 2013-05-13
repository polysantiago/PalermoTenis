package com.palermotenis.model.beans.atributos.tipos;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.valores.Valor;
import com.palermotenis.model.beans.valores.ValorPosible;

@Entity
@DiscriminatorValue("T")
public class TipoAtributoTipado extends TipoAtributoSimple {

    private static final long serialVersionUID = 809915291580737824L;

    @Transient
    private transient final char tipo = 'T';

    @OneToMany(mappedBy = "tipoAtributo", cascade =
        { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    private Collection<ValorPosible> valoresPosibles;

    public TipoAtributoTipado() {
        super();
    }

    public TipoAtributoTipado(String nombre, Class<String> clase, TipoProducto tipoProducto) {
        super(nombre, clase, tipoProducto);
    }

    @Override
    public char getTipo() {
        return tipo;
    }

    public Collection<ValorPosible> getValoresPosibles() {
        return valoresPosibles;
    }

    public void setValoresPosibles(Collection<ValorPosible> valoresPosibles) {
        this.valoresPosibles = valoresPosibles;
    }

    public void addValorPosible(ValorPosible valorPosible) {
        if (hasValorPosible(valorPosible)) {
            // TODO handle exception
        }
        valoresPosibles.add(valorPosible);
    }

    public void removeValorPosible(ValorPosible valorPosible) {
        if (!hasValorPosible(valorPosible)) {
            // TODO handle exception
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
