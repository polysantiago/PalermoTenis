package com.palermotenis.controller.struts.actions.admin.crud;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.precios.PrecioPresentacion;
import com.palermotenis.model.beans.precios.PrecioUnidad;
import com.palermotenis.model.beans.precios.pks.PrecioPresentacionPK;
import com.palermotenis.model.beans.precios.pks.PrecioProductoPK;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.dao.Dao;

public class PrecioAction extends InputStreamActionSupport {

    private static final long serialVersionUID = -4112606099516303261L;

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

    private EntityManager entityManager;
    private PlatformTransactionManager transactionManager;

    @Autowired
    private Dao<PrecioPresentacion, PrecioPresentacionPK> precioPresentacionDao;

    @Autowired
    private Dao<PrecioUnidad, PrecioProductoPK> precioUnidadDao;

    @Autowired
    private Dao<Producto, Integer> productoDao;

    @Autowired
    private Dao<Pago, Integer> pagoDao;

    @Autowired
    private Dao<Moneda, Integer> monedaDao;

    @Autowired
    private Dao<Presentacion, Integer> presentacionDao;

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
                    entityManager
                        .createNativeQuery("{call moverOferta(?,?,?,?)}")
                        .setParameter(1, productoId)
                        .setParameter(2, productoOrden)
                        .setParameter(3, productoRgtId)
                        .setParameter(4, productoRgtOrden)
                        .executeUpdate();
                }
            });
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
    }

    private boolean isPresentable(Integer productoId) {
        Producto producto = productoDao.find(productoId);
        return producto.getTipoProducto().isPresentable();
    }

    private void createByPresentacion() {
        try {
            Producto producto = productoDao.find(productoId);
            Pago pago = pagoDao.find(pagoId);
            Moneda moneda = monedaDao.find(monedaId);
            Presentacion presentacion = presentacionDao.find(presentacionId);

            if (cuotas == null) {
                setCuotas(1);
            }

            PrecioPresentacionPK pk = new PrecioPresentacionPK(moneda, pago, producto, presentacion, cuotas);

            if (precioPresentacionDao.find(pk) != null) {
                writeResponse("Ya existe un precio con esas características");
            } else {
                PrecioPresentacion precio = new PrecioPresentacion(pk, valor);
                precio.setEnOferta(enOferta);
                if (enOferta) {
                    precio.setOrden(productoId);
                }
                precio.setValorOferta(valorOferta);
                precioPresentacionDao.create(precio);
                success();
            }
        } catch (HibernateException ex) {
            failure(ex);
        } catch (Exception ex) {
            failure(ex);
        }
    }

    private void createByUnidad() {
        try {
            Producto producto = productoDao.find(productoId);
            Pago pago = pagoDao.find(pagoId);
            Moneda moneda = monedaDao.find(monedaId);

            if (cuotas == null) {
                setCuotas(1);
            }

            PrecioProductoPK pk = new PrecioProductoPK(moneda, pago, producto, cuotas);

            if (precioUnidadDao.find(pk) != null) {
                writeResponse("Ya existe un precio con esas características");
            } else {
                PrecioUnidad precio = new PrecioUnidad(pk, valor);
                precio.setEnOferta(enOferta);
                if (enOferta) {
                    precio.setOrden(productoId);
                }
                precio.setValorOferta(valorOferta);
                precioUnidadDao.create(precio);
                success();
            }
        } catch (HibernateException ex) {
            failure(ex);
        } catch (Exception ex) {
            failure(ex);
        }
    }

    private void editByPresentacion() {
        try {
            Producto producto = productoDao.find(productoId);
            Pago pago = pagoDao.find(pagoId);
            Moneda moneda = monedaDao.find(monedaId);
            Presentacion presentacion = presentacionDao.find(presentacionId);

            Pago newPago = pagoDao.find(newPagoId);
            Moneda newMoneda = monedaDao.find(newMonedaId);
            Presentacion newPresentacion = presentacionDao.find(newPresentacionId);

            PrecioPresentacionPK pk = new PrecioPresentacionPK(moneda, pago, producto, presentacion, cuotas); // precioAnterior
            PrecioPresentacion precioPresentacion = precioPresentacionDao.find(pk);

            PrecioPresentacionPK newPK = new PrecioPresentacionPK(newMoneda, newPago, producto, newPresentacion,
                newCuotas); // nuevoPrecio
            PrecioPresentacion newPrecioPresentacion = precioPresentacionDao.find(newPK);

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
                precioPresentacionDao.edit(precioPresentacion);
            } else {
                if (newPrecioPresentacion != null) {
                    writeResponse("Ya existe un precio con esas características");
                } else {
                    precioPresentacionDao.destroy(precioPresentacion);

                    PrecioPresentacion precio = new PrecioPresentacion();

                    precio.setValor(valor);
                    precioPresentacion.setEnOferta(enOferta);
                    precioPresentacion.setValorOferta(valorOferta);
                    precio.setId(newPK);

                    precioPresentacionDao.create(precio);
                }
            }

            success();
        } catch (IllegalArgumentException ex) {
            failure(ex);
        } catch (HibernateException ex) {
            failure(ex);
        } catch (Exception ex) {
            failure(ex);
        }
    }

    private void editByUnidad() {
        try {
            Producto producto = productoDao.find(productoId);
            Pago pago = pagoDao.find(pagoId);
            Moneda moneda = monedaDao.find(monedaId);

            Pago newPago = pagoDao.find(newPagoId);
            Moneda newMoneda = monedaDao.find(newMonedaId);

            PrecioProductoPK pk = new PrecioProductoPK(moneda, pago, producto, cuotas); // precioUnidadAnterior
            PrecioUnidad precioUnidad = precioUnidadDao.find(pk);

            PrecioProductoPK newPK = new PrecioProductoPK(newMoneda, newPago, producto, newCuotas); // nuevoPrecioUnidad
            PrecioUnidad newPrecioUnidad = precioUnidadDao.find(newPK);

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
                precioUnidadDao.edit(precioUnidad);
            } else {
                if (newPrecioUnidad != null) {
                    writeResponse("Ya existe un precio con esas características");
                    return;
                } else {
                    precioUnidadDao.destroy(precioUnidad);

                    PrecioUnidad precio = new PrecioUnidad();

                    precio.setValor(valor);
                    precioUnidad.setEnOferta(enOferta);
                    precioUnidad.setValorOferta(valorOferta);
                    precio.setId(newPK);

                    precioUnidadDao.create(precio);
                }
            }

            success();
        } catch (IllegalArgumentException ex) {
            failure(ex);
        } catch (HibernateException ex) {
            failure(ex);
        } catch (Exception ex) {
            failure(ex);
        }
    }

    private void destroyByPresentacion() {
        try {
            Moneda moneda = monedaDao.find(monedaId);
            Pago pago = pagoDao.find(pagoId);
            Producto producto = productoDao.find(productoId);
            Presentacion presentacion = presentacionDao.find(presentacionId);

            PrecioPresentacionPK pk = new PrecioPresentacionPK(moneda, pago, producto, presentacion, cuotas);
            precioPresentacionDao.destroy(precioPresentacionDao.find(pk));

            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
    }

    private void destroyByUnidad() {
        try {
            Moneda moneda = monedaDao.find(monedaId);
            Pago pago = pagoDao.find(pagoId);
            Producto producto = productoDao.find(productoId);

            PrecioProductoPK pk = new PrecioProductoPK(moneda, pago, producto, cuotas);
            precioUnidadDao.destroy(precioUnidadDao.find(pk));

            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
    }

    /**
     * @param productoId
     *            the productoId to set
     */
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    /**
     * @param newPagoId
     *            the newPagoId to set
     */
    public void setNewPagoId(Integer newPagoId) {
        this.newPagoId = newPagoId;
    }

    /**
     * @param newMonedaId
     *            the newMonedaId to set
     */
    public void setNewMonedaId(Integer newMonedaId) {
        this.newMonedaId = newMonedaId;
    }

    /**
     * @param newPresentacionId
     *            the newPresentacionId to set
     */
    public void setNewPresentacionId(Integer newPresentacionId) {
        this.newPresentacionId = newPresentacionId;
    }

    /**
     * @param newCuotas
     *            the newCuotas to set
     */
    public void setNewCuotas(Integer newCuotas) {
        this.newCuotas = newCuotas;
    }

    /**
     * @param valor
     *            the valor to set
     */
    public void setValor(Double valor) {
        this.valor = valor;
    }

    /**
     * @param enOferta
     *            the enOferta to set
     */
    public void setEnOferta(boolean enOferta) {
        this.enOferta = enOferta;
    }

    /**
     * @param valorOferta
     *            the valorOferta to set
     */
    public void setValorOferta(Double valorOferta) {
        this.valorOferta = valorOferta;
    }

    /**
     * @param pagoId
     *            the pagoId to set
     */
    public void setPagoId(Integer pagoId) {
        this.pagoId = pagoId;
    }

    /**
     * @param monedaId
     *            the monedaId to set
     */
    public void setMonedaId(Integer monedaId) {
        this.monedaId = monedaId;
    }

    /**
     * @param presentacionId
     *            the presentacionId to set
     */
    public void setPresentacionId(Integer presentacionId) {
        this.presentacionId = presentacionId;
    }

    /**
     * @param cuotas
     *            the cuotas to set
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

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setJtaTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
