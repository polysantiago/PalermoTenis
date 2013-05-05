package com.palermotenis.controller.struts.actions;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.service.productos.ProductoService;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;

public class PrepararPresentaciones extends ActionSupport {

    private static final long serialVersionUID = -6953347495272026802L;

    private final Map<String, Object> map = Maps.newHashMap();

    @Autowired
    private TipoProductoService tipoProductoService;

    @Autowired
    private ProductoService productoService;

    private Integer productoId;

    @Override
    public String execute() {
        List<Map<String, Object>> tiposProductos = Lists.newArrayList();
        for (TipoProducto tipoProducto : getTiposProducto()) {
            tiposProductos.add(new ImmutableMap.Builder<String, Object>()
                .put("id", tipoProducto.getId())
                .put("text", tipoProducto.getNombre())
                .put("children", tipoProducto.getTiposPresentacion())
                .build());
        }
        Producto producto = productoService.getProductById(productoId);
        map.put("tiposProducto", tiposProductos);
        map.put("preselectFirst", producto.getTipoProducto().getId());
        return SUCCESS;
    }

    private List<TipoProducto> getTiposProducto() {
        return tipoProductoService.getAllTiposProductoPresentables();
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public Map<String, Object> getMap() {
        return map;
    }
}
