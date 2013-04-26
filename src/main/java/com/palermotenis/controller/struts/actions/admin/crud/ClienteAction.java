package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.clientes.Cliente;
import com.palermotenis.model.beans.newsletter.Suscriptor;
import com.palermotenis.model.dao.Dao;
import com.palermotenis.util.StringUtility;

/**
 * 
 * @author Poly
 */
public class ClienteAction extends ActionSupport {

    private static final long serialVersionUID = -8851428973350849153L;
    private static final String CREATE = "create";

    private Cliente cliente = new Cliente();

    @Autowired
    private Dao<Cliente, Integer> clienteDao;

    @Autowired
    private Dao<Suscriptor, Integer> suscriptorDao;

    public String create() {
        clienteDao.create(cliente);
        List<Suscriptor> suscriptores = suscriptorDao.queryBy("Email",
            new ImmutableMap.Builder<String, Object>().put("email", cliente.getEmail()).build());
        if (suscriptores == null || suscriptores.isEmpty()) {
            suscriptorDao.create(new Suscriptor(cliente.getEmail(), StringUtility.buildRandomString(), true));
        }
        return CREATE;
    }

    @Override
    public void validate() {
        List<Cliente> clientes = clienteDao.queryBy("Email",
            new ImmutableMap.Builder<String, Object>().put("email", cliente.getEmail()).build());
        if (clientes != null && !clientes.isEmpty()) {
            addFieldError("email", "Ya hay otro cliente registrado con esta dirección de correo.");
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
