package com.palermotenis.controller.struts.actions.clientes;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.clientes.Cliente;

/**
 * 
 * @author Poly
 */
public class BuscarCliente extends ActionSupport {

    private static final long serialVersionUID = 4947994581192892643L;

    private char filter;
    private String searchVal;
    private Collection<Cliente> clientes;

    @Autowired
    private GenericDao<Cliente, Integer> clienteDao;

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

    private void buscarTodos() {
        clientes = clienteDao.findAll();
    }

    private void buscarXEmail() {
        clientes = clienteDao.queryBy("Email",
            new ImmutableMap.Builder<String, Object>().put("email", "%" + getSearchVal() + "%").build());
    }

    private void buscarXNombre() {
        clientes = clienteDao.queryBy("Nombre",
            new ImmutableMap.Builder<String, Object>().put("nombre", "%" + getSearchVal() + "%").build());
    }

    /**
     * @return the filter
     */
    public char getFilter() {
        return filter;
    }

    /**
     * @param filter
     *            the filter to set
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
     * @param searchVal
     *            the searchVal to set
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
}
