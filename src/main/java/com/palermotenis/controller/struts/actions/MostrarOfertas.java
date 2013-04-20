package com.palermotenis.controller.struts.actions;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.productos.Producto;

public class MostrarOfertas extends ActionSupport {

    private static final long serialVersionUID = 50801903034983564L;

    private List<Producto> productos;

    @Autowired
    private GenericDao<Producto, Integer> productoDao;

    @Override
    public String execute() {
        productos = productoDao.query("Ofertas");
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
        for (Precio p : precios) {
            if (p.isEnOferta()) {
                return p.getOrden();
            }
        }
        return null;
    }

    public Collection<Producto> getProductos() {
        return productos;
    }

}
