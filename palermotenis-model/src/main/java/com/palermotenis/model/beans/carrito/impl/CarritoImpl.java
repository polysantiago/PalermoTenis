package com.palermotenis.model.beans.carrito.impl;

import java.util.HashMap;
import java.util.Map;

import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.carrito.Carrito;
import com.palermotenis.model.beans.carrito.Item;

public class CarritoImpl implements Carrito {

    private static final long serialVersionUID = -5810633890011863550L;

    private final Map<Stock, Item> contenido = new HashMap<Stock, Item>();
    private Pago pago;
    private int cuotas;

    @Override
    public void agregar(int cantidad, Stock stock) {
        if (!hasStock(stock)) {
            Item item = new Item(cantidad);
            contenido.put(stock, item);
        } else {
            Item item = get(stock);
            item.setCantidad(item.getCantidad() + cantidad);
        }
    }

    @Override
    public Item get(Stock stock) {
        return contenido.get(stock);
    }

    @Override
    public void quitar(Stock stock) {
        contenido.remove(stock);
    }

    @Override
    public Map<Stock, Item> getContenido() {
        return contenido;
    }

    @Override
    public int getCantidad(Stock stock) {
        if (!hasStock(stock)) {
            return 0;
        }
        return contenido.get(stock).getCantidad();
    }

    @Override
    public double getTotal() {
        double total = 0.0;
        for (Item i : contenido.values()) {
            total += i.getSubtotal();
        }
        return total;
    }

    @Override
    public int getCantidadItems() {
        int qty = 0;
        for (Item i : contenido.values()) {
            qty += i.getCantidad();
        }
        return qty;
    }

    @Override
    public void vaciar() {
        contenido.clear();
    }

    @Override
    public Pago getPago() {
        return pago;
    }

    @Override
    public void setPago(Pago pago) {
        this.pago = pago;
    }

    @Override
    public int getCuotas() {
        return cuotas;
    }

    @Override
    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    @Override
    public boolean hasStock(Stock stock) {
        return contenido.containsKey(stock);
    }

    @Override
    public String toString() {
        return "Carrito{contenido=" + contenido + "}";
    }
}
