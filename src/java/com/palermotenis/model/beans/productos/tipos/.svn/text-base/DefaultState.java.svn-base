/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans.productos.tipos;

import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.productos.Producto;
import java.util.List;

/**
 *
 * @author poly
 */
public class DefaultState extends AbstractState implements State {
    
    public DefaultState(Producto producto, List<Sucursal> sucursales) {
        super(producto);
        addToList(sucursales);
    }

    protected void create(Object obj) {
        getStockService().create(new Stock(getProducto(), (Sucursal) obj));
    }
}
