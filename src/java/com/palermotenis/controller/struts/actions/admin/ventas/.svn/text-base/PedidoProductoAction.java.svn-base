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
import com.palermotenis.model.beans.pedidos.PedidoProductoPK;
import com.palermotenis.util.Convertor;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONObject;
import org.hibernate.HibernateException;

/**
 *
 * @author Poly
 */
public class PedidoProductoAction extends ActionSupport {

    private GenericDao<PedidoProducto, PedidoProductoPK> pedidoProductoService;
    private GenericDao<Pedido, Integer> pedidoService;
    private GenericDao<Stock, Integer> stockService;
    private Convertor convertor;
    private Integer pedidoId;
    private List<PedidoProducto> pedidosProductos;
    private Pedido pedido;
    private int cantidad;
    private Integer stockId;
    private Integer newStockId;
    private InputStream inputStream;

    public String list() {
        pedido = pedidoService.find(getPedidoId());
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("pedido", getPedido());
        pedidosProductos = pedidoProductoService.queryBy("Pedido", args);
        return "list";
    }

    public String add() {
        try {
            Stock s = stockService.find(stockId);
            Pedido p = pedidoService.find(pedidoId);
            PedidoProducto pp = new PedidoProducto(new PedidoProductoPK(p, s), 1);
            pp.setSubtotal(convertor.calculateSubtotalPesos(convertor.estimarPrecio(pp), 1));
            pedidoProductoService.create(pp);

            double total = convertor.calculateTotalPesos(p.getPedidosProductos());
            p.setTotal(total);
            pedidoService.edit(p);

            createJSON(SUCCESS, "OK", total);
        } catch (HibernateException ex) {
            createJSON(ERROR, ex.getLocalizedMessage(),0.00);
            Logger.getLogger(PedidoProductoAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            createJSON(ERROR, ex.getLocalizedMessage(),0.00);
            Logger.getLogger(PedidoProductoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "json";
    }

    public String editCantidad() {
        Pedido p = pedidoService.find(pedidoId);
        Stock s = stockService.find(stockId);
        PedidoProducto pp = pedidoProductoService.find(new PedidoProductoPK(p, s));
        pp.setCantidad(cantidad);
        
        double subtotal = convertor.calculateSubtotalPesos(convertor.estimarPrecio(pp), cantidad);
        double total = convertor.calculateTotalPesos(p.getPedidosProductos());
        pp.setSubtotal(subtotal);
        p.setTotal(total);
        
        try {
            pedidoProductoService.edit(pp);
            pedidoService.edit(p);
            createJSON(subtotal, total, 0);
        } catch (HibernateException ex) {
            createJSON(ERROR, ex.getLocalizedMessage(),0.00);
            Logger.getLogger(PedidoProductoAction.class.getName()).log(Level.SEVERE, null, ex);            
        } catch (Exception ex) {
            createJSON(ERROR, ex.getLocalizedMessage(),0.00);
            Logger.getLogger(PedidoProductoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "json";
    }

    public String editStock() {
        try {
            Pedido p = pedidoService.find(pedidoId);
            PedidoProducto pedidoProducto = pedidoProductoService.find(new PedidoProductoPK(p, stockService.find(stockId)));
            pedidoProductoService.destroy(pedidoProducto);

            Stock newStock = stockService.find(newStockId);
            PedidoProducto pp = new PedidoProducto(new PedidoProductoPK(p, newStock),1);
            double subtotal = convertor.calculateSubtotalPesos(convertor.estimarPrecio(pp),1);
            pp.setSubtotal(subtotal);
            pedidoProductoService.create(pp);

            double total = convertor.calculateTotalPesos(p.getPedidosProductos());
            p.setTotal(total);            
            pedidoService.edit(p);

            int stock = newStock.getStock();
            createJSON(subtotal, total, stock);
        } catch (HibernateException ex) {
            Logger.getLogger(PedidoProductoAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PedidoProductoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "json";
    }

    public String destroy() {
        try {
            Pedido p = pedidoService.find(pedidoId);
            PedidoProducto pedidoProducto = pedidoProductoService.find(new PedidoProductoPK(p, stockService.find(stockId)));
            pedidoProductoService.destroy(pedidoProducto);
            p.setTotal(convertor.calculateTotalPesos(p.getPedidosProductos()));
            createJSON(SUCCESS, "OK",p.getTotal());
        } catch (HibernateException ex) {
            createJSON(ERROR, ex.getLocalizedMessage(),0.00);
            Logger.getLogger(PedidoProductoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "json";
    }

    private void createJSON(double subtotal, double total, int stock){
        JSONObject jo = new JSONObject();
        jo.element("subtotal",subtotal);
        jo.element("total", total);
        jo.element("stock", stock);
        inputStream = StringUtility.getInputString(jo.toString());
    }

    private void createJSON(String result, String msg, double total){
        JSONObject jo = new JSONObject();
        jo.element("result", result);
        jo.element("msg", msg);
        jo.element("total",total);
        inputStream = StringUtility.getInputString(jo.toString());
    }


    public void setPedidoProductoService(GenericDao<PedidoProducto, PedidoProductoPK> pedidoProductoService) {
        this.pedidoProductoService = pedidoProductoService;
    }


    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    /**
     * @return the pedidoId
     */
    public Integer getPedidoId() {
        return pedidoId;
    }

    /**
     * @param pedidoService the pedidoService to set
     */
    public void setPedidoService(GenericDao<Pedido, Integer> pedidoService) {
        this.pedidoService = pedidoService;
    }

    /**
     * @return the pedidosProductos
     */
    public List<PedidoProducto> getPedidosProductos() {
        return pedidosProductos;
    }

    /**
     * @return the pedido
     */
    public Pedido getPedido() {
        return pedido;
    }

    /**
     * @param stockService the stockService to set
     */
    public void setStockService(GenericDao<Stock, Integer> stockService) {
        this.stockService = stockService;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @param stockId the stockId to set
     */
    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    /**
     * @param convertor the convertor to set
     */
    public void setConvertor(Convertor convertor) {
        this.convertor = convertor;
    }

    /**
     * @param newStockId the newStockId to set
     */
    public void setNewStockId(Integer newStockId) {
        this.newStockId = newStockId;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }
}
