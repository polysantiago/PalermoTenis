package com.palermotenis.controller.struts.actions;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.dao.Dao;

/**
 * 
 * @author Poly
 */
public class GetProducto extends ActionSupport {

    private static final long serialVersionUID = -5926960147665282516L;

    private Producto producto;

    private Integer productoId;

    private String redirectPago;

    @Autowired
    private Dao<Producto, Integer> productoDao;

    @Override
    public String execute() {
        producto = productoDao.find(productoId);
        return SUCCESS;
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
     * @param productoId
     *            the productoId to set
     */
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    /**
     * @param redirect
     *            the redirect to set
     */
    public void setRedirectPage(String redirectPage) {
        this.redirectPago = redirectPage;
    }
}
