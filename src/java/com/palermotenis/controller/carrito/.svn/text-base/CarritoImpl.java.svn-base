/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.carrito;

import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.util.Convertor;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Poly
 */
public class CarritoImpl implements Carrito {

    private static final long serialVersionUID = 1L;
    private Map<Stock, Item> contenido = new HashMap<Stock, Item>();
    private transient GenericDao<Pago, Integer> pagoService;
    private transient Convertor convertor;
    private Pago pago;
    private int cuotas;

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

    public void quitar(Stock stock) {
        contenido.remove(stock);
    }

    public Map<Stock, Item> getContenido() {
        return contenido;
    }

    public int getCantidad(Stock stock) {
        if (!contenido.containsKey(stock)) {
            return 0;
        }
        return contenido.get(stock).getCantidad();
    }

    private void setPrecio(Stock stock) {
        Convertor c = getConvertor();        
        if (!contenido.containsKey(stock)) {
            return;
        } else {
            if (pago == null) {
                pago = getPagoService().find(1);
            }
            Item i = contenido.get(stock);
            Precio p = c.estimarPrecio(stock, pago, cuotas);
            i.setPrecioUnitario(c.calculatePrecioUnitarioPesos(p));
            i.setSubtotal(c.calculateSubtotalPesos(p, i.getCantidad()));
        }
    }

    public double getTotal() {
        double total = 0.0;
        for (Item i : contenido.values()) {
            total += i.getSubtotal();
        }
        return total;
    }

    public int getCantidadItems() {
        int qty = 0;
        for (Item i : contenido.values()) {
            qty += i.getCantidad();
        }
        return qty;
    }

    public void vaciar() {
        contenido.clear();
    }

    private void updatePrecios() {
        for (Stock s : contenido.keySet()) {
            setPrecio(s);
        }
    }

    public Convertor getConvertor() {
        if(convertor == null){
            convertor = new Convertor();
        }
        return convertor;
    }

    public GenericDao<Pago, Integer> getPagoService() {
        //return (GenericDao<Pago, Integer>)applicationContext.getBean("pagoService");
        return pagoService;
    }

    public void setPagoService(GenericDao<Pago, Integer> pagoService) {
        this.pagoService = pagoService;
    }

    /**
     * @param convertor the convertor to set
     */
    public void setConvertor(Convertor convertor) {
        this.convertor = convertor;
    }

    /**
     * @return the pago
     */
    public Pago getPago() {
        return pago;
    }

    /**
     * @param pago the pago to set
     */
    public void setPago(Pago pago) {
        if(this.pago == null){
            this.pago = pago;
        } else if (!this.pago.equals(pago)) {
            this.pago = pago;
            updatePrecios();
        }
    }

    /**
     * @return the cuotas
     */
    public int getCuotas() {
        return cuotas;
    }

    /**
     * @param cuotas the cuotas to set
     */
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
