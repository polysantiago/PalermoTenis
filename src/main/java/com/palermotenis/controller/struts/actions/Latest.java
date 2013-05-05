package com.palermotenis.controller.struts.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.service.productos.ProductoService;

public class Latest extends JsonActionSupport {

    private static final long serialVersionUID = -9117974645418468534L;

    private final List<Map<String, Object>> productosMap = new ArrayList<Map<String, Object>>();

    @Autowired
    private ProductoService productoService;

    @Override
    public String execute() {
        for (Producto producto : getOfertas()) {
            productosMap.add(new ImmutableMap.Builder<String, Object>()
                .put("id", producto.getId())
                .put("text", producto.getModelo().getNombre())
                .put("activo", producto.isActivo())
                .put("hasStock", producto.hasStock())
                .build());
        }
        return JSON;
    }

    private List<Producto> getOfertas() {
        return productoService.getLatest8Ofertas();
    }

    public List<Map<String, Object>> getProductosMap() {
        return productosMap;
    }
}
