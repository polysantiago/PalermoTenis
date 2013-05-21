package com.palermotenis.model.dao.atributos.tipos.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributoMultipleValores;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoMultipleValoresDao;

@Repository("tipoAtributoMultipleValoresDao")
public class TipoAtributoMultipleValoresDaoHibernateImpl extends
    AbstractHibernateDao<TipoAtributoMultipleValores, Integer> implements TipoAtributoMultipleValoresDao {

    @Override
    public TipoAtributoMultipleValores getTipoAtributoMultipleValoresById(Integer tipoAtributoMultipleValoresId) {
        return find(tipoAtributoMultipleValoresId);
    }

}
