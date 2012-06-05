/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.productos.Producto;

/**
 *
 * @author Poly
 */
public class GetProducto extends ActionSupport {

    private GenericDao<Producto, Integer> productoService;

    private Integer productoId;
    private Producto producto;

    private String redirectPago;

    @Override
    public String execute() {
        producto = productoService.find(productoId);        
        return SUCCESS;
    }

    /**
     * @param productoService the productoService to set
     */
    public void setProductoService(GenericDao<Producto, Integer> productoService) {
        this.productoService = productoService;
    }

    /**
     * @param productoId the productoId to set
     */
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    /**
     * @return the producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * @return the redirect
     */
    public String getRedirectPage() {
        return redirectPago;
    }

    /**
     * @param redirect the redirect to set
     */
    public void setRedirectPage(String redirectPage) {
        this.redirectPago = redirectPage;
    }
}
