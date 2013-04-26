package com.palermotenis.model.dao.atributos.tipos.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributoTipado;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoTipadoDao;

@Repository("tipoAtributoTipadoDao")
public class TipoAtributoTipadoDaoHibernateImpl extends AbstractHibernateDao<TipoAtributoTipado, Integer> implements
    TipoAtributoTipadoDao {

    @Override
    public TipoAtributoTipado getTipoAtributoTipadoById(Integer tipoAtributoTipadoId) {
        return find(tipoAtributoTipadoId);
    }

}
