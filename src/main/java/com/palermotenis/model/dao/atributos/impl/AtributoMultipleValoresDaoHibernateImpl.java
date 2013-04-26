package com.palermotenis.model.dao.atributos.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.atributos.AtributoMultipleValores;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.atributos.AtributoMultipleValoresDao;

@Repository("atributoMultipleValoresDao")
public class AtributoMultipleValoresDaoHibernateImpl extends AbstractHibernateDao<AtributoMultipleValores, Integer>
    implements AtributoMultipleValoresDao {

}
