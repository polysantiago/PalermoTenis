package com.palermotenis.model.dao.atributos.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.atributos.AtributoTipado;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.atributos.AtributoTipadoDao;

@Repository("atributoTipadoDao")
public class AtributoTipadoDaoHibernateImpl extends AbstractHibernateDao<AtributoTipado, Integer> implements
    AtributoTipadoDao {

}
