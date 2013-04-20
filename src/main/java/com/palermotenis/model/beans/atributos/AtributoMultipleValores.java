/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans.atributos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Poly
 */
@Entity
@DiscriminatorValue("M")
@NamedQueries({
    @NamedQuery(name = "AtributoMultipleValores.findByProducto",
    query = "SELECT a FROM AtributoMultipleValores a WHERE a.producto = :producto")
})
public class AtributoMultipleValores extends AtributoTipado {
}
