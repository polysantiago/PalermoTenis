/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans.productos.tipos;

import com.google.common.collect.HashBiMap;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import java.util.List;
import java.util.Map;

/**
 *
 * @author poly
 */
public class ClasificableState extends DefaultState {

    private Map<String, Object> map = HashBiMap.create();

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
            getStockService().create(new Stock(getProducto(), sucursal, valorClasificatorio));
            map.clear();
        }
    }
}
