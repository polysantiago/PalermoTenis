package com.palermotenis.model.dao.atributos.tipos.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoClasificatorioDao;

@Repository("tipoAtributoClasificatorioDao")
public class TipoAtributoClasificatorioDaoHibernateImpl extends
    AbstractHibernateDao<TipoAtributoClasificatorio, Integer> implements TipoAtributoClasificatorioDao {

    @Override
    public TipoAtributoClasificatorio getTipoAtributoClasificatorioById(Integer tipoAtributoId) {
        return find(tipoAtributoId);
    }

}
