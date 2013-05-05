package com.palermotenis.controller.carrito;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.service.pagos.PagoService;
import com.palermotenis.model.service.precios.impl.PrecioService;

public class CarritoImpl implements Carrito {

    private static final long serialVersionUID = -5810633890011863550L;

    private final Map<Stock, Item> contenido = new HashMap<Stock, Item>();
    private Pago pago;
    private int cuotas;

    @Autowired
    private transient PagoService pagoService;

    @Autowired
    private transient PrecioService precioService;

    @Override
    public void agregar(int cantidad, Stock stock) {
        if (!contenido.containsKey(stock)) {
            Item item = new Item(cantidad);
            contenido.put(stock, item);
        } else {
            Item i = contenido.get(stock);
            i.setCantidad(i.getCantidad() + cantidad);
        }
        setPrecio(stock);
    }

    @Override
    public void setCantidad(int cantidad, Stock stock) {
        if (cantidad <= 0) {
            contenido.remove(stock);
            return;
        }
        if (!contenido.containsKey(stock)) {
            agregar(cantidad, stock);
        } else {
            Item i = contenido.get(stock);
            i.setCantidad(cantidad);
            setPrecio(stock);
        }
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
        if (!contenido.containsKey(stock)) {
            return 0;
        }
        return contenido.get(stock).getCantidad();
    }

    private void setPrecio(Stock stock) {
        if (!contenido.containsKey(stock)) {
            return;
        } else {
            if (pago == null) {
                pago = pagoService.getFirstPago();
            }
            Item item = contenido.get(stock);
            Precio precio = precioService.estimarPrecio(stock, pago, cuotas);
            item.setPrecioUnitario(precioService.calculatePrecioUnitarioPesos(precio));
            item.setSubtotal(precioService.calculateSubtotalPesos(precio, item.getCantidad()));
        }
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

    private void updatePrecios() {
        for (Stock s : contenido.keySet()) {
            setPrecio(s);
        }
    }

    @Override
    public Pago getPago() {
        return pago;
    }

    @Override
    public void setPago(Pago pago) {
        if (this.pago == null) {
            this.pago = pago;
        } else if (!this.pago.equals(pago)) {
            this.pago = pago;
            updatePrecios();
        }
    }

    @Override
    public int getCuotas() {
        return cuotas;
    }

    @Override
    public void setCuotas(int cuotas) {
        if (this.cuotas != cuotas) {
            this.cuotas = cuotas;
            updatePrecios();
        }
    }

    @Override
    public String toString() {
        return "Carrito{contenido=" + contenido + "}";
    }
}
