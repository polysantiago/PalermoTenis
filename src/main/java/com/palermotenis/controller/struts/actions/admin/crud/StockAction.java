package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.controller.struts.actions.JsonActionSupport;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.ClasificableState;
import com.palermotenis.model.beans.productos.tipos.DefaultState;
import com.palermotenis.model.beans.productos.tipos.PresentableAndClasificableState;
import com.palermotenis.model.beans.productos.tipos.PresentableState;
import com.palermotenis.model.beans.productos.tipos.State;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;

/**
 * 
 * @author Poly
 */
public class StockAction extends JsonActionSupport {

    private static final long serialVersionUID = 5299928945994232297L;

    private final String SHOW_ONE = "showOne";
    private final String SHOW_CLASF = "showByClasif";

    private Collection<Stock> stocks;
    private Stock stock;
    private Producto producto;
    private Integer stockId;
    private Integer cantidad;
    private Integer productoId;
    private Integer valorClasificatorioId;
    private Integer total;
    private boolean clasificable;

    @Autowired
    private GenericDao<Stock, Integer> stockDao;

    @Autowired
    private GenericDao<ValorClasificatorio, Integer> valorClasificatorioDao;

    @Autowired
    private GenericDao<Producto, Integer> productoDao;

    @Autowired
    private GenericDao<Presentacion, Integer> presentacionDao;

    @Autowired
    private GenericDao<Sucursal, Integer> sucursalDao;

    public String show() {
        producto = productoDao.find(productoId);
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("producto", producto);
        if (clasificable) {
            stocks = stockDao.queryBy("ProductoClasificable", args);
            total = stockDao.getIntResultBy("Producto,SumOfStock", "producto", producto);
            return SHOW_CLASF;
        } else {
            stock = stockDao.findBy("ProductoClasificable", "producto", producto);
            return SHOW_ONE;
        }
    }

    public String fixStock() {
        Producto p = productoDao.find(productoId);

        try {
            for (Stock s : p.getStocks()) {
                stockDao.destroy(s);
            }

            TipoProducto tp = p.getTipoProducto();
            List<ValorClasificatorio> vc = null;
            List<Presentacion> prs = null;

            Collection<TipoAtributoClasificatorio> tac = tp.getTiposAtributoClasificatorios();
            if (tp.isClasificable() && tac != null && !tac.isEmpty()) {
                vc = valorClasificatorioDao.queryBy("TipoAtributoList", "tipoAtributoList", tac);
            }

            Collection<TipoPresentacion> tpr = tp.getTiposPresentacion();
            if (tp.isPresentable() && tpr != null && !tpr.isEmpty()) {
                prs = presentacionDao.queryBy("TipoList", "tipoList", tpr);
            }

            List<Sucursal> ss = sucursalDao.findAll();

            State state = new DefaultState(p, ss);
            if (isClasificable(tp, tac) && isPresentable(tp, tpr)) {
                state = new PresentableAndClasificableState(p, ss, vc, prs);
            } else if (isClasificable(tp, tac)) {
                state = new ClasificableState(p, ss, vc);
            } else if (isPresentable(tp, tpr)) {
                state = new PresentableState(p, ss, prs);
            }
            state.setStockDao(stockDao);
            state.createStock();

            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
    }

    public String create() {
        Stock s = new Stock();
        if (clasificable) {
            s.setValorClasificatorio(valorClasificatorioDao.find(valorClasificatorioId));
        }
        s.setProducto(productoDao.find(productoId));
        s.setStock(cantidad);
        stockDao.create(s);
        success();
        return JSON;
    }

    public String edit() {
        try {
            Stock s = stockDao.find(stockId);
            s.setStock(cantidad);
            stockDao.edit(s);
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
            stockDao.destroy(stockDao.find(stockId));
            success();
        } catch (HibernateException ex) {
            failure(ex);
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
