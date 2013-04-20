/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.controller.struts.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.productos.Producto;
import java.util.Collection;

/**
 *
 * @author Poly
 */
public class PrepararPrecios extends ActionSupport {

    private Integer productoId;

    private GenericDao<Producto, Integer> productoService;
    private GenericDao<Pago, Integer> pagoService;
    private GenericDao<Moneda, Integer> monedaService;

    private Producto producto;
    private Collection<Moneda> monedas;
    private Collection<Pago> pagos;

    private String redirectPage;

    @Override
    public String execute(){

        producto = productoService.find(productoId);
        monedas = monedaService.findAll();
        pagos = pagoService.findAll();

        return SUCCESS;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public void setProductoService(GenericDao<Producto, Integer> productoService) {
        this.productoService = productoService;
    }

    public void setPagoService(GenericDao<Pago, Integer> pagoService) {
        this.pagoService = pagoService;
    }

    public void setMonedaService(GenericDao<Moneda, Integer> monedaService) {
        this.monedaService = monedaService;
    }


    public Producto getProducto() {
        return producto;
    }

    public String getRedirectPage() {
        return redirectPage;
    }

    public void setRedirectPage(String redirectPage) {
        this.redirectPage = redirectPage;
    }

    public Collection<Moneda> getMonedas() {
        return monedas;
    }

    public Collection<Pago> getPagos() {
        return pagos;
    }

}
