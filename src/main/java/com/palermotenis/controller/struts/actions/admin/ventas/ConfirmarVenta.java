/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.ventas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.pedidos.Pedido;
import com.palermotenis.model.beans.pedidos.PedidoProducto;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.beans.ventas.Listado;
import com.palermotenis.model.beans.ventas.ProductoVenta;
import com.palermotenis.model.beans.ventas.StockListado;
import com.palermotenis.model.beans.ventas.Venta;
import com.palermotenis.model.dao.Dao;
import com.palermotenis.util.StringUtility;

/**
 * 
 * @author Poly
 */
public class ConfirmarVenta extends ActionSupport {

    private static final long serialVersionUID = 1135420464751868634L;
    private static final Logger logger = Logger.getLogger(ConfirmarVenta.class);
    private static final String ZERO_PRICES = "zeroPrices";

    private int pedidoId;

    private String listadoId;

    private Map<Integer, Double> precios = new HashMap<Integer, Double>();
    private Map<Integer, Integer> cantidades = new HashMap<Integer, Integer>();

    @Autowired
    private Dao<Pedido, Integer> pedidoDao;

    @Autowired
    private Dao<Listado, String> listadoDao;

    @Autowired
    private Dao<Venta, Integer> ventaDao;

    @Autowired
    private Dao<Stock, Integer> stockDao;

    public String confirmarPedido() {

        Pedido pedido = pedidoDao.find(pedidoId);
        Venta venta = new Venta(pedido.getCliente(), pedido.getPago());

        List<ProductoVenta> productosVenta = new ArrayList<ProductoVenta>();
        for (PedidoProducto pp : pedido.getPedidosProductos()) {
            ProductoVenta pv = new ProductoVenta();
            Stock s = pp.getId().getStock();
            pv.setProducto(StringUtility.buildNameFromStock(pp.getId().getStock()));
            pv.setPrecioUnitario(pp.getSubtotal() / pp.getCantidad());
            pv.setSubtotal(pp.getSubtotal());
            pv.setVenta(venta);
            pv.setStock(s);
            productosVenta.add(pv);

            int current = s.getStock();
            if (current - pp.getCantidad() >= 0) {
                s.setStock(current - pp.getCantidad());
            }
            try {
                stockDao.edit(s);
            } catch (HibernateException ex) {
                logger.error("Error al actualizar el stock", ex);
            } catch (Exception ex) {
                logger.error("Error al editar la entidad", ex);
            }
        }
        venta.setProductos(productosVenta);
        venta.setTotal(pedido.getTotal());
        venta.setCuotas(pedido.getCuotas());
        venta.setUsuario(getUsuario());
        ventaDao.create(venta);

        try {
            pedidoDao.destroy(pedido);
        } catch (HibernateException ex) {
            logger.error("Error al eliminar el pedido", ex);
        }

        return SUCCESS;
    }

    public String confirmarNuevaVenta() {

        Listado listado = listadoDao.find(listadoId);

        Venta venta = new Venta(listado.getCliente(), listado.getPago());
        List<ProductoVenta> productosVenta = new ArrayList<ProductoVenta>();
        for (StockListado sl : listado.getStocksListado()) {
            Stock stock = sl.getStockListadoPK().getStock();

            double unitario = sl.getPrecioUnitario();
            double subtotal = sl.getSubtotal();

            if (unitario == 0.0) {
                venta = null;
                productosVenta = null;
                return ZERO_PRICES;
            }

            ProductoVenta pv = new ProductoVenta(StringUtility.buildNameFromStock(stock), unitario, subtotal);
            pv.setVenta(venta);
            pv.setStock(stock);
            productosVenta.add(pv);

            int current = stock.getStock();
            if (current - stock.getStock() >= 0) {
                stock.setStock(current - sl.getCantidad());
            }
            try {
                stockDao.edit(stock);
            } catch (HibernateException ex) {
                logger.error("Error al actualizar la entidad", ex);
                return ERROR;
            } catch (Exception ex) {
                logger.error("Error al actualizar la entidad", ex);
                return ERROR;
            }
        }
        venta.setCuotas(listado.getCuotas());
        venta.setTotal(listado.getTotal());
        venta.setProductos(productosVenta);
        venta.setUsuario(getUsuario());
        ventaDao.create(venta);

        try {
            listadoDao.destroy(listado);
        } catch (HibernateException ex) {
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
