package com.palermotenis.model.dao.compras.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.compras.Compra;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.compras.CompraDao;

@Repository("compraDao")
public class CompraDaoHibernateImpl extends AbstractHibernateDao<Compra, Integer> implements CompraDao {

}
