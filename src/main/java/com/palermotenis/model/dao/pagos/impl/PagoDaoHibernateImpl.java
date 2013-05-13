package com.palermotenis.model.dao.pagos.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.pagos.PagoDao;

@Repository("pagoDao")
public class PagoDaoHibernateImpl extends AbstractHibernateDao<Pago, Integer> implements PagoDao {

}
