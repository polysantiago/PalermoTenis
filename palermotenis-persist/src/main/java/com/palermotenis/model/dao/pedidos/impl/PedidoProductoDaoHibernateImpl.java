package com.palermotenis.model.dao.pedidos.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.pedidos.PedidoProducto;
import com.palermotenis.model.beans.pedidos.PedidoProductoPK;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.pedidos.PedidoProductoDao;

@Repository("pedidoProductoDao")
public class PedidoProductoDaoHibernateImpl extends AbstractHibernateDao<PedidoProducto, PedidoProductoPK> implements
    PedidoProductoDao {

}
