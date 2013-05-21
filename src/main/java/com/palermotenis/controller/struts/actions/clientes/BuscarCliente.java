package com.palermotenis.controller.struts.actions.clientes;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.clientes.Cliente;
import com.palermotenis.model.service.clientes.ClienteService;

public class BuscarCliente extends ActionSupport {

    private static final long serialVersionUID = 4947994581192892643L;

    private char filter;
    private String searchVal;
    private List<Cliente> clientes;

    @Autowired
    private ClienteService clienteService;

    @Override
    public String execute() {
        if (StringUtils.isEmpty(searchVal)) {
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

    private void buscarTodos() {
        clientes = clienteService.getAllClientes();
    }

    private void buscarXEmail() {
        clientes = clienteService.getClientesByEmail("%" + searchVal + "%");
    }

    private void buscarXNombre() {
        clientes = clienteService.getClientesByNombre("%" + searchVal + "%");
    }

    public char getFilter() {
        return filter;
    }

    public void setFilter(char filter) {
        this.filter = filter;
    }

    public String getSearchVal() {
        return searchVal;
    }

    public void setSearchVal(String searchVal) {
        this.searchVal = searchVal;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}
