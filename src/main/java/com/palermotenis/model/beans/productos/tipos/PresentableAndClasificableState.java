package com.palermotenis.model.beans.productos.tipos;

import java.util.List;
import java.util.Map;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;

/**
 * 
 * @author poly
 */
public class PresentableAndClasificableState extends PresentableState {

    private final Map<String, Object> map = HashBiMap.create();

    public PresentableAndClasificableState(Producto producto, List<Sucursal> sucursales,
            List<ValorClasificatorio> valoresClasificatorios, Presentacion presentacion) {
        this(producto, sucursales, valoresClasificatorios, new ImmutableList.Builder<Presentacion>()
            .add(presentacion)
            .build());
    }

    public PresentableAndClasificableState(Producto producto, List<Sucursal> sucursales,
            ValorClasificatorio valorClasificatorio, List<Presentacion> presentaciones) {
        this(producto, sucursales, new ImmutableList.Builder<ValorClasificatorio>().add(valorClasificatorio).build(),
            presentaciones);
    }

    public PresentableAndClasificableState(Producto producto, List<Sucursal> sucursales,
            List<ValorClasificatorio> valoresClasificatorios, List<Presentacion> presentaciones) {
        super(producto, sucursales, presentaciones);
        addToList(valoresClasificatorios);
    }

    @Override
    protected void create(Object obj) {
        addToMap(obj, Sucursal.class, "sucursal");
        addToMap(obj, Presentacion.class, "presentacion");
        addToMap(obj, ValorClasificatorio.class, "valorClasificatorio");

        if (map.size() == 3) {
            Sucursal sucursal = (Sucursal) map.get("sucursal");
            Presentacion presentacion = (Presentacion) map.get("presentacion");
            ValorClasificatorio valorClasificatorio = (ValorClasificatorio) map.get("valorClasificatorio");
            getStockService().createStock(getProducto(), sucursal, presentacion, valorClasificatorio);
            map.clear();
        }
    }

    private void addToMap(Object obj, Class<?> clazz, String name) {
        if (clazz.isInstance(obj)) {
            map.put(name, obj);
        }
    }
}
