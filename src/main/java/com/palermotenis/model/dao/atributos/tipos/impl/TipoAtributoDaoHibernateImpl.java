package com.palermotenis.model.dao.atributos.tipos.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoDao;

@Repository("tipoAtributoDao")
public class TipoAtributoDaoHibernateImpl extends AbstractHibernateDao<TipoAtributo, Integer> implements
    TipoAtributoDao {

}
