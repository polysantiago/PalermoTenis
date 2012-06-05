/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.controller.carrito;

import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.productos.Producto;
import java.io.Serializable;

/**
 *
 * @author Poly
 */
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    private int cantidad;
    private double subtotal;
    private double precioUnitario;

    public Item(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * @return the precioUnitario
     */
    public double getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * @param precioUnitario the precioUnitario to set
     */
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }



}
