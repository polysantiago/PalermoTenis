/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.crud;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.clientes.Cliente;
import com.palermotenis.model.beans.newsletter.Suscriptor;
import com.palermotenis.util.StringUtility;
import java.util.List;

/**
 *
 * @author Poly
 */
public class ClienteAction extends ActionSupport {

    private static final String CREATE = "create";
    private Cliente cliente = new Cliente();
    private GenericDao<Cliente, Integer> clienteService;
    private GenericDao<Suscriptor, Integer> suscriptorService;

    public String create() {
        clienteService.create(cliente);
        List<Suscriptor> suscriptores = suscriptorService.queryBy("Email",
                new ImmutableMap.Builder<String, Object>().put("email", cliente.getEmail()).build());
        if (suscriptores == null || suscriptores.isEmpty()) {
            suscriptorService.create(new Suscriptor(cliente.getEmail(), StringUtility.buildRandomString(), true));
        }
        return CREATE;
    }

    @Override
    public void validate() {
        List<Cliente> clientes = clienteService.queryBy("Email",
                new ImmutableMap.Builder<String, Object>().put("email", cliente.getEmail()).build());
        if (clientes != null && !clientes.isEmpty()) {
            addFieldError("email", "Ya hay otro cliente registrado con esta dirección de correo.");
        }
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setClienteService(GenericDao<Cliente, Integer> clienteService) {
        this.clienteService = clienteService;
    }

    public void setSuscriptorService(GenericDao<Suscriptor, Integer> suscriptorService) {
        this.suscriptorService = suscriptorService;
    }
}
