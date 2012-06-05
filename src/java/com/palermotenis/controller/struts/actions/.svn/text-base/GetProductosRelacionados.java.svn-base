/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.controller.struts.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.productos.Producto;
import java.util.List;

/**
 *
 * @author poly
 */
public class GetProductosRelacionados extends ActionSupport {

    private Integer productoId;
    private GenericDao<Producto, Integer> productoService;    
    private List<Producto> productos;

    @Override
    public String execute(){
        productos = productoService.query("ProductosRelacionados", "producto", productoService.find(productoId));
        return SUCCESS;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public void setProductoService(GenericDao<Producto, Integer> productoService) {
        this.productoService = productoService;
    }
}
