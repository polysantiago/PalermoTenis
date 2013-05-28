package com.palermotenis.controller.struts.actions.admin.ventas;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.ventas.Listado;
import com.palermotenis.model.service.ventas.VentaService;

public class ListarListado extends ActionSupport {

    private static final long serialVersionUID = 1144958588010468961L;

    private Listado listado;

    private String listadoId;

    @Autowired
    private VentaService ventaService;

    public void setListadoId(String listadoId) {
        this.listadoId = listadoId;
    }

    public Listado getListado() {
        if (listado == null) {
            listado = ventaService.getListadoById(listadoId);
        }
        return listado;
    }

}
