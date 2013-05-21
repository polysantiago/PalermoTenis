package com.palermotenis.support.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.clientes.Cliente;
import com.palermotenis.model.beans.clientes.Direccion;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.dao.clientes.ClienteDao;
import com.palermotenis.model.dao.exceptions.PreexistingEntityException;
import com.palermotenis.model.service.clientes.ClienteService;
import com.palermotenis.support.TestClienteService;

@Service("testClienteService")
public class TestClienteServiceImpl implements TestClienteService {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteDao clienteDao;

    @Override
    public Cliente refresh(Cliente cliente) {
        if (cliente != null) {
            return clienteDao.find(cliente.getId());
        }
        return cliente;
    }

    @Override
    @Transactional
    public Cliente create() {
        return create(false);
    }

    @Override
    @Transactional
    public Cliente create(boolean isSuscriptor) {
        String email = "testName." + RandomStringUtils.randomAlphabetic(5) + "@test.com";
        String nombre = "testName";
        String password = "testPassword";
        String telefono = "123456788";
        Direccion direccion = new Direccion("testAddress 123", "testCiudad");

        Usuario usuario = null;
        try {
            usuario = clienteService.registerCliente(email, password, nombre, direccion, telefono, isSuscriptor);
        } catch (PreexistingEntityException e) {
            // do nothing
        }
        return usuario.getCliente();

    }

    @Override
    public Cliente getAny() {
        // TODO Auto-generated method stub
        return null;
    }

}
