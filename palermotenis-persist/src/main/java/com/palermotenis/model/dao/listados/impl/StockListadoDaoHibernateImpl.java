package com.palermotenis.model.dao.listados.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.ventas.StockListado;
import com.palermotenis.model.beans.ventas.StockListadoPK;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.listados.StockListadoDao;

@Repository("stockListadoDao")
public class StockListadoDaoHibernateImpl extends AbstractHibernateDao<StockListado, StockListadoPK> implements
    StockListadoDao {

}
