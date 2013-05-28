package com.palermotenis.model.dao.ventas.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.ventas.Venta;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.ventas.VentaDao;

@Repository("ventaDao")
public class VentaDaoHibernateImpl extends AbstractHibernateDao<Venta, Integer> implements VentaDao {

}
