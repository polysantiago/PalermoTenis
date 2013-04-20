package com.palermotenis.controller.struts.actions.admin.ventas;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.json.JSONObject;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.controller.struts.actions.JsonActionSupport;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.pedidos.Pedido;
import com.palermotenis.model.beans.pedidos.PedidoProducto;
import com.palermotenis.model.beans.pedidos.PedidoProductoPK;
import com.palermotenis.util.Convertor;

/**
 * 
 * @author Poly
 */
public class PedidoProductoAction extends JsonActionSupport {

    private static final long serialVersionUID = -2042943340293648023L;

    private Integer pedidoId;
    private List<PedidoProducto> pedidosProductos;
    private Pedido pedido;
    private int cantidad;
    private Integer stockId;
    private Integer newStockId;

    @Autowired
    private GenericDao<PedidoProducto, PedidoProductoPK> pedidoProductoDao;

    @Autowired
    private GenericDao<Pedido, Integer> pedidoDao;

    @Autowired
    private GenericDao<Stock, Integer> stockDao;

    @Autowired
    private Convertor convertor;

    public String list() {
        pedido = pedidoDao.find(getPedidoId());
        pedidosProductos = pedidoProductoDao.queryBy("Pedido",
            new ImmutableMap.Builder<String, Object>().put("pedido", getPedido()).build());
        return "list";
    }

    public String add() {
        try {
            Stock stock = stockDao.find(stockId);
            Pedido pedido = pedidoDao.find(pedidoId);
            PedidoProducto pp = new PedidoProducto(new PedidoProductoPK(pedido, stock), 1);
            pp.setSubtotal(convertor.calculateSubtotalPesos(convertor.estimarPrecio(pp), 1));
            pedidoProductoDao.create(pp);

            double total = convertor.calculateTotalPesos(pedido.getPedidosProductos());
            pedido.setTotal(total);
            pedidoDao.edit(pedido);

            createJSON(SUCCESS, "OK", total);
        } catch (HibernateException ex) {
            createErrorJSON(ex);
        } catch (Exception ex) {
            createErrorJSON(ex);
        }
        return JSON;
    }

    public String editCantidad() {
        Pedido pedido = pedidoDao.find(pedidoId);
        Stock stock = stockDao.find(stockId);
        PedidoProducto pedidoProducto = pedidoProductoDao.find(new PedidoProductoPK(pedido, stock));
        pedidoProducto.setCantidad(cantidad);

        double subtotal = convertor.calculateSubtotalPesos(convertor.estimarPrecio(pedidoProducto), cantidad);
        double total = convertor.calculateTotalPesos(pedido.getPedidosProductos());
        pedidoProducto.setSubtotal(subtotal);
        pedido.setTotal(total);

        try {
            pedidoProductoDao.edit(pedidoProducto);
            pedidoDao.edit(pedido);
            createJSON(subtotal, total, 0);
        } catch (HibernateException ex) {
            createErrorJSON(ex);
        } catch (Exception ex) {
            createErrorJSON(ex);
        }
        return JSON;
    }

    public String editStock() {
        try {
            Pedido p = pedidoDao.find(pedidoId);
            PedidoProducto pedidoProducto = pedidoProductoDao.find(new PedidoProductoPK(p, stockDao.find(stockId)));
            pedidoProductoDao.destroy(pedidoProducto);

            Stock newStock = stockDao.find(newStockId);
            PedidoProducto pp = new PedidoProducto(new PedidoProductoPK(p, newStock), 1);
            double subtotal = convertor.calculateSubtotalPesos(convertor.estimarPrecio(pp), 1);
            pp.setSubtotal(subtotal);
            pedidoProductoDao.create(pp);

            double total = convertor.calculateTotalPesos(p.getPedidosProductos());
            p.setTotal(total);
            pedidoDao.edit(p);

            int stock = newStock.getStock();
            createJSON(subtotal, total, stock);
        } catch (HibernateException ex) {
            createErrorJSON(ex);
        } catch (Exception ex) {
            createErrorJSON(ex);
        }
        return JSON;
    }

    public String destroy() {
        try {
            Pedido p = pedidoDao.find(pedidoId);
            PedidoProducto pedidoProducto = pedidoProductoDao.find(new PedidoProductoPK(p, stockDao.find(stockId)));
            pedidoProductoDao.destroy(pedidoProducto);
            p.setTotal(convertor.calculateTotalPesos(p.getPedidosProductos()));
            createJSON(SUCCESS, "OK", p.getTotal());
        } catch (HibernateException ex) {
            createErrorJSON(ex);
        }
        return JSON;
    }

    private void createJSON(double subtotal, double total, int stock) {
        JSONObject jo = new JSONObject();
        jo.element("subtotal", subtotal);
        jo.element("total", total);
        jo.element("stock", stock);
        writeResponse(jo);
    }

    private void createJSON(String result, String msg, double total) {
        JSONObject jo = new JSONObject();
        jo.element("result", result);
        jo.element("msg", msg);
        jo.element("total", total);
        writeResponse(jo);
    }

    private void createErrorJSON(Exception ex) {
        createJSON(ERROR, ex.getLocalizedMessage(), 0.00);
        Logger.getLogger(PedidoProductoAction.class.getName()).log(Level.SEVERE, null, ex);
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
     * @param cantidad
     *            the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @param stockId
     *            the stockId to set
     */
    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    /**
     * @param newStockId
     *            the newStockId to set
     */
    public void setNewStockId(Integer newStockId) {
        this.newStockId = newStockId;
    }

}
