package com.palermotenis.controller.struts.actions.admin.analytics;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.number.CurrencyFormatter;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.precios.pks.PrecioPK;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.service.precios.PrecioService;
import com.palermotenis.model.service.stock.StockService;

public class VerPrecioStock extends InputStreamActionSupport {

    private static final long serialVersionUID = -1023046494395874487L;
    private static final Locale LOCALE_ES_AR = new Locale("es", "AR");

    private Integer id;

    private final List<Map<String, Object>> rows = Lists.newArrayList();

    @Autowired
    private StockService stockService;

    @Autowired
    private PrecioService precioService;

    @Autowired
    private CurrencyFormatter currencyFormatter;

    @Override
    public String execute() {
        Stock stock = stockService.getStockById(id);
        Producto producto = stock.getProducto();
        Presentacion presentacion = stock.getPresentacion();

        List<? extends Precio> precios = null;

        if (producto.isPresentable()) {
            precios = precioService.getPrecios(producto.getId(), presentacion.getId());
        } else {
            precios = precioService.getPrecios(producto.getId(), null);
        }

        int precioId = 0;
        for (Precio precio : precios) {
            PrecioPK pk = precio.getId();

            List<Object> cell = new ImmutableList.Builder<Object>()
                .add(pk.getPago().getNombre())
                .add(pk.getCuotas())
                .add(pk.getMoneda().getCodigo())
                .add(precio.getValor() != null ? format(precio.getValor()) : StringUtils.EMPTY)
                .add(precio.isEnOferta() ? "Sí" : "No")
                .add(
                    precio.isEnOferta() && precio.getValorOferta() != null ? format(precio.getValorOferta())
                            : StringUtils.EMPTY)
                .build();

            Map<String, Object> row = new ImmutableMap.Builder<String, Object>()
                .put("id", ++precioId)
                .put("cell", cell)
                .build();
            rows.add(row);
        }
        return SUCCESS;
    }

    private String format(Double value) {
        return currencyFormatter.print(value, LOCALE_ES_AR);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

}
