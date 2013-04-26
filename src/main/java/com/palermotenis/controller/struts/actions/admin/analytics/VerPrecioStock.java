package com.palermotenis.controller.struts.actions.admin.analytics;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.number.CurrencyFormatter;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.precios.PrecioPresentacion;
import com.palermotenis.model.beans.precios.PrecioUnidad;
import com.palermotenis.model.beans.precios.pks.PrecioPresentacionPK;
import com.palermotenis.model.beans.precios.pks.PrecioProductoPK;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.dao.Dao;
import com.palermotenis.util.StringUtility;

/**
 * 
 * @author poly
 */
public class VerPrecioStock extends ActionSupport {

    private static final long serialVersionUID = -1023046494395874487L;
    private static final Locale LOCALE_ES_AR = new Locale("es", "AR");

    private Integer id;
    private InputStream inputStream;

    @Autowired
    private Dao<Stock, Integer> stockDao;

    @Autowired
    private Dao<PrecioUnidad, PrecioProductoPK> precioUnidadDao;

    @Autowired
    private Dao<PrecioPresentacion, PrecioPresentacionPK> precioPresentacionDao;

    @Autowired
    private CurrencyFormatter currencyFormatter;

    @Override
    public String execute() {
        Stock stock = stockDao.find(id);
        JSONObject rootObj = new JSONObject();
        JSONArray rowsArr = new JSONArray();

        Producto producto = stock.getProducto();
        List<? extends Precio> precios = null;

        if (producto.isPresentable()) {
            precios = precioPresentacionDao.queryBy("Producto,Presentacion", new ImmutableMap.Builder<String, Object>()
                .put("producto", producto)
                .put("presentacion", stock.getPresentacion())
                .build());
        } else {
            precios = precioUnidadDao.queryBy("Producto",
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
            cellArr.add(precio.isEnOferta() && precio.getValorOferta() != null ? currencyFormatter.print(
                precio.getValorOferta(), LOCALE_ES_AR) : "");
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

    public void setId(Integer id) {
        this.id = id;
    }
}
