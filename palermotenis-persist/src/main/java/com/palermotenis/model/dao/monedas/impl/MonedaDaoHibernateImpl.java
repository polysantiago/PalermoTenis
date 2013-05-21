package com.palermotenis.model.dao.monedas.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.monedas.MonedaDao;

@Repository("monedaDao")
public class MonedaDaoHibernateImpl extends AbstractHibernateDao<Moneda, Integer> implements MonedaDao {

}
