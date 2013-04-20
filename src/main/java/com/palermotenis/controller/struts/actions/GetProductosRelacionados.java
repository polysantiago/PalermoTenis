package com.palermotenis.controller.struts.actions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.productos.Producto;

/**
 * 
 * @author poly
 */
public class GetProductosRelacionados extends ActionSupport {

    private static final long serialVersionUID = -2681006745098644600L;

    private Integer productoId;
    private List<Producto> productos;

    @Autowired
    private GenericDao<Producto, Integer> productoDao;

    @Override
    public String execute() {
        productos = productoDao.query("ProductosRelacionados", "producto", productoDao.find(productoId));
        return SUCCESS;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

}
