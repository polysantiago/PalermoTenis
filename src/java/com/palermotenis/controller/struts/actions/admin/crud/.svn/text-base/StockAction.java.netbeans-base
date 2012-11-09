/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.crud;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.beans.productos.tipos.ClasificableState;
import com.palermotenis.model.beans.productos.tipos.DefaultState;
import com.palermotenis.model.beans.productos.tipos.PresentableAndClasificableState;
import com.palermotenis.model.beans.productos.tipos.PresentableState;
import com.palermotenis.model.beans.productos.tipos.State;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;

/**
 *
 * @author Poly
 */
public class StockAction extends ActionSupport {

    private final String SHOW_ONE = "showOne";
    private final String SHOW_CLASF = "showByClasif";
    private final String JSON = "json";
    private GenericDao<Stock, Integer> stockService;
    private GenericDao<ValorClasificatorio, Integer> valorClasificatorioService;
    private GenericDao<Producto, Integer> productoService;
    private GenericDao<Presentacion, Integer> presentacionService;
    private GenericDao<Sucursal, Integer> sucursalService;
    private Collection<Stock> stocks;
    private Stock stock;
    private Producto producto;
    private Integer stockId;
    private Integer cantidad;
    private Integer productoId;
    private Integer valorClasificatorioId;
    private Integer total;
    private boolean clasificable;
    private InputStream inputStream;

    public String show() {
        producto = productoService.find(productoId);
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("producto", producto);
        if (clasificable) {
            stocks = stockService.queryBy("ProductoClasificable", args);
            total = stockService.getIntResultBy("Producto,SumOfStock", "producto", producto);
            return SHOW_CLASF;
        } else {
            stock = stockService.findBy("ProductoClasificable", "producto", producto);
            return SHOW_ONE;
        }
    }

    public String fixStock() {
        Producto p = productoService.find(productoId);

        try {
            for (Stock s : p.getStocks()) {
                stockService.destroy(s);
            }

            TipoProducto tp = p.getTipoProducto();
            List<ValorClasificatorio> vc = null;
            List<Presentacion> prs = null;

            Collection<TipoAtributoClasificatorio> tac = tp.getTiposAtributoClasificatorios();
            if (tp.isClasificable() && tac != null && !tac.isEmpty()) {
                vc = valorClasificatorioService.queryBy("TipoAtributoList", "tipoAtributoList", tac);
            }

            Collection<TipoPresentacion> tpr = tp.getTiposPresentacion();
            if (tp.isPresentable() && tpr != null && !tpr.isEmpty()) {
                prs = presentacionService.queryBy("TipoList", "tipoList", tpr);
            }

            List<Sucursal> ss = sucursalService.findAll();

            State state = new DefaultState(p, ss);
            if (isClasificable(tp, tac) && isPresentable(tp, tpr)) {
                state = new PresentableAndClasificableState(p, ss, vc, prs);
            } else if (isClasificable(tp, tac)) {
                state = new ClasificableState(p, ss, vc);
            } else if (isPresentable(tp, tpr)) {
                state = new PresentableState(p, ss, prs);
            }
            state.setStockService(stockService);
            state.createStock();

            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(StockAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    public String create() {
        Stock s = new Stock();
        if (clasificable) {
            s.setValorClasificatorio(valorClasificatorioService.find(valorClasificatorioId));
        }
        s.setProducto(productoService.find(productoId));
        s.setStock(cantidad);
        stockService.create(s);
        inputStream = StringUtility.getInputString("OK");
        return JSON;
    }

    public String edit() {
        try {
            Stock s = stockService.find(stockId);
            s.setStock(cantidad);
            stockService.edit(s);
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(StockAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        } catch (Exception ex) {
            Logger.getLogger(StockAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    public String destroy() {
        try {
            stockService.destroy(stockService.find(stockId));
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(StockAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    private boolean isClasificable(TipoProducto tipoProducto, Collection<TipoAtributoClasificatorio> collection) {
        return tipoProducto.isClasificable() && collection != null && !collection.isEmpty();
    }

    private boolean isPresentable(TipoProducto tipoProducto, Collection<TipoPresentacion> collection) {
        return tipoProducto.isPresentable() && collection != null && !collection.isEmpty();
    }

    /**
     * @param stockService the stockService to set
     */
    public void setStockService(GenericDao<Stock, Integer> stockService) {
        this.stockService = stockService;
    }

    /**
     * @param valorClasificatorioService the valorPosibleService to set
     */
    public void setValorClasificatorioService(GenericDao<ValorClasificatorio, Integer> valorClasificatorioService) {
        this.valorClasificatorioService = valorClasificatorioService;
    }

    /**
     * @param productoService the productoService to set
     */
    public void setProductoService(GenericDao<Producto, Integer> productoService) {
        this.productoService = productoService;
    }

    /**
     * @return the stocks
     */
    public Collection<Stock> getStocks() {
        return stocks;
    }

    /**
     * @return the stock
     */
    public Stock getStock() {
        return stock;
    }

    /**
     * @param stockId the stockId to set
     */
    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @param productoId the productoId to set
     */
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    /**
     * @param valorClasificatorioId the valorClasificatorioId to set
     */
    public void setValorClasificatorioId(Integer valorClasificatorioId) {
        this.valorClasificatorioId = valorClasificatorioId;
    }

    /**
     * @param clasificable the clasificable to set
     */
    public void setClasificable(boolean clasificable) {
        this.clasificable = clasificable;
    }

    public void setPresentacionService(GenericDao<Presentacion, Integer> presentacionService) {
        this.presentacionService = presentacionService;
    }

    public void setSucursalService(GenericDao<Sucursal, Integer> sucursalService) {
        this.sucursalService = sucursalService;
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
     * @return the total
     */
    public Integer getTotal() {
        return total;
    }
}
