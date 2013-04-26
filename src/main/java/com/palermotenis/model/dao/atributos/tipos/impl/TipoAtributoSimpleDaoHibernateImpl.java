package com.palermotenis.model.dao.atributos.tipos.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoSimpleDao;

@Repository("tipoAtributoSimpleDao")
public class TipoAtributoSimpleDaoHibernateImpl extends AbstractHibernateDao<TipoAtributo, Integer> implements
    TipoAtributoSimpleDao {

    @Override
    public TipoAtributo getTipoAtributoSimpleById(Integer tipoAtributoId) {
        return find(tipoAtributoId);
    }

}
