package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.clientes.Cliente;
import com.palermotenis.model.service.clientes.ClienteService;
import com.palermotenis.model.service.newsletter.SuscriptorService;

public class ClienteAction extends ActionSupport {

    private static final long serialVersionUID = -8851428973350849153L;
    private static final String CREATE = "create";

    private Cliente cliente = new Cliente();

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private SuscriptorService suscriptorService;

    public String create() {
        clienteService.createCliente(cliente);
        suscriptorService.create(cliente.getEmail(), true);
        return CREATE;
    }

    @Override
    public void validate() {
        List<Cliente> clientes = clienteService.getClientesByEmail(cliente.getEmail());
        if (CollectionUtils.isNotEmpty(clientes)) {
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
