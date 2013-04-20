/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.crud;

import com.google.common.collect.ImmutableList;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.PresentableAndClasificableState;
import com.palermotenis.model.beans.productos.tipos.PresentableState;
import com.palermotenis.model.beans.productos.tipos.State;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;

/**
 *
 * @author Poly
 */
public class PresentacionAction extends ActionSupport {

    private final String SHOW = "show";
    private final String JSON = "json";
    private Integer tipoPresentacionId;
    private Integer presentacionId;
    private Double cantidad;
    private String unidad;
    private String nombre;
    private GenericDao<TipoPresentacion, Integer> tipoPresentacionService;
    private GenericDao<Presentacion, Integer> presentacionService;
    private GenericDao<Stock, Integer> stockService;
    private GenericDao<Sucursal, Integer> sucursalService;
    private GenericDao<ValorClasificatorio, Integer> valorClasificatorioService;
    private Collection<Presentacion> presentaciones;
    private InputStream inputStream;

    public String show() {
        TipoPresentacion tipo = tipoPresentacionService.find(tipoPresentacionId);
        presentaciones = presentacionService.queryBy("Tipo", "tipo", tipo);
        return SHOW;
    }

    public String create() {

        Presentacion presentacion = new Presentacion();
        TipoPresentacion tipo = tipoPresentacionService.find(tipoPresentacionId);
        presentacion.setCantidad(cantidad);
        presentacion.setTipoPresentacion(tipo);
        presentacion.setUnidad(unidad);

        if (nombre.isEmpty()) {
            nombre = null;
        }
        presentacion.setNombre(nombre);

        presentacionService.create(presentacion);

        TipoProducto tp = tipo.getTipoProducto();

        if (tp.isPresentable()) {
            for (Producto p : tp.getProductos()) {
                //Es la primera presentación que sea crea y ya se habian creado stocks anteriormente.                
                if (tipo.getPresentaciones().size() == 1 && p.getStocks() != null && !p.getStocks().isEmpty()) {
                    for (Stock stock : p.getStocks()) {
                        if (stock.getPresentacion() == null) {
                            stock.setPresentacion(presentacion);
                            stockService.edit(stock);
                        }
                    }
                } else {
                    List<Sucursal> sucursales = sucursalService.findAll();
                    List<Presentacion> pr = new ImmutableList.Builder<Presentacion>().add(presentacion).build();
                    State state = new PresentableState(p, sucursales, pr);
                    if (tp.isClasificable()) {
                        List<ValorClasificatorio> vcl = valorClasificatorioService.queryBy("TipoAtributoList", "tipoAtributoList", tp.getTiposAtributoClasificatorios());
                        state = new PresentableAndClasificableState(p, sucursales, vcl, pr);
                    }
                    state.setStockService(stockService);
                    state.createStock();
                }
            }
        }

        inputStream = StringUtility.getInputString("OK");

        return JSON;
    }

    public String edit() {
        try {
            Presentacion presentacion = presentacionService.find(presentacionId);
            presentacion.setCantidad(cantidad);
            presentacion.setNombre(nombre);
            presentacion.setUnidad(unidad);

            presentacionService.edit(presentacion);

            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(PresentacionAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(PresentacionAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getMessage());
        }
        return JSON;
    }

    public String destroy() {
        try {
            Presentacion presentacion = presentacionService.find(presentacionId);
            for(Stock stock : presentacion.getStocks()){
                if(presentacion.getTipoPresentacion().getPresentaciones().size() == 1){ //Es la única presentación
                    stock.setPresentacion(null);
                    stockService.edit(stock);
                } else {
                    stockService.destroy(stock);
                }
            }            
            presentacionService.destroy(presentacion);
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(PresentacionAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getMessage());
        }
        return JSON;
    }

    /**
     * @param tipoPresentacionId the tipoPresentacionId to set
     */
    public void setTipoPresentacionId(Integer tipoPresentacionId) {
        this.tipoPresentacionId = tipoPresentacionId;
    }

    /**
     * @param presentacionId the presentacionId to set
     */
    public void setPresentacionId(Integer presentacionId) {
        this.presentacionId = presentacionId;
    }

    /**
     * @param presentacionService the presentacionService to set
     */
    public void setPresentacionService(GenericDao<Presentacion, Integer> presentacionService) {
        this.presentacionService = presentacionService;
    }

    /**
     * @param tipoPresentacionService the tipoPresentacionService to set
     */
    public void setTipoPresentacionService(GenericDao<TipoPresentacion, Integer> tipoPresentacionService) {
        this.tipoPresentacionService = tipoPresentacionService;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    /**
     * @param nombre the nombre to set
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

    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param stockService the stockService to set
     */
    public void setStockService(GenericDao<Stock, Integer> stockService) {
        this.stockService = stockService;
    }

    public void setSucursalService(GenericDao<Sucursal, Integer> sucursalService) {
        this.sucursalService = sucursalService;
    }

    public void setValorClasificatorioService(GenericDao<ValorClasificatorio, Integer> valorClasificatorioService) {
        this.valorClasificatorioService = valorClasificatorioService;
    }
}
