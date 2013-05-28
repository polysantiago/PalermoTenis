package com.palermotenis.controller.struts.actions.admin.ventas;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.json.JSONObject;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.model.beans.pedidos.Pedido;
import com.palermotenis.model.beans.pedidos.PedidoProducto;
import com.palermotenis.model.beans.pedidos.PedidoProductoPK;
import com.palermotenis.model.service.carrito.PedidoService;

public class PedidoProductoAction extends InputStreamActionSupport {

    private static final String LIST = "list";

    private static final long serialVersionUID = -2042943340293648023L;

    private Integer pedidoId;

    private List<PedidoProducto> pedidosProductos;

    private int cantidad;

    private Integer stockId;
    private Integer newStockId;

    @Autowired
    private PedidoService pedidoService;

    public String list() {
        pedidosProductos = pedidoService.getDetails(pedidoId);
        return LIST;
    }

    public String add() {
        try {
            Pedido pedido = pedidoService.addItem(pedidoId, stockId);
            createJSON(SUCCESS, "OK", pedido.getTotal());
        } catch (HibernateException ex) {
            createErrorJSON(ex);
        } catch (Exception ex) {
            createErrorJSON(ex);
        }
        return JSON;
    }

    public String editCantidad() {
        try {
            PedidoProducto pedidoProducto = pedidoService.updateQuantity(pedidoId, stockId, cantidad);
            createJSON(pedidoProducto.getSubtotal(), pedidoProducto.getId().getPedido().getTotal(), 0);
        } catch (HibernateException ex) {
            createErrorJSON(ex);
        } catch (Exception ex) {
            createErrorJSON(ex);
        }
        return JSON;
    }

    public String editStock() {
        try {
            PedidoProducto updated = pedidoService.updateStock(pedidoId, stockId, newStockId);
            PedidoProductoPK id = updated.getId();
            createJSON(updated.getSubtotal(), id.getPedido().getTotal(), id.getStock().getStock());
        } catch (HibernateException ex) {
            createErrorJSON(ex);
        } catch (Exception ex) {
            createErrorJSON(ex);
        }
        return JSON;
    }

    public String destroy() {
        try {
            Pedido pedido = pedidoService.removeItem(pedidoId, stockId);
            createJSON(SUCCESS, "OK", pedido.getTotal());
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

    public Integer getPedidoId() {
        return pedidoId;
    }

    public List<PedidoProducto> getPedidosProductos() {
        return pedidosProductos;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public void setNewStockId(Integer newStockId) {
        this.newStockId = newStockId;
    }

}
