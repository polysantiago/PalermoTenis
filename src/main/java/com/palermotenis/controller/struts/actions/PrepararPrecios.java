package com.palermotenis.controller.struts.actions;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.productos.Producto;

/**
 * 
 * @author Poly
 */
public class PrepararPrecios extends ActionSupport {

    private static final long serialVersionUID = 1747802129269131653L;

    private Integer productoId;

    private Producto producto;

    private Collection<Moneda> monedas;
    private Collection<Pago> pagos;

    private String redirectPage;

    @Autowired
    private GenericDao<Producto, Integer> productoDao;

    @Autowired
    private GenericDao<Pago, Integer> pagoDao;

    @Autowired
    private GenericDao<Moneda, Integer> monedaDao;

    @Override
    public String execute() {

        producto = productoDao.find(productoId);
        monedas = monedaDao.findAll();
        pagos = pagoDao.findAll();

        return SUCCESS;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
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
