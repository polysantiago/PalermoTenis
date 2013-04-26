/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.carrito;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.palermotenis.controller.carrito.Carrito;
import com.palermotenis.controller.carrito.Item;
import com.palermotenis.controller.struts.actions.JsonActionSupport;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.model.dao.Dao;
import com.palermotenis.util.SecurityUtil;

/**
 * 
 * @author Poly
 */
public class CarritoAction extends JsonActionSupport {

    private static final String EDIT = "edit";

    private static final long serialVersionUID = -7964324584663887753L;

    private static final Logger logger = Logger.getLogger(CarritoAction.class);

    private Carrito carrito;

    private Integer productoId;
    private Integer presentacionId;
    private Integer valorClasificatorioId;
    private Integer pagoId;
    private Integer cuotas;
    private Integer stockId;

    private final Map<Integer, Integer> cantidad = new HashMap<Integer, Integer>();

    private Collection<Item> items;

    private double total = 0.00;

    private int itemQty = 0;

    @Autowired
    private Dao<Producto, Integer> productoDao;

    @Autowired
    private Dao<Presentacion, Integer> presentacionDao;

    @Autowired
    private Dao<ValorClasificatorio, Integer> valorClasificatorioDao;

    @Autowired
    private Dao<Pago, Integer> pagoDao;

    @Autowired
    private Dao<Stock, Integer> stockDao;

    public String add() {
        Producto producto = productoDao.find(productoId);
        Presentacion presentacion = null;
        ValorClasificatorio valorClasificatorio = null;

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("producto", producto);

        if (presentacionId != null) {
            presentacion = presentacionDao.find(presentacionId);
            args.put("presentacion", presentacion);
        }
        if (valorClasificatorioId != null) {
            valorClasificatorio = valorClasificatorioDao.find(valorClasificatorioId);
            args.put("valorClasificatorio", valorClasificatorio);
        }

        Stock stock = null;
        if (presentacion != null && valorClasificatorio != null) {
            stock = stockDao.queryBy("Producto,Presentacion,ValorClasificatorio", args).get(0);
        } else if (presentacion != null) {
            stock = stockDao.queryBy("Producto,Presentacion", args).get(0);
        } else if (valorClasificatorio != null) {
            stock = stockDao.queryBy("Producto,ValorClasificatorio", args).get(0);
        } else {
            stock = stockDao.queryBy("Producto", args).get(0);
        }

        getCarrito().agregar(1, stock);

        logger.info("Stock added: " + stock + contextInfo());

        return SUCCESS;
    }

    public String show() {
        total = carrito.getTotal();
        itemQty = carrito.getCantidadItems();
        return SUCCESS;
    }

    public String remove() {
        Stock stock = stockDao.find(stockId);
        getCarrito().quitar(stock);
        logger.info("Stock removed: " + stock + contextInfo());
        return SUCCESS;
    }

    public String empty() {
        logger.info("Cart has been emptied" + contextInfo());
        getCarrito().vaciar();
        return SUCCESS;
    }

    public String editPago() {
        carrito.setCuotas(cuotas);
        carrito.setPago(pagoDao.find(pagoId));

        JSONObject main = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Stock s : carrito.getContenido().keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.element("id", s.getId());
            jsonObject.element("item", JSONSerializer.toJSON(carrito.getContenido().get(s)));
            jsonArray.add(jsonObject);
        }
        main.element("items", jsonArray);
        main.element("total", carrito.getTotal());
        main.element("pago", carrito.getPago().getNombre());

        writeResponse(main);
        return EDIT;
    }

    public String updateCantidades() {
        for (Integer i : getCantidad().keySet()) {
            carrito.setCantidad(getCantidad().get(i), stockDao.find(i));
        }
        return SUCCESS;
    }

    private String contextInfo() {
        return " - Username[" + getUsername() + "] - IP[" + getRemoteAddress() + "] - Browser[" + getBrowser() + "]";
    }

    private String getUsername() {
        return SecurityUtil.getLoggedInUser().getUsername();
    }

    private String getRemoteAddress() {
        return getRequest().getRemoteAddr();
    }

    private String getBrowser() {
        return getRequest().getHeader("User-Agent");
    }

    private HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    }

    /**
     * @param carrito
     *            the carrito to set
     */
    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    /**
     * @return the carrito
     */
    public Carrito getCarrito() {
        return carrito;
    }

    /**
     * @param productoId
     *            the productoId to set
     */
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    /**
     * @param presentacionId
     *            the presentacionId to set
     */
    public void setPresentacionId(Integer presentacionId) {
        this.presentacionId = presentacionId;
    }

    /**
     * @param valorClasificatorioId
     *            the valorClasificatorioId to set
     */
    public void setValorClasificatorioId(Integer valorClasificatorioId) {
        this.valorClasificatorioId = valorClasificatorioId;
    }

    /**
     * @return the items
     */
    public Collection<Item> getItems() {
        return items;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @return the itemQty
     */
    public int getItemQty() {
        return itemQty;
    }

    /**
     * @param pagoId
     *            the pagoId to set
     */
    public void setPagoId(Integer pagoId) {
        this.pagoId = pagoId;
    }

    /**
     * @param cuotas
     *            the cuotas to set
     */
    public void setCuotas(Integer cuotas) {
        this.cuotas = cuotas;
    }

    /**
     * @param stockId
     *            the stockId to set
     */
    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    /**
     * @param cantidad
     *            the cantidad to set
     */
    public void setCantidad(Map<Integer, Integer> cantidad) {
        this.setCantidad(cantidad);
    }

    /**
     * @return the cantidad
     */
    public Map<Integer, Integer> getCantidad() {
        return cantidad;
    }
}
