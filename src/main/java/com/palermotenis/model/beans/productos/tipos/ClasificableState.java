/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.palermotenis.model.beans.productos.tipos;

import java.util.List;
import java.util.Map;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;

/**
 * 
 * @author poly
 */
public class ClasificableState extends DefaultState {

    private final Map<String, Object> map = HashBiMap.create();

    public ClasificableState(Producto producto, List<Sucursal> sucursales, ValorClasificatorio valorClasificatorio) {
        this(producto, sucursales, new ImmutableList.Builder<ValorClasificatorio>().add(valorClasificatorio).build());
    }

    public ClasificableState(Producto producto, List<Sucursal> sucursales,
            List<ValorClasificatorio> valoresClasificatorios) {
        super(producto, sucursales);
        addToList(valoresClasificatorios);
    }

    @Override
    protected void create(Object obj) {
        if (obj instanceof Sucursal) {
            map.put("sucursal", obj);
        } else if (obj instanceof ValorClasificatorio) {
            map.put("valorClasificatorio", obj);
        }

        if (map.size() == 2) {
            Sucursal sucursal = (Sucursal) map.get("sucursal");
            ValorClasificatorio valorClasificatorio = (ValorClasificatorio) map.get("valorClasificatorio");
            getStockService().createStock(getProducto(), sucursal, valorClasificatorio);
            map.clear();
        }
    }
}
