package com.palermotenis.model.dao.suscriptores.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.newsletter.Suscriptor;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.suscriptores.SuscriptorDao;

@Repository("suscriptorDao")
public class SuscriptorDaoHibernateImpl extends AbstractHibernateDao<Suscriptor, Integer> implements SuscriptorDao {

}
