package com.palermotenis.controller.struts.actions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.service.productos.ProductoService;

public class GetProductosRelacionados extends ActionSupport {

    private static final long serialVersionUID = -2681006745098644600L;

    private Integer productoId;

    @Autowired
    private ProductoService productoService;

    public List<Producto> getProductos() {
        return productoService.getProductosRelacionados(productoId);
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

}
