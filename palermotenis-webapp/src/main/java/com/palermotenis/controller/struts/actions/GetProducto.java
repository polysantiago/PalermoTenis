package com.palermotenis.controller.struts.actions;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.service.productos.ProductoService;

public class GetProducto extends ActionSupport {

    private static final long serialVersionUID = -5926960147665282516L;

    private Producto producto;

    private Integer productoId;

    private String redirectPago;

    @Autowired
    private ProductoService productoService;

    @Override
    public String execute() {
        return SUCCESS;
    }

    public Producto getProducto() {
        if (producto == null) {
            producto = productoService.getProductById(productoId);
        }
        return producto;
    }

    public String getRedirectPage() {
        return redirectPago;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public void setRedirectPage(String redirectPage) {
        this.redirectPago = redirectPage;
    }
}
