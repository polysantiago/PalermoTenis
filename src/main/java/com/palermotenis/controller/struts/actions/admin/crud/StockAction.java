package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.Collection;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.service.stock.StockService;

public class StockAction extends InputStreamActionSupport {

    private static final long serialVersionUID = 5299928945994232297L;

    private final String SHOW_ONE = "showOne";
    private final String SHOW_CLASF = "showByClasif";

    private Collection<Stock> stocks;
    private Stock stock;
    private Integer stockId;
    private Integer cantidad;
    private Integer productoId;
    private Integer valorClasificatorioId;
    private Integer total;
    private boolean clasificable;

    @Autowired
    private StockService stockService;

    public String show() {
        return clasificable ? SHOW_CLASF : SHOW_ONE;
    }

    public String fixStock() {
        try {
            stockService.fixStock(productoId);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
    }

    public String create() {
        if (clasificable) {
            stockService.createNewStock(productoId, valorClasificatorioId, cantidad);
        } else {
            stockService.createNewStock(productoId, cantidad);
        }

        success();
        return JSON;
    }

    public String edit() {
        try {
            stockService.updateQuantity(stockId, cantidad);
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
            stockService.delete(stockId);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
    }

    /**
     * @return the stocks
     */
    public Collection<Stock> getStocks() {
        if (stocks == null) {
            stocks = stockService.getStocksByProductoClasificable(productoId);
        }
        return stocks;
    }

    /**
     * @return the stock
     */
    public Stock getStock() {
        if (stock == null) {
            stock = stockService.getStockByProductoClasificable(productoId);
        }
        return stock;
    }

    /**
     * @param stockId
     *            the stockId to set
     */
    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    /**
     * @param cantidad
     *            the cantidad to set
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @param productoId
     *            the productoId to set
     */
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    /**
     * @param valorClasificatorioId
     *            the valorClasificatorioId to set
     */
    public void setValorClasificatorioId(Integer valorClasificatorioId) {
        this.valorClasificatorioId = valorClasificatorioId;
    }

    /**
     * @param clasificable
     *            the clasificable to set
     */
    public void setClasificable(boolean clasificable) {
        this.clasificable = clasificable;
    }

    /**
     * @return the total
     */
    public Integer getTotal() {
        if (total == null) {
            total = stockService.getSumOfStockByProducto(productoId);
        }
        return total;
    }
}
