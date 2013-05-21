package com.palermotenis.controller.struts.actions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.service.monedas.MonedaService;
import com.palermotenis.model.service.pagos.PagoService;
import com.palermotenis.model.service.productos.ProductoService;

public class PrepararPrecios extends ActionSupport {

    private static final long serialVersionUID = 1747802129269131653L;

    private Integer productoId;

    private Producto producto;

    private List<Moneda> monedas;
    private List<Pago> pagos;

    private String redirectPage;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PagoService pagoService;

    @Autowired
    private MonedaService monedaService;

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public Producto getProducto() {
        if (producto == null) {
            producto = productoService.getProductById(productoId);
        }
        return producto;
    }

    public String getRedirectPage() {
        return redirectPage;
    }

    public void setRedirectPage(String redirectPage) {
        this.redirectPage = redirectPage;
    }

    public List<Moneda> getMonedas() {
        if (monedas == null) {
            monedas = monedaService.getAllMonedas();
        }
        return monedas;
    }

    public List<Pago> getPagos() {
        if (pagos == null) {
            pagos = pagoService.getAllPagos();
        }
        return pagos;
    }

}
