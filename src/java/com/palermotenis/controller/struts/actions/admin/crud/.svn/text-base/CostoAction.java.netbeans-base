/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.controller.struts.actions.admin.crud;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.compras.Costo;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.proveedores.Proveedor;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;

/**
 *
 * @author Poly
 */
public class CostoAction extends ActionSupport {

    private final String SHOW = "show";
    private final String JSON = "json";

    private GenericDao<Presentacion, Integer> presentacionService;
    private GenericDao<Costo, Integer> costoService;
    private GenericDao<Proveedor, Integer> proveedorService;
    private GenericDao<Producto, Integer> productoService;
    private GenericDao<Moneda, Integer> monedaService;

    private Collection<Costo> costos;
    private Integer costoId;
    private Integer presentacionId;
    private Integer productoId;
    private Integer proveedorId;
    private Integer monedaId;
    private Double costoVal;

    private InputStream inputStream;
    private Producto producto;


    public String show() {
        producto = productoService.find(productoId);
        costos = getProducto().getCostos();
        return SHOW;
    }

    public String create() {
        Producto prod = productoService.find(productoId);
        Moneda moneda = monedaService.find(monedaId);
        Proveedor proveedor = proveedorService.find(proveedorId);

        Costo costo = null;
        if(prod.getTipoProducto().isPresentable()){
            Presentacion presentacion = presentacionService.find(presentacionId);
            costo = new Costo(costoVal, prod, presentacion, proveedor, moneda);
        } else {
            costo = new Costo(costoVal, prod, proveedor, moneda);
        }

        costoService.create(costo);

        inputStream = StringUtility.getInputString("OK");
        return JSON;
    }

    public String edit() {
        try {
            Costo costo = costoService.find(costoId);
            Moneda moneda = monedaService.find(monedaId);
            Proveedor proveedor = proveedorService.find(proveedorId);
            Producto prod = productoService.find(productoId);

            if(prod.getTipoProducto().isPresentable()){
               Presentacion presentacion = presentacionService.find(presentacionId);
               costo.setPresentacion(presentacion);
            }

            costo.setProducto(prod);
            costo.setProveedor(proveedor);
            costo.setMoneda(moneda);
            costo.setCosto(costoVal);

            costoService.edit(costo);
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(CostoAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        } catch (Exception ex) {
            Logger.getLogger(CostoAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    public String destroy() {
        try {
            costoService.destroy(costoService.find(costoId));
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(CostoAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    /**
     * @param costoService the costosService to set
     */
    public void setCostoService(GenericDao<Costo, Integer> costoService) {
        this.costoService = costoService;
    }

    /**
     * @param proveedorService the proveedorService to set
     */
    public void setProveedorService(GenericDao<Proveedor, Integer> proveedorService) {
        this.proveedorService = proveedorService;
    }

    /**
     * @param productoService the productoService to set
     */
    public void setProductoService(GenericDao<Producto, Integer> productoService) {
        this.productoService = productoService;
    }

    /**
     * @param monedaService the monedasService to set
     */
    public void setMonedaService(GenericDao<Moneda, Integer> monedaService) {
        this.monedaService = monedaService;
    }

    /**
     * @return the costos
     */
    public Collection<Costo> getCostos() {
        return costos;
    }

    /**
     * @param costoId the costoId to set
     */
    public void setCostoId(Integer costoId) {
        this.costoId = costoId;
    }

    /**
     * @param productoId the productoId to set
     */
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    /**
     * @param proveedorId the proveedorId to set
     */
    public void setProveedorId(Integer proveedorId) {
        this.proveedorId = proveedorId;
    }

    /**
     * @param monedaId the monedaId to set
     */
    public void setMonedaId(Integer monedaId) {
        this.monedaId = monedaId;
    }

    /**
     * @param costoVal the costoVal to set
     */
    public void setCostoVal(Double costoVal) {
        this.costoVal = costoVal;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @return the producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * @param presentacionService the presentacionService to set
     */
    public void setPresentacionService(GenericDao<Presentacion, Integer> presentacionService) {
        this.presentacionService = presentacionService;
    }

    /**
     * @param presentacionId the presentacionId to set
     */
    public void setPresentacionId(Integer presentacionId) {
        this.presentacionId = presentacionId;
    }

}
