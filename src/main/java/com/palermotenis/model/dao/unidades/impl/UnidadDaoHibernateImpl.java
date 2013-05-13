package com.palermotenis.model.dao.unidades.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.Unidad;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.unidades.UnidadDao;

@Repository("unidadDao")
public class UnidadDaoHibernateImpl extends AbstractHibernateDao<Unidad, Integer> implements UnidadDao {

}
