/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.carrito;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.carrito.Carrito;
import com.palermotenis.controller.carrito.Item;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author Poly
 */
public class CarritoAction extends ActionSupport {

    private Carrito carrito;
    private Integer productoId;
    private Integer presentacionId;
    private Integer valorClasificatorioId;
    private Integer pagoId;
    private Integer cuotas;
    private Integer stockId;
    private Map<Integer, Integer> cantidad = new HashMap<Integer, Integer>();
    private GenericDao<Producto, Integer> productoService;
    private GenericDao<Presentacion, Integer> presentacionService;
    private GenericDao<ValorClasificatorio, Integer> valorClasificatorioService;
    private GenericDao<Pago, Integer> pagoService;
    private GenericDao<Stock, Integer> stockService;
    private InputStream inputStream;
    private Collection<Item> items;
    private double total = 0.00;
    private int itemQty = 0;
    private static final Logger logger = Logger.getLogger(CarritoAction.class);
    private String remoteAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
    private String browser = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getHeader("User-Agent");
    private String usuario = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

    public String add() {
        Producto producto = productoService.find(productoId);
        Presentacion presentacion = null;
        ValorClasificatorio valorClasificatorio = null;

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("producto", producto);

        if (presentacionId != null) {
            presentacion = presentacionService.find(presentacionId);
            args.put("presentacion", presentacion);
        }
        if (valorClasificatorioId != null) {
            valorClasificatorio = valorClasificatorioService.find(valorClasificatorioId);
            args.put("valorClasificatorio", valorClasificatorio);
        }

        Stock stock = null;
        if (presentacion != null && valorClasificatorio != null) {
            stock = stockService.queryBy("Producto,Presentacion,ValorClasificatorio", args).get(0);
        } else if (presentacion != null) {
            stock = stockService.queryBy("Producto,Presentacion", args).get(0);
        } else if (valorClasificatorio != null) {
            stock = stockService.queryBy("Producto,ValorClasificatorio", args).get(0);
        } else {
            stock = stockService.queryBy("Producto", args).get(0);
        }

        getCarrito().agregar(1, stock);

        logger.info("El usuario " + usuario + " con IP [" + remoteAddress + "] y navegador " + browser + " ha agregado " + stock);

        return SUCCESS;
    }

    public String show() {
        total = carrito.getTotal();
        itemQty = carrito.getCantidadItems();
        return SUCCESS;
    }

    public String remove() {
        Stock stock = stockService.find(stockId);
        getCarrito().quitar(stock);
        logger.info("El usuario " + usuario + " con IP [" + remoteAddress + "] y navegador " + browser + " ha quitado " + stock);
        return SUCCESS;
    }

    public String empty() {
        logger.info("El usuario " + usuario + " con IP [" + remoteAddress + "] y navegador " + browser + " ha vaciado el carrito");
        getCarrito().vaciar();
        return SUCCESS;
    }

    public String editPago() {
        carrito.setCuotas(cuotas);
        carrito.setPago(pagoService.find(pagoId));

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

        inputStream = StringUtility.getInputString(main.toString());
        return "edit";
    }

    public String updateCantidades() {
        for (Integer i : getCantidad().keySet()) {
            carrito.setCantidad(getCantidad().get(i), stockService.find(i));
        }
        return SUCCESS;
    }

    /**
     * @param carrito the carrito to set
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
     * @param productoId the productoId to set
     */
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    /**
     * @param presentacionId the presentacionId to set
     */
    public void setPresentacionId(Integer presentacionId) {
        this.presentacionId = presentacionId;
    }

    /**
     * @param valorClasificatorioId the valorClasificatorioId to set
     */
    public void setValorClasificatorioId(Integer valorClasificatorioId) {
        this.valorClasificatorioId = valorClasificatorioId;
    }

    /**
     * @param productoService the productoService to set
     */
    public void setProductoService(GenericDao<Producto, Integer> productoService) {
        this.productoService = productoService;
    }

    /**
     * @param presentacionService the presentacionService to set
     */
    public void setPresentacionService(GenericDao<Presentacion, Integer> presentacionService) {
        this.presentacionService = presentacionService;
    }

    /**
     * @param valorClasificatorioService the valorClasificatorioService to set
     */
    public void setValorClasificatorioService(GenericDao<ValorClasificatorio, Integer> valorClasificatorioService) {
        this.valorClasificatorioService = valorClasificatorioService;
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
     * @param pagoId the pagoId to set
     */
    public void setPagoId(Integer pagoId) {
        this.pagoId = pagoId;
    }

    /**
     * @param cuotas the cuotas to set
     */
    public void setCuotas(Integer cuotas) {
        this.cuotas = cuotas;
    }

    /**
     * @param pagoService the pagosService to set
     */
    public void setPagoService(GenericDao<Pago, Integer> pagoService) {
        this.pagoService = pagoService;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param stockService the stockService to set
     */
    public void setStockService(GenericDao<Stock, Integer> stockService) {
        this.stockService = stockService;
    }

    /**
     * @param stockId the stockId to set
     */
    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    /**
     * @param cantidad the cantidad to set
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
