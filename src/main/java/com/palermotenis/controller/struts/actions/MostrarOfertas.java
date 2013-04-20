/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.controller.struts.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.productos.Producto;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Poly
 */
public class MostrarOfertas extends ActionSupport {

    private GenericDao<Producto, Integer> productoService;
    private List<Producto> productos;

    @Override
    public String execute(){
        productos = productoService.query("Ofertas");        
        Collections.sort(productos, new Comparator<Producto>(){

            public int compare(Producto p1, Producto p2) {
                return getOrden(p1).compareTo(getOrden(p2));
            }

        });
        return SUCCESS;
    }

    private Integer getOrden(Producto producto){
        Collection<? extends Precio> precios = producto.getPrecios();
        for(Precio p : precios){
            if(p.isEnOferta()){
                return p.getOrden();
            }
        }
        return null;
    }

    @Autowired
    public void setProductoService(GenericDao<Producto, Integer> productoService) {
        this.productoService = productoService;
    }

    /**
     * @return the productos
     */
    public Collection<Producto> getProductos() {
        return productos;
    }

}
