package com.palermotenis.model.beans.atributos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@DiscriminatorValue("M")
@NamedQueries(
    { @NamedQuery(name = "AtributoMultipleValores.findByProducto",
            query = "SELECT a FROM AtributoMultipleValores a WHERE a.producto = :producto") })
public class AtributoMultipleValores extends AtributoTipado {

    private static final long serialVersionUID = 8902730456698011616L;

}
