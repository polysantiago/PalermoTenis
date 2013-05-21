package com.palermotenis.model.states;

import java.util.List;
import java.util.Map;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;

/**
 * 
 * @author poly
 */
public class PresentableState extends DefaultState {

    private final Map<String, Object> map = HashBiMap.create();

    public PresentableState(Producto producto, List<Sucursal> sucursales, Presentacion presentacion) {
        this(producto, sucursales, new ImmutableList.Builder<Presentacion>().add(presentacion).build());
    }

    public PresentableState(Producto producto, List<Sucursal> sucursales, List<Presentacion> presentaciones) {
        super(producto, sucursales);
        addToList(presentaciones);
    }

    @Override
    protected void create(Object obj) {
        if (obj instanceof Sucursal) {
            map.put("sucursal", obj);
        } else if (obj instanceof Presentacion) {
            map.put("presentacion", obj);
        }

        if (map.size() == 2) {
            Sucursal sucursal = (Sucursal) map.get("sucursal");
            Presentacion presentacion = (Presentacion) map.get("presentacion");
            getStockService().createStock(getProducto(), sucursal, presentacion);
            map.clear();
        }
    }
}
