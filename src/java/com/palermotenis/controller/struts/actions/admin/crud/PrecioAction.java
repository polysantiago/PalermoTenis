/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.crud;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.precios.PrecioPresentacion;
import com.palermotenis.model.beans.precios.PrecioUnidad;
import com.palermotenis.model.beans.precios.pks.PrecioPresentacionPK;
import com.palermotenis.model.beans.precios.pks.PrecioProductoPK;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.HibernateException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author Poly
 */
public class PrecioAction extends ActionSupport {

    private static final String JSON = "json";
    private Integer productoId;
    private Integer newPagoId;
    private Integer newMonedaId;
    private Integer newPresentacionId;
    private Integer newCuotas;
    private Double valor;
    private boolean enOferta;
    private Double valorOferta;
    private Integer pagoId;
    private Integer monedaId;
    private Integer presentacionId;
    private Integer cuotas;
    private Integer productoOrden;
    private Integer productoRgtId;
    private Integer productoRgtOrden;
    private GenericDao<PrecioPresentacion, PrecioPresentacionPK> precioPresentacionService;
    private GenericDao<PrecioUnidad, PrecioProductoPK> precioUnidadService;
    private GenericDao<Producto, Integer> productoService;
    private GenericDao<Pago, Integer> pagoService;
    private GenericDao<Moneda, Integer> monedaService;
    private GenericDao<Presentacion, Integer> presentacionService;
    private EntityManager entityManager;
    private PlatformTransactionManager transactionManager;
    private InputStream inputStream;

    public String create() {
        if (isPresentable(productoId)) {
            createByPresentacion();
        } else {
            createByUnidad();
        }
        return JSON;
    }

    public String edit() {
        if (isPresentable(productoId)) {
            editByPresentacion();
        } else {
            editByUnidad();
        }
        return JSON;
    }

    public String destroy() {
        if (isPresentable(productoId)) {
            destroyByPresentacion();
        } else {
            destroyByUnidad();
        }
        return JSON;
    }

    public String move() {
        try {
            TransactionTemplate tt = new TransactionTemplate(transactionManager);
            tt.execute(new TransactionCallbackWithoutResult() {

                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    entityManager.createNativeQuery("{call moverOferta(?,?,?,?)}").setParameter(1, productoId).setParameter(2, productoOrden).setParameter(3, productoRgtId).setParameter(4, productoRgtOrden).executeUpdate();
                }
            });
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(PrecioAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getMessage());
        }
        return JSON;
    }

    private boolean isPresentable(Integer productoId) {
        Producto producto = productoService.find(productoId);
        return producto.getTipoProducto().isPresentable();
    }

    private void createByPresentacion() {
        try {
            Producto producto = productoService.find(productoId);
            Pago pago = pagoService.find(pagoId);
            Moneda moneda = monedaService.find(monedaId);
            Presentacion presentacion = presentacionService.find(presentacionId);

            if (cuotas == null) {
                setCuotas((Integer) 1);
            }

            PrecioPresentacionPK pk = new PrecioPresentacionPK(moneda, pago, producto, presentacion, cuotas);

            if (precioPresentacionService.find(pk) != null) {
                inputStream = StringUtility.getInputString("Ya existe un precio con esas características");
            } else {

                PrecioPresentacion precio = new PrecioPresentacion(pk, valor);
                precio.setEnOferta(enOferta);
                if (enOferta) {
                    precio.setOrden(productoId);
                }
                precio.setValorOferta(valorOferta);
                precioPresentacionService.create(precio);
                inputStream = StringUtility.getInputString("OK");
            }
        } catch (HibernateException ex) {
            Logger.getLogger(PrecioAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(PrecioAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getMessage());
        }
    }

    private void createByUnidad() {
        try {
            Producto producto = productoService.find(productoId);
            Pago pago = pagoService.find(pagoId);
            Moneda moneda = monedaService.find(monedaId);

            if (cuotas == null) {
                setCuotas((Integer) 1);
            }

            PrecioProductoPK pk = new PrecioProductoPK(moneda, pago, producto, cuotas);

            if (precioUnidadService.find(pk) != null) {
                inputStream = StringUtility.getInputString("Ya existe un precio con esas características");
            } else {
                PrecioUnidad precio = new PrecioUnidad(pk, valor);
                precio.setEnOferta(enOferta);
                if (enOferta) {
                    precio.setOrden(productoId);
                }
                precio.setValorOferta(valorOferta);
                precioUnidadService.create(precio);
                inputStream = StringUtility.getInputString("OK");
            }
        } catch (HibernateException ex) {
            Logger.getLogger(PrecioAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(PrecioAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getMessage());
        }
    }

    private void editByPresentacion() {
        try {
            Producto producto = productoService.find(productoId);
            Pago pago = pagoService.find(pagoId);
            Moneda moneda = monedaService.find(monedaId);
            Presentacion presentacion = presentacionService.find(presentacionId);

            Pago newPago = pagoService.find(newPagoId);
            Moneda newMoneda = monedaService.find(newMonedaId);
            Presentacion newPresentacion = presentacionService.find(newPresentacionId);

            PrecioPresentacionPK pk = new PrecioPresentacionPK(moneda, pago, producto, presentacion, cuotas); //precioAnterior
            PrecioPresentacion precioPresentacion = precioPresentacionService.find(pk);

            PrecioPresentacionPK newPK = new PrecioPresentacionPK(newMoneda, newPago, producto, newPresentacion, newCuotas); //nuevoPrecio
            PrecioPresentacion newPrecioPresentacion = precioPresentacionService.find(newPK);


            if (pk.equals(newPK)) {
                precioPresentacion.setValor(valor);
                precioPresentacion.setEnOferta(enOferta);
                if (enOferta && precioPresentacion.getOrden() == 0) {
                    precioPresentacion.setOrden(productoId);
                }
                precioPresentacion.setValorOferta(valorOferta);
                if (precioPresentacion.getOrden() == null) {
                    precioPresentacion.setOrden(producto.getId());
                }
                precioPresentacionService.edit(precioPresentacion);
            } else {
                if (newPrecioPresentacion != null) {
                    inputStream = StringUtility.getInputString("Ya existe un precio con esas características");
                    return;
                } else {
                    precioPresentacionService.destroy(precioPresentacion);

                    PrecioPresentacion precio = new PrecioPresentacion();

                    precio.setValor(valor);
                    precioPresentacion.setEnOferta(enOferta);
                    precioPresentacion.setValorOferta(valorOferta);
                    precio.setId(newPK);

                    precioPresentacionService.create(precio);
                }
            }

            inputStream = StringUtility.getInputString("OK");
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(PrecioAction.class.getName()).log(Level.WARNING, null, ex);
            inputStream = StringUtility.getInputString(ex.getMessage());
        } catch (HibernateException ex) {
            Logger.getLogger(PrecioAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(PrecioAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getMessage());
        }
    }

    private void editByUnidad() {
        try {
            Producto producto = productoService.find(productoId);
            Pago pago = pagoService.find(pagoId);
            Moneda moneda = monedaService.find(monedaId);

            Pago newPago = pagoService.find(newPagoId);
            Moneda newMoneda = monedaService.find(newMonedaId);

            PrecioProductoPK pk = new PrecioProductoPK(moneda, pago, producto, cuotas); //precioUnidadAnterior
            PrecioUnidad precioUnidad = precioUnidadService.find(pk);

            PrecioProductoPK newPK = new PrecioProductoPK(newMoneda, newPago, producto, newCuotas); //nuevoPrecioUnidad
            PrecioUnidad newPrecioUnidad = precioUnidadService.find(newPK);


            if (pk.equals(newPK)) {
                precioUnidad.setValor(valor);
                precioUnidad.setEnOferta(enOferta);
                if (enOferta && precioUnidad.getOrden() == 0) {
                    precioUnidad.setOrden(productoId);
                }
                precioUnidad.setValorOferta(valorOferta);
                if (precioUnidad.getOrden() == null) {
                    precioUnidad.setOrden(producto.getId());
                }
                precioUnidadService.edit(precioUnidad);
            } else {
                if (newPrecioUnidad != null) {
                    inputStream = StringUtility.getInputString("Ya existe un precio con esas características");
                    return;
                } else {
                    precioUnidadService.destroy(precioUnidad);

                    PrecioUnidad precio = new PrecioUnidad();

                    precio.setValor(valor);
                    precioUnidad.setEnOferta(enOferta);
                    precioUnidad.setValorOferta(valorOferta);
                    precio.setId(newPK);

                    precioUnidadService.create(precio);
                }
            }

            inputStream = StringUtility.getInputString("OK");
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(PrecioAction.class.getName()).log(Level.WARNING, null, ex);
            inputStream = StringUtility.getInputString(ex.getMessage());
        } catch (HibernateException ex) {
            Logger.getLogger(PrecioAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(PrecioAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getMessage());
        }
    }

    private void destroyByPresentacion() {
        try {
            Moneda moneda = monedaService.find(monedaId);
            Pago pago = pagoService.find(pagoId);
            Producto producto = productoService.find(productoId);
            Presentacion presentacion = presentacionService.find(presentacionId);

            PrecioPresentacionPK pk = new PrecioPresentacionPK(moneda, pago, producto, presentacion, cuotas);
            precioPresentacionService.destroy(precioPresentacionService.find(pk));

            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(PrecioAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getMessage());
        }
    }

    private void destroyByUnidad() {
        try {
            Moneda moneda = monedaService.find(monedaId);
            Pago pago = pagoService.find(pagoId);
            Producto producto = productoService.find(productoId);

            PrecioProductoPK pk = new PrecioProductoPK(moneda, pago, producto, cuotas);
            precioUnidadService.destroy(precioUnidadService.find(pk));

            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(PrecioAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getMessage());
        }
    }

    /**
     * @param productoId the productoId to set
     */
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    /**
     * @param newPagoId the newPagoId to set
     */
    public void setNewPagoId(Integer newPagoId) {
        this.newPagoId = newPagoId;
    }

    /**
     * @param newMonedaId the newMonedaId to set
     */
    public void setNewMonedaId(Integer newMonedaId) {
        this.newMonedaId = newMonedaId;
    }

    /**
     * @param newPresentacionId the newPresentacionId to set
     */
    public void setNewPresentacionId(Integer newPresentacionId) {
        this.newPresentacionId = newPresentacionId;
    }

    /**
     * @param newCuotas the newCuotas to set
     */
    public void setNewCuotas(Integer newCuotas) {
        this.newCuotas = newCuotas;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Double valor) {
        this.valor = valor;
    }

    /**
     * @param enOferta the enOferta to set
     */
    public void setEnOferta(boolean enOferta) {
        this.enOferta = enOferta;
    }

    /**
     * @param valorOferta the valorOferta to set
     */
    public void setValorOferta(Double valorOferta) {
        this.valorOferta = valorOferta;
    }

    /**
     * @param pagoId the pagoId to set
     */
    public void setPagoId(Integer pagoId) {
        this.pagoId = pagoId;
    }

    /**
     * @param monedaId the monedaId to set
     */
    public void setMonedaId(Integer monedaId) {
        this.monedaId = monedaId;
    }

    /**
     * @param presentacionId the presentacionId to set
     */
    public void setPresentacionId(Integer presentacionId) {
        this.presentacionId = presentacionId;
    }

    /**
     * @param cuotas the cuotas to set
     */
    public void setCuotas(Integer cuotas) {
        this.cuotas = cuotas;
    }

    public void setProductoOrden(Integer productoOrden) {
        this.productoOrden = productoOrden;
    }

    public void setProductoRgtId(Integer productoRgtId) {
        this.productoRgtId = productoRgtId;
    }

    public void setProductoRgtOrden(Integer productoRgtOrden) {
        this.productoRgtOrden = productoRgtOrden;
    }

    /**
     * @param precioPresentacionService the precioPresentacionService to set
     */
    public void setPrecioPresentacionService(GenericDao<PrecioPresentacion, PrecioPresentacionPK> precioPresentacionService) {
        this.precioPresentacionService = precioPresentacionService;
    }

    /**
     * @param precioUnidadService the preciosUnidadService to set
     */
    public void setPrecioUnidadService(GenericDao<PrecioUnidad, PrecioProductoPK> precioUnidadService) {
        this.precioUnidadService = precioUnidadService;
    }

    /**
     * @param productoService the productoService to set
     */
    public void setProductoService(GenericDao<Producto, Integer> productoService) {
        this.productoService = productoService;
    }

    /**
     * @param pagoService the pagosService to set
     */
    public void setPagoService(GenericDao<Pago, Integer> pagoService) {
        this.pagoService = pagoService;
    }

    /**
     * @param monedaService the monedasService to set
     */
    public void setMonedaService(GenericDao<Moneda, Integer> monedaService) {
        this.monedaService = monedaService;
    }

    /**
     * @param presentacionService the presentacionService to set
     */
    public void setPresentacionService(GenericDao<Presentacion, Integer> presentacionService) {
        this.presentacionService = presentacionService;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setJtaTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
