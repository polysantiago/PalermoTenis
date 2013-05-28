package com.palermotenis.controller.struts.actions.admin.ventas;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.ventas.Listado;
import com.palermotenis.model.service.security.SecurityService;
import com.palermotenis.model.service.ventas.VentaService;

public class ListadoAction extends ActionSupport {

    private static final long serialVersionUID = 7498016206955120742L;

    private static final Logger logger = Logger.getLogger(ListadoAction.class);
    private static final String NEED_AUTHORIZATION = "needAuth";

    private Map<Integer, Double> precios = new HashMap<Integer, Double>();
    private Map<Integer, Integer> cantidades = new HashMap<Integer, Integer>();

    private Listado listado;

    private String listadoId;
    private String accion;

    @Autowired
    private VentaService ventaService;

    @Autowired
    private SecurityService securityService;

    @Override
    public String execute() {
        if ("Actualizar Cantidades".equals(accion)) {
            return actualizarCantidades();
        } else if ("Actualizar Precios".equals(accion)) {
            return actualizarPrecios();
        } else {
            return ERROR;
        }
    }

    private String actualizarCantidades() {
        try {
            listado = ventaService.updateListingQuantities(listadoId, cantidades);
        } catch (Exception ex) {
            logger.error("Error al editar la entidad", ex);
            return ERROR;
        }
        return SUCCESS;
    }

    private String actualizarPrecios() {
        try {
            boolean authorized = securityService.isLoggedInUserSupervisor();
            listado = ventaService.updateListingPrices(listadoId, precios, authorized);
        } catch (Exception ex) {
            logger.error("Error al editar la entidad", ex);
            return ERROR;
        }

        if (!listado.isAutorizado()) {
            return NEED_AUTHORIZATION;
        }

        return SUCCESS;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Listado getListado() {
        return listado;
    }

    public String getListadoId() {
        return listadoId;
    }

    public Map<Integer, Integer> getCantidades() {
        return cantidades;
    }

    public void setCantidades(Map<Integer, Integer> cantidades) {
        this.cantidades = cantidades;
    }

    public Map<Integer, Double> getPrecios() {
        return precios;
    }

    public void setPrecios(Map<Integer, Double> precios) {
        this.precios = precios;
    }

    public void setListadoId(String listadoId) {
        this.listadoId = listadoId;
    }

}
