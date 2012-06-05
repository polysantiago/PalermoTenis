/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.analytics;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.precios.PrecioPresentacion;
import com.palermotenis.model.beans.precios.PrecioUnidad;
import com.palermotenis.model.beans.precios.pks.PrecioPresentacionPK;
import com.palermotenis.model.beans.precios.pks.PrecioProductoPK;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.format.number.CurrencyFormatter;

/**
 *
 * @author poly
 */
public class VerPrecioStock extends ActionSupport {

    private GenericDao<Stock, Integer> stockService;
    private GenericDao<PrecioUnidad, PrecioProductoPK> precioUnidadService;
    private GenericDao<PrecioPresentacion, PrecioPresentacionPK> precioPresentacionService;
    private Integer id;
    private InputStream inputStream;
    private CurrencyFormatter currencyFormatter;
    private static final Locale LOCALE_ES_AR = new Locale("es", "AR");

    @Override
    public String execute() {
        Stock stock = stockService.find(id);
        JSONObject rootObj = new JSONObject();
        JSONArray rowsArr = new JSONArray();

        Producto producto = stock.getProducto();
        List<? extends Precio> precios = null;

        if(producto.isPresentable()){
            precios = precioPresentacionService.queryBy("Producto,Presentacion",
                    new ImmutableMap.Builder<String, Object>().put("producto", producto).put("presentacion", stock.getPresentacion()).build());
        } else {
            precios = precioUnidadService.queryBy("Producto",
                    new ImmutableMap.Builder<String, Object>().put("producto", producto).build());
        }
       
        int precioId = 0;
        for (Precio precio : precios) {
            JSONObject row = new JSONObject();
            row.element("id", ++precioId);
            JSONArray cellArr = new JSONArray();

            cellArr.add(precio.getId().getPago().getNombre());
            cellArr.add(precio.getId().getCuotas());
            cellArr.add(precio.getId().getMoneda().getCodigo());
            cellArr.add(precio.getValor() != null ? currencyFormatter.print(precio.getValor(), LOCALE_ES_AR) : "");
            cellArr.add(precio.isEnOferta() ? "Sí" : "No");
            cellArr.add(precio.isEnOferta() && precio.getValorOferta() != null ? currencyFormatter.print(precio.getValorOferta(), LOCALE_ES_AR) : "");
            row.element("cell", cellArr);
            rowsArr.add(row);
        }

        rootObj.element("rows", rowsArr);

        inputStream = StringUtility.getInputString(rootObj.toString());

        return SUCCESS;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setCurrencyFormatter(CurrencyFormatter currencyFormatter) {
        this.currencyFormatter = currencyFormatter;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStockService(GenericDao<Stock, Integer> stockService) {
        this.stockService = stockService;
    }

    public void setPrecioPresentacionService(GenericDao<PrecioPresentacion, PrecioPresentacionPK> precioPresentacionService) {
        this.precioPresentacionService = precioPresentacionService;
    }

    public void setPrecioUnidadService(GenericDao<PrecioUnidad, PrecioProductoPK> precioUnidadService) {
        this.precioUnidadService = precioUnidadService;
    }
}
