package com.palermotenis.model.dao.geograficos.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.geograficos.Pais;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.geograficos.PaisDao;

@Repository("paisDao")
public class PaisDaoHibernateImpl extends AbstractHibernateDao<Pais, Integer> implements PaisDao {

}
