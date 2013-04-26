package com.palermotenis.model.beans.valores;

import java.util.Collection;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.palermotenis.model.beans.Stock;

@Entity
@DiscriminatorValue("C")
@NamedQueries(
    { @NamedQuery(name = "ValorClasificatorio.findByTipoAtributoList",
            query = "SELECT vc FROM ValorClasificatorio vc WHERE tipoAtributo IN (:tipoAtributoList)") })
public class ValorClasificatorio extends ValorPosible {

    private static final long serialVersionUID = 3051161264168186989L;

    @OneToMany(mappedBy = "valorClasificatorio", fetch = FetchType.LAZY)
    private Collection<Stock> stocks;

    /**
     * @return the stocks
     */
    public Collection<Stock> getStocks() {
        return stocks;
    }

    /**
     * @param stocks
     *            the stocks to set
     */
    public void setStocks(Collection<Stock> stocks) {
        this.stocks = stocks;
    }

}
