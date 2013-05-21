package com.palermotenis.model.states;

import java.util.List;

import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.productos.Producto;

public class DefaultState extends AbstractState implements State {

    public DefaultState(Producto producto, List<Sucursal> sucursales) {
        super(producto);
        addToList(sucursales);
    }

    @Override
    protected void create(Object obj) {
        Sucursal sucursal = (Sucursal) obj;
        getStockService().createStock(getProducto(), sucursal);
    }
}
