/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans.productos.tipos;

import com.google.common.collect.HashBiMap;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author poly
 */
public class PresentableState extends DefaultState {

    private Map<String, Object> map = HashBiMap.create();

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
            getStockDao().create(new Stock(getProducto(), sucursal, presentacion));
            map.clear();
        }
    }
}
