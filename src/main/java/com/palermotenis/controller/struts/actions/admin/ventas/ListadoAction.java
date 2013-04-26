package com.palermotenis.controller.struts.actions.admin.ventas;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.ventas.Listado;
import com.palermotenis.model.beans.ventas.StockListado;
import com.palermotenis.model.beans.ventas.StockListadoPK;
import com.palermotenis.model.dao.Dao;
import com.palermotenis.util.SecurityUtil;

/**
 * 
 * @author Poly
 */
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
    private Dao<Listado, String> listadoDao;

    @Autowired
    private Dao<StockListado, StockListadoPK> stockListadoDao;

    @Autowired
    private Dao<Stock, Integer> stockDao;

    @Autowired
    private SecurityUtil securityUtil;

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
        listado = listadoDao.find(listadoId);

        for (Integer i : cantidades.keySet()) {
            Stock stock = stockDao.find(i);
            StockListado sl = stockListadoDao.find(new StockListadoPK(listado, stock));
            sl.setCantidad(cantidades.get(i));
            sl.setSubtotal(sl.getPrecioUnitario() * cantidades.get(i));
        }
        updateTotal();
        try {
            listadoDao.edit(listado);
        } catch (HibernateException ex) {
            logger.error("No existe la entidad", ex);
            return ERROR;
        } catch (Exception ex) {
            logger.error("Error al editar la entidad", ex);
            return ERROR;
        }
        return SUCCESS;
    }

    private String actualizarPrecios() {
        listado = listadoDao.find(listadoId);

        if (securityUtil.isSupervisor(SecurityUtil.getLoggedInUser())) {
            listado.setAutorizado(true);
        } else {
            listado.setAutorizado(false);
        }

        for (Integer i : precios.keySet()) {
            Stock stock = stockDao.find(i);
            StockListadoPK pk = new StockListadoPK(listado, stock);
            StockListado sl = stockListadoDao.find(pk);
            sl.setPrecioUnitario(precios.get(i));
            sl.setSubtotal(sl.getCantidad() * precios.get(i));
        }

        updateTotal();

        try {
            listadoDao.edit(listado);
        } catch (HibernateException ex) {
            logger.error("No existe la entidad", ex);
            return ERROR;
        } catch (Exception ex) {
            logger.error("Error al editar la entidad", ex);
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

}
