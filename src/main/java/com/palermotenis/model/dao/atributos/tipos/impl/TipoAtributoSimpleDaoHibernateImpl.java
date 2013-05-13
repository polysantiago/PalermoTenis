package com.palermotenis.model.dao.atributos.tipos.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributoSimple;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoSimpleDao;

@Repository("tipoAtributoSimpleDao")
public class TipoAtributoSimpleDaoHibernateImpl extends AbstractHibernateDao<TipoAtributoSimple, Integer> implements
    TipoAtributoSimpleDao {

    @Override
    public TipoAtributoSimple getTipoAtributoSimpleById(Integer tipoAtributoId) {
        return find(tipoAtributoId);
    }

}
