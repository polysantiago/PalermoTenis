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
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import java.util.List;
import java.util.Map;

/**
 *
 * @author poly
 */
public class PresentableAndClasificableState extends PresentableState {

    private Map<String, Object> map = HashBiMap.create();

    public PresentableAndClasificableState(Producto producto,
            List<Sucursal> sucursales,
            List<ValorClasificatorio> valoresClasificatorios,
            List<Presentacion> presentaciones) {
        super(producto, sucursales, presentaciones);
        addToList(valoresClasificatorios);
    }

    @Override
    protected void create(Object obj) {
        addToMap(obj, Sucursal.class, "sucursal");
        addToMap(obj, Presentacion.class, "presentacion");
        addToMap(obj, ValorClasificatorio.class, "valorClasificatorio");

        if (map.size() == 3) {
            getStockDao().create(
                    new Stock(getProducto(),
                    (Sucursal) map.get("sucursal"),
                    (ValorClasificatorio) map.get("valorClasificatorio"),
                    (Presentacion) map.get("presentacion")));
            map.clear();
        }
    }

    private void addToMap(Object obj, Class clazz, String name) {
        if (clazz.isInstance(obj)) {
            map.put(name, obj);
        }
    }
}
