package com.palermotenis.model.dao.valores.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.valores.ValorClasificatorioDao;

@Repository("valorClasificatorioDao")
public class ValorClasificatorioDaoHibernateImpl extends AbstractHibernateDao<ValorClasificatorio, Integer> implements
    ValorClasificatorioDao {

    @Override
    public List<ValorClasificatorio> getValoresClasificatoriosByTiposAtributo(
            Collection<TipoAtributoClasificatorio> tiposAtributosClasificatorios) {
        return queryBy("TipoAtributoList", "tipoAtributoList", tiposAtributosClasificatorios);
    }

}
