package com.palermotenis.model.dao.clientes;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.clientes.Cliente;
import com.palermotenis.model.dao.AbstractHibernateDao;

@Repository("clienteDao")
public class ClienteDaoHibernateImpl extends AbstractHibernateDao<Cliente, Integer> implements ClienteDao {

}
