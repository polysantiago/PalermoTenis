package com.palermotenis.model.dao.monedas;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.dao.AbstractHibernateDao;

@Repository("monedaDao")
public class MonedaDaoHibernateImpl extends AbstractHibernateDao<Moneda, Integer> implements MonedaDao {

}
