/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.clientes;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.clientes.Cliente;
import java.util.Collection;

/**
 *
 * @author Poly
 */
public class BuscarCliente extends ActionSupport {

    private char filter;
    private String searchVal;
    private Collection<Cliente> clientes;

    private GenericDao<Cliente, Integer> clienteService;

    @Override
    public String execute() {
        if (getSearchVal() == null || getSearchVal().isEmpty()) {
            buscarTodos();
        } else {
            switch (getFilter()) {
                case 'E':
                    buscarXEmail();
                    break;
                case 'N':
                    buscarXNombre();
                    break;
                default:
                    return ERROR;
            }
        }
        return SUCCESS;
    }

    private void buscarTodos(){
        clientes = clienteService.findAll();
    }

    private void buscarXEmail(){
        clientes = clienteService.queryBy("Email",
                new ImmutableMap.Builder<String, Object>().put("email", "%" + getSearchVal() + "%").build());
    }

    private void buscarXNombre(){
        clientes = clienteService.queryBy("Nombre",
                new ImmutableMap.Builder<String, Object>().put("nombre", "%" + getSearchVal() + "%").build());
    }

    /**
     * @return the filter
     */
    public char getFilter() {
        return filter;
    }

    /**
     * @param filter the filter to set
     */
    public void setFilter(char filter) {
        this.filter = filter;
    }

    /**
     * @return the searchVal
     */
    public String getSearchVal() {
        return searchVal;
    }

    /**
     * @param searchVal the searchVal to set
     */
    public void setSearchVal(String searchVal) {
        this.searchVal = searchVal;
    }

    /**
     * @return the clientes
     */
    public Collection<Cliente> getClientes() {
        return clientes;
    }

    /**
     * @param clienteService the clientesService to set
     */
    public void setClienteService(GenericDao<Cliente, Integer> clienteService) {
        this.clienteService = clienteService;
    }
}
