package com.palermotenis.controller.struts.actions.admin.crud;

import java.io.InputStream;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.compras.Costo;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.proveedores.Proveedor;
import com.palermotenis.util.StringUtility;

/**
 * 
 * @author Poly
 */
public class CostoAction extends ActionSupport {

    private static final long serialVersionUID = -4981608712701371254L;

    private final String SHOW = "show";
    private final String JSON = "json";

    private Collection<Costo> costos;
    private Integer costoId;
    private Integer presentacionId;
    private Integer productoId;
    private Integer proveedorId;
    private Integer monedaId;
    private Double costoVal;

    private InputStream inputStream;
    private Producto producto;

    @Autowired
    private GenericDao<Presentacion, Integer> presentacionDao;

    @Autowired
    private GenericDao<Costo, Integer> costoDao;

    @Autowired
    private GenericDao<Proveedor, Integer> proveedorDao;

    @Autowired
    private GenericDao<Producto, Integer> productoDao;

    @Autowired
    private GenericDao<Moneda, Integer> monedaDao;

    public String show() {
        producto = productoDao.find(productoId);
        costos = getProducto().getCostos();
        return SHOW;
    }

    public String create() {
        Producto prod = productoDao.find(productoId);
        Moneda moneda = monedaDao.find(monedaId);
        Proveedor proveedor = proveedorDao.find(proveedorId);

        Costo costo = null;
        if (prod.getTipoProducto().isPresentable()) {
            Presentacion presentacion = presentacionDao.find(presentacionId);
            costo = new Costo(costoVal, prod, presentacion, proveedor, moneda);
        } else {
            costo = new Costo(costoVal, prod, proveedor, moneda);
        }

        costoDao.create(costo);

        inputStream = StringUtility.getInputString("OK");
        return JSON;
    }

    public String edit() {
        try {
            Costo costo = costoDao.find(costoId);
            Moneda moneda = monedaDao.find(monedaId);
            Proveedor proveedor = proveedorDao.find(proveedorId);
            Producto prod = productoDao.find(productoId);

            if (prod.getTipoProducto().isPresentable()) {
                Presentacion presentacion = presentacionDao.find(presentacionId);
                costo.setPresentacion(presentacion);
            }

            costo.setProducto(prod);
            costo.setProveedor(proveedor);
            costo.setMoneda(moneda);
            costo.setCosto(costoVal);

            costoDao.edit(costo);
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
            costoDao.destroy(costoDao.find(costoId));
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(CostoAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    /**
     * @return the costos
     */
    public Collection<Costo> getCostos() {
        return costos;
    }

    /**
     * @param costoId
     *            the costoId to set
     */
    public void setCostoId(Integer costoId) {
        this.costoId = costoId;
    }

    /**
     * @param productoId
     *            the productoId to set
     */
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    /**
     * @param proveedorId
     *            the proveedorId to set
     */
    public void setProveedorId(Integer proveedorId) {
        this.proveedorId = proveedorId;
    }

    /**
     * @param monedaId
     *            the monedaId to set
     */
    public void setMonedaId(Integer monedaId) {
        this.monedaId = monedaId;
    }

    /**
     * @param costoVal
     *            the costoVal to set
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
     * @param presentacionId
     *            the presentacionId to set
     */
    public void setPresentacionId(Integer presentacionId) {
        this.presentacionId = presentacionId;
    }

}
