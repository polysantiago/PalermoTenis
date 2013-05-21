package com.palermotenis.model.dao.clientes.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.clientes.Cliente;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.clientes.ClienteDao;

@Repository("clienteDao")
public class ClienteDaoHibernateImpl extends AbstractHibernateDao<Cliente, Integer> implements ClienteDao {

}
