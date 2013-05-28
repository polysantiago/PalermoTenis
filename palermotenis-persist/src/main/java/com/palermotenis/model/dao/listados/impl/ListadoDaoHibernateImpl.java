package com.palermotenis.model.dao.listados.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.ventas.Listado;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.listados.ListadoDao;

@Repository("listadoDao")
public class ListadoDaoHibernateImpl extends AbstractHibernateDao<Listado, String> implements ListadoDao {

}
