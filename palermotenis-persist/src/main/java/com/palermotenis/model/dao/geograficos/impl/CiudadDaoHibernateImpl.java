package com.palermotenis.model.dao.geograficos.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.geograficos.Ciudad;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.geograficos.CiudadDao;

@Repository("ciudadDao")
public class CiudadDaoHibernateImpl extends AbstractHibernateDao<Ciudad, Integer> implements CiudadDao {

}
