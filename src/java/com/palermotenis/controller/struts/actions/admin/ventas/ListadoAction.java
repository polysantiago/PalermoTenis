/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.ventas;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.beans.ventas.Listado;
import com.palermotenis.model.beans.ventas.StockListado;
import com.palermotenis.model.beans.ventas.StockListadoPK;
import com.palermotenis.util.SecurityUtil;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Poly
 */
public class ListadoAction extends ActionSupport {

    private static final String NEED_AUTHORIZATION = "needAuth";
    private Map<Integer, Double> precios = new HashMap<Integer, Double>();
    private Map<Integer, Integer> cantidades = new HashMap<Integer, Integer>();
    private Listado listado;
    private GenericDao<Listado, String> listadoService;
    private GenericDao<StockListado, StockListadoPK> stockListadoService;
    private GenericDao<Stock, Integer> stockService;
    private String listadoId;
    private String accion;    
    private SecurityUtil securityUtil;

    private Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private static final Logger logger = Logger.getLogger(ListadoAction.class);

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
        listado = listadoService.find(listadoId);

        for (Integer i : cantidades.keySet()) {
            Stock stock = stockService.find(i);
            StockListado sl = stockListadoService.find(new StockListadoPK(listado, stock));
            sl.setCantidad(cantidades.get(i));
            sl.setSubtotal(sl.getPrecioUnitario() * cantidades.get(i));
        }
        updateTotal();
        try {
            listadoService.edit(listado);
        } catch (HibernateException ex) {
            logger.error("No existe la entidad",ex);
            return ERROR;
        } catch (Exception ex) {
            logger.error("Error al editar la entidad",ex);
            return ERROR;
        }
        return SUCCESS;
    }

    private String actualizarPrecios() {
        listado = listadoService.find(listadoId);

        if (securityUtil.isSupervisor(usuario)) {
            listado.setAutorizado(true);
        } else {
            listado.setAutorizado(false);
        }

        for (Integer i : precios.keySet()) {
            Stock stock = stockService.find(i);
            StockListadoPK pk = new StockListadoPK(listado, stock);
            StockListado sl = stockListadoService.find(pk);
            sl.setPrecioUnitario(precios.get(i));
            sl.setSubtotal(sl.getCantidad() * precios.get(i));
        }

        updateTotal();

        try {
            listadoService.edit(listado);
        } catch (HibernateException ex) {
            logger.error("No existe la entidad",ex);
            return ERROR;
        } catch (Exception ex) {
            logger.error("Error al editar la entidad",ex);
            return ERROR;
        }

        if (!listado.isAutorizado()) {
            return NEED_AUTHORIZATION;
        }

        return SUCCESS;
    }

    private void updateTotal() {
        double total = 0.00;
        for (StockListado sl : listado.getStocksListado()) {
            total += sl.getSubtotal();
        }
        listado.setTotal(total);
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

    public void setListadoService(GenericDao<Listado, String> listadoService) {
        this.listadoService = listadoService;
    }

    public void setStockListadoService(GenericDao<StockListado, StockListadoPK> stockListadoService) {
        this.stockListadoService = stockListadoService;
    }

    public void setStockService(GenericDao<Stock, Integer> stockService) {
        this.stockService = stockService;
    }

    public void setSecurityUtil(SecurityUtil securityUtil) {
        this.securityUtil = securityUtil;
    }
}
