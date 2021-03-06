package com.palermotenis.model.dao.presentaciones.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.presentaciones.PresentacionDao;

@Repository("presentacionDao")
public class PresentacionDaoHibernateImpl extends AbstractHibernateDao<Presentacion, Integer> implements
    PresentacionDao {

    @Override
    public List<Presentacion> getPresentaciones(Collection<TipoPresentacion> tiposPresentacion) {
        return queryBy("TipoList", "tipoList", tiposPresentacion);
    }

}
