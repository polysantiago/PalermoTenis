package com.palermotenis.model.service.clientes.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.clientes.Cliente;
import com.palermotenis.model.dao.clientes.ClienteDao;
import com.palermotenis.model.service.clientes.ClienteService;

@Service("clienteService")
@Transactional
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteDao clienteDao;

    @Override
    public void createCliente(Cliente cliente) {
        clienteDao.create(cliente);
    }

    @Override
    public List<Cliente> getClientesByEmail(String email) {
        return clienteDao.queryBy("Email", "email", email);
    }

}
