package com.palermotenis.model.dao.authorities.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.authorities.Rol;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.authorities.RolDao;

@Repository("rolDao")
public class RolDaoHibernateImpl extends AbstractHibernateDao<Rol, Integer> implements RolDao {

}
