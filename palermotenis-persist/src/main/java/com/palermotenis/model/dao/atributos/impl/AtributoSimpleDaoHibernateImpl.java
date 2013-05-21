package com.palermotenis.model.dao.atributos.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.atributos.AtributoSimple;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.atributos.AtributoSimpleDao;

@Repository("atributoSimpleDao")
public class AtributoSimpleDaoHibernateImpl extends AbstractHibernateDao<AtributoSimple, Integer> implements
    AtributoSimpleDao {

}
