/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.controller.struts.actions.JsonActionSupport;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.PresentableAndClasificableState;
import com.palermotenis.model.beans.productos.tipos.PresentableState;
import com.palermotenis.model.beans.productos.tipos.State;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;

/**
 * 
 * @author Poly
 */
public class PresentacionAction extends JsonActionSupport {

    private static final long serialVersionUID = -1202633900380924622L;

    private final String SHOW = "show";

    private Integer tipoPresentacionId;
    private Integer presentacionId;
    private Double cantidad;
    private String unidad;
    private String nombre;

    private Collection<Presentacion> presentaciones;

    @Autowired
    private GenericDao<TipoPresentacion, Integer> tipoPresentacionDao;

    @Autowired
    private GenericDao<Presentacion, Integer> presentacionDao;

    @Autowired
    private GenericDao<Stock, Integer> stockDao;

    @Autowired
    private GenericDao<Sucursal, Integer> sucursalDao;

    @Autowired
    private GenericDao<ValorClasificatorio, Integer> valorClasificatorioDao;

    public String show() {
        TipoPresentacion tipo = tipoPresentacionDao.find(tipoPresentacionId);
        presentaciones = presentacionDao.queryBy("Tipo", "tipo", tipo);
        return SHOW;
    }

    public String create() {

        Presentacion presentacion = new Presentacion();
        TipoPresentacion tipo = tipoPresentacionDao.find(tipoPresentacionId);
        presentacion.setCantidad(cantidad);
        presentacion.setTipoPresentacion(tipo);
        presentacion.setUnidad(unidad);

        if (nombre.isEmpty()) {
            nombre = null;
        }
        presentacion.setNombre(nombre);

        presentacionDao.create(presentacion);

        TipoProducto tp = tipo.getTipoProducto();

        if (tp.isPresentable()) {
            for (Producto p : tp.getProductos()) {
                // Es la primera presentación que sea crea y ya se habian creado stocks anteriormente.
                if (tipo.getPresentaciones().size() == 1 && p.getStocks() != null && !p.getStocks().isEmpty()) {
                    for (Stock stock : p.getStocks()) {
                        if (stock.getPresentacion() == null) {
                            stock.setPresentacion(presentacion);
                            stockDao.edit(stock);
                        }
                    }
                } else {
                    List<Sucursal> sucursales = sucursalDao.findAll();
                    List<Presentacion> pr = new ImmutableList.Builder<Presentacion>().add(presentacion).build();
                    State state = new PresentableState(p, sucursales, pr);
                    if (tp.isClasificable()) {
                        List<ValorClasificatorio> vcl = valorClasificatorioDao.queryBy("TipoAtributoList",
                            "tipoAtributoList", tp.getTiposAtributoClasificatorios());
                        state = new PresentableAndClasificableState(p, sucursales, vcl, pr);
                    }
                    state.setStockDao(stockDao);
                    state.createStock();
                }
            }
        }

        success();

        return JSON;
    }

    public String edit() {
        try {
            Presentacion presentacion = presentacionDao.find(presentacionId);
            presentacion.setCantidad(cantidad);
            presentacion.setNombre(nombre);
            presentacion.setUnidad(unidad);

            presentacionDao.edit(presentacion);

            success();
        } catch (HibernateException ex) {
            failure(ex);
        } catch (Exception ex) {
            failure(ex);
        }
        return JSON;
    }

    public String destroy() {
        try {
            Presentacion presentacion = presentacionDao.find(presentacionId);
            for (Stock stock : presentacion.getStocks()) {
                if (presentacion.getTipoPresentacion().getPresentaciones().size() == 1) { // Es la única presentación
                    stock.setPresentacion(null);
                    stockDao.edit(stock);
                } else {
                    stockDao.destroy(stock);
                }
            }
            presentacionDao.destroy(presentacion);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
    }

    /**
     * @param tipoPresentacionId
     *            the tipoPresentacionId to set
     */
    public void setTipoPresentacionId(Integer tipoPresentacionId) {
        this.tipoPresentacionId = tipoPresentacionId;
    }

    /**
     * @param presentacionId
     *            the presentacionId to set
     */
    public void setPresentacionId(Integer presentacionId) {
        this.presentacionId = presentacionId;
    }

    /**
     * @param cantidad
     *            the cantidad to set
     */
    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @param unidad
     *            the unidad to set
     */
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the presentaciones
     */
    public Collection<Presentacion> getPresentaciones() {
        return presentaciones;
    }

}
