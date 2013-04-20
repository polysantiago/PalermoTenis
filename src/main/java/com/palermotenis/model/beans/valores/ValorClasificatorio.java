/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.model.beans.valores;

import com.palermotenis.model.beans.Stock;
import java.util.Collection;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Poly
 */
@Entity
@DiscriminatorValue("C")
@NamedQueries({
    @NamedQuery(name = "ValorClasificatorio.findByTipoAtributoList",
    query = "SELECT vc FROM ValorClasificatorio vc WHERE tipoAtributo IN (:tipoAtributoList)")
})
public class ValorClasificatorio extends ValorPosible {

    @OneToMany(mappedBy = "valorClasificatorio", fetch = FetchType.LAZY)
    private Collection<Stock> stocks;

    /**
     * @return the stocks
     */
    public Collection<Stock> getStocks() {
        return stocks;
    }

    /**
     * @param stocks the stocks to set
     */
    public void setStocks(Collection<Stock> stocks) {
        this.stocks = stocks;
    }

}
