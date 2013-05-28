package com.palermotenis.controller.struts.actions.admin.ventas;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.exceptions.NullPriceException;
import com.palermotenis.model.service.ventas.VentaService;

public class ConfirmarVenta extends ActionSupport {

    private static final long serialVersionUID = 1135420464751868634L;

    private static final Logger logger = Logger.getLogger(ConfirmarVenta.class);
    private static final String ZERO_PRICES = "zeroPrices";

    private int pedidoId;

    private String listadoId;

    private Map<Integer, Double> precios = new HashMap<Integer, Double>();
    private Map<Integer, Integer> cantidades = new HashMap<Integer, Integer>();

    @Autowired
    private VentaService ventaService;

    public String confirmarPedido() {
        try {
            ventaService.confirmOrder(pedidoId, getUsuario());
        } catch (Exception ex) {
            logger.error("Error al eliminar el pedido", ex);
        }
        return SUCCESS;
    }

    public String confirmarNuevaVenta() {
        try {
            ventaService.confirmSale(listadoId, getUsuario());
        } catch (NullPriceException ex) {
            return ZERO_PRICES;
        } catch (Exception ex) {
            logger.error("No existe la entidad", ex);
            return ERROR;
        }
        return SUCCESS;
    }

    private Usuario getUsuario() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    public void setListadoId(String listadoId) {
        this.listadoId = listadoId;
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

}
