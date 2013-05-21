package com.palermotenis.model.dao.pedidos.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.pedidos.Pedido;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.pedidos.PedidoDao;

@Repository("pedidoDao")
public class PedidoDaoHibernateImpl extends AbstractHibernateDao<Pedido, Integer> implements PedidoDao {

}
