package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.Collection;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.model.beans.compras.Costo;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.service.costos.CostoService;
import com.palermotenis.model.service.productos.ProductoService;

public class CostoAction extends InputStreamActionSupport {

    private static final long serialVersionUID = -4981608712701371254L;

    private final String SHOW = "show";
    private Collection<Costo> costos;
    private Integer costoId;
    private Integer presentacionId;
    private Integer productoId;
    private Integer proveedorId;
    private Integer monedaId;
    private Double costoVal;

    private Producto producto;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CostoService costoService;

    public String show() {
        producto = getProductoById();
        costos = getProducto().getCostos();
        return SHOW;
    }

    public String create() {
        costoService.createCosto(costoVal, productoId, monedaId, proveedorId, presentacionId);
        success();
        return STREAM;
    }

    public String edit() {
        try {
            costoService.updateCosto(costoId, costoVal, productoId, monedaId, proveedorId, presentacionId);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        } catch (Exception ex) {
            failure(ex);
        }
        return STREAM;
    }

    public String destroy() {
        try {
            costoService.deleteCosto(costoId);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return STREAM;
    }

    private Producto getProductoById() {
        return productoService.getProductById(productoId);
    }

    public Collection<Costo> getCostos() {
        return costos;
    }

    public void setCostoId(Integer costoId) {
        this.costoId = costoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public void setProveedorId(Integer proveedorId) {
        this.proveedorId = proveedorId;
    }

    public void setMonedaId(Integer monedaId) {
        this.monedaId = monedaId;
    }

    public void setCostoVal(Double costoVal) {
        this.costoVal = costoVal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setPresentacionId(Integer presentacionId) {
        this.presentacionId = presentacionId;
    }

}
