package com.palermotenis.model.dao.authorities;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.authorities.Rol;
import com.palermotenis.model.dao.AbstractHibernateDao;

@Repository("rolDao")
public class RolDaoHibernateImpl extends AbstractHibernateDao<Rol, Integer> implements RolDao {

}
