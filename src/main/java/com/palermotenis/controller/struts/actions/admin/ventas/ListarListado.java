package com.palermotenis.controller.struts.actions.admin.ventas;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.ventas.Listado;

/**
 * 
 * @author Poly
 */
public class ListarListado extends ActionSupport {

    private static final long serialVersionUID = 1144958588010468961L;

    private Listado listado;

    private String listadoId;

    @Autowired
    private GenericDao<Listado, String> listadoDao;

    @Override
    public String execute() {
        listado = listadoDao.find(listadoId);
        return SUCCESS;
    }

    public void setListadoId(String listadoId) {
        this.listadoId = listadoId;
    }

    public Listado getListado() {
        return listado;
    }

}
