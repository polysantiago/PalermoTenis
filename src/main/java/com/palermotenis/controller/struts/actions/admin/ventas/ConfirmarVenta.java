/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.ventas;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.pedidos.Pedido;
import com.palermotenis.model.beans.pedidos.PedidoProducto;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.beans.ventas.Listado;
import com.palermotenis.model.beans.ventas.ProductoVenta;
import com.palermotenis.model.beans.ventas.StockListado;
import com.palermotenis.model.beans.ventas.Venta;
import com.palermotenis.util.StringUtility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Poly
 */
public class ConfirmarVenta extends ActionSupport {

    private static final String ZERO_PRICES = "zeroPrices";
    private int pedidoId;
    private String listadoId;
    private GenericDao<Pedido, Integer> pedidoService;
    private GenericDao<Listado, String> listadoService;
    private GenericDao<Venta, Integer> ventaService;
    private GenericDao<Stock, Integer> stockService;

    private Map<Integer, Double> precios = new HashMap<Integer, Double>();
    private Map<Integer, Integer> cantidades = new HashMap<Integer, Integer>();
    
    private Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private static final Logger logger = Logger.getLogger(ConfirmarVenta.class);

    public String confirmarPedido() {

        Pedido pedido = pedidoService.find(pedidoId);
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
                stockService.edit(s);
            } catch (HibernateException ex) {
                logger.error("Error al actualizar el stock", ex);
            } catch (Exception ex) {
                logger.error("Error al editar la entidad", ex);
            }
        }
        venta.setProductos(productosVenta);
        venta.setTotal(pedido.getTotal());
        venta.setCuotas(pedido.getCuotas());
        venta.setUsuario(usuario);
        ventaService.create(venta);

        try {
            pedidoService.destroy(pedido);
        } catch (HibernateException ex) {
            logger.error("Error al eliminar el pedido", ex);
        }

        return SUCCESS;
    }

    public String confirmarNuevaVenta() {

       Listado listado = listadoService.find(listadoId);

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
                stockService.edit(stock);
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
        venta.setUsuario(usuario);
        ventaService.create(venta);

        try {
            listadoService.destroy(listado);
        } catch (HibernateException ex) {
            logger.error("No existe la entidad", ex);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * @param pedidoId the pedidoId to set
     */
    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    /**
     * @param pedidoService the pedidoService to set
     */
    public void setPedidoService(GenericDao<Pedido, Integer> pedidoService) {
        this.pedidoService = pedidoService;
    }

    /**
     * @param ventaService the ventasService to set
     */
    public void setVentaService(GenericDao<Venta, Integer> ventaService) {
        this.ventaService = ventaService;
    }
    /**
     * @param stockService the stockService to set
     */
    public void setStockService(GenericDao<Stock, Integer> stockService) {
        this.stockService = stockService;
    }

    /**
     * @param listadoId the listadoId to set
     */
    public void setListadoId(String listadoId) {
        this.listadoId = listadoId;
    }

    /**
     * @param listadoService the listadosService to set
     */
    public void setListadoService(GenericDao<Listado, String> listadoService) {
        this.listadoService = listadoService;
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
