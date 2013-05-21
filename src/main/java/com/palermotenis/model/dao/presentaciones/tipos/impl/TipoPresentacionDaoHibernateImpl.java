package com.palermotenis.model.dao.presentaciones.tipos.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.presentaciones.tipos.TipoPresentacionDao;

@Repository("tipoPresentacionDao")
public class TipoPresentacionDaoHibernateImpl extends AbstractHibernateDao<TipoPresentacion, Integer> implements
    TipoPresentacionDao {

}
