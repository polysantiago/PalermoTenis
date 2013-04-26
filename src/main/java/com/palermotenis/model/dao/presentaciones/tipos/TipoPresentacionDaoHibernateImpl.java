package com.palermotenis.model.dao.presentaciones.tipos;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.dao.AbstractHibernateDao;

@Repository("tipoPresentacionDao")
public class TipoPresentacionDaoHibernateImpl extends AbstractHibernateDao<TipoPresentacion, Integer> implements
    TipoPresentacionDao {

}
