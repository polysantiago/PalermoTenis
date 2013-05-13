package com.palermotenis.model.dao.geograficos.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.geograficos.Provincia;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.geograficos.ProvinciaDao;

@Repository("provinciaDao")
public class ProvinciaDaoHibernateImpl extends AbstractHibernateDao<Provincia, Integer> implements ProvinciaDao {

}
