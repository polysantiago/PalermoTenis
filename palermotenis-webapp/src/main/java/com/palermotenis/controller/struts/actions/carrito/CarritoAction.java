package com.palermotenis.controller.struts.actions.carrito;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.carrito.Carrito;
import com.palermotenis.model.beans.carrito.Item;
import com.palermotenis.model.service.carrito.CarritoService;

public class CarritoAction extends InputStreamActionSupport {

    private static final String EDIT = "edit";

    private static final long serialVersionUID = -7964324584663887753L;

    private Carrito carrito;

    private Integer productoId;
    private Integer pagoId;
    private Integer cuotas;
    private Integer stockId;

    private final Map<Integer, Integer> cantidad = new HashMap<Integer, Integer>();

    private final Map<String, Object> resultsMap = Maps.newLinkedHashMap();

    private List<Item> items;

    private double total = 0.00;

    private int itemQty = 0;

    @Autowired
    private CarritoService carritoService;

    public String add() {
        carritoService.add(carrito, productoId);
        return SUCCESS;
    }

    public String show() {
        total = carrito.getTotal();
        itemQty = carrito.getCantidadItems();
        return SUCCESS;
    }

    public String remove() {
        carritoService.remove(carrito, stockId);
        return SUCCESS;
    }

    public String empty() {
        carritoService.empty(carrito);
        return SUCCESS;
    }

    public String editPago() {
        carritoService.edit(carrito, cuotas, pagoId);

        List<Map<String, Object>> items = Lists.newArrayList();
        for (Entry<Stock, Item> entry : carrito.getContenido().entrySet()) {
            Stock stock = entry.getKey();
            Item item = entry.getValue();
            items.add(new ImmutableMap.Builder<String, Object>().put("id", stock.getId()).put("item", item).build());
        }
        resultsMap.put("items", items);
        resultsMap.put("total", carrito.getTotal());
        resultsMap.put("pago", carrito.getPago().getNombre());
        return EDIT;
    }

    public String updateCantidades() {
        carritoService.updateCantidades(carrito, cantidad);
        return SUCCESS;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public List<Item> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setPagoId(Integer pagoId) {
        this.pagoId = pagoId;
    }

    public void setCuotas(Integer cuotas) {
        this.cuotas = cuotas;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public void setCantidad(Map<Integer, Integer> cantidad) {
        this.setCantidad(cantidad);
    }

    public Map<Integer, Integer> getCantidad() {
        return cantidad;
    }

    public Map<String, Object> getResultsMap() {
        return resultsMap;
    }
}
