/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.controller.struts.actions.admin.ventas;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.ventas.Listado;

/**
 *
 * @author Poly
 */
public class ListarListado extends ActionSupport {

    private GenericDao<Listado, String> listadoService;
    private Listado listado;
    private String listadoId;

    @Override
    public String execute(){
        listado = listadoService.find(listadoId);
        return SUCCESS;
    }

    public void setListadoId(String listadoId) {
        this.listadoId = listadoId;
    }

    public void setListadoService(GenericDao<Listado, String> listadoService) {
        this.listadoService = listadoService;
    }

    public Listado getListado() {
        return listado;
    }

}
