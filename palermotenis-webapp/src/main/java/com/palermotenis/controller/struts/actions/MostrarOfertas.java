package com.palermotenis.controller.struts.actions;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.service.productos.ProductoService;

public class MostrarOfertas extends ActionSupport {

    private static final long serialVersionUID = 50801903034983564L;

    private List<Producto> productos;

    @Autowired
    private ProductoService productoService;

    @Override
    public String execute() {
        productos = productoService.getProductosOnSale();

        Collections.sort(productos, new Comparator<Producto>() {
            @Override
            public int compare(Producto p1, Producto p2) {
                return getOrden(p1).compareTo(getOrden(p2));
            }
        });
        return SUCCESS;
    }

    private Integer getOrden(Producto producto) {
        Collection<? extends Precio> precios = producto.getPrecios();
        for (Precio precio : precios) {
            if (precio.isEnOferta()) {
                return precio.getOrden();
            }
        }
        return null;
    }

    public Collection<Producto> getProductos() {
        return productos;
    }

}
