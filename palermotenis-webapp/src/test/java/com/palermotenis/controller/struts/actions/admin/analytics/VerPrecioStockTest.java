package com.palermotenis.controller.struts.actions.admin.analytics;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.number.CurrencyFormatter;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.infrastructure.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.service.precios.impl.PrecioService;
import com.palermotenis.model.service.stock.StockService;
import com.palermotenis.support.TestPrecioService;
import com.palermotenis.support.TestProductoService;

public class VerPrecioStockTest extends BaseSpringStrutsTestCase<VerPrecioStock> {

    private static final Locale LOCALE_ES_AR = new Locale("es", "AR");

    @Autowired
    private StockService stockService;

    @Autowired
    private PrecioService precioService;

    @Autowired
    private TestProductoService testProductoService;

    @Autowired
    private TestPrecioService testPrecioService;

    @Autowired
    private CurrencyFormatter currencyFormatter;

    @Test
    @Transactional
    public void testVerPrecioStock() throws UnsupportedEncodingException, ServletException {
        Producto producto = testProductoService.create();
        Stock stock = producto.getStocks().get(0);

        testPrecioService.create(producto);

        request.setParameter("id", stock.getId().toString());
        String expected = buildExpectedResponse(stock.getId());
        String result = executeAction("/admin/estadisticas/VerPrecioStock");

        assertEquals(expected, result);
    }

    private String buildExpectedResponse(Integer stockId) {

        Stock stock = stockService.getStockById(stockId);
        JSONObject rootObj = new JSONObject();
        JSONArray rowsArr = new JSONArray();

        Producto producto = stock.getProducto();
        List<? extends Precio> precios = null;

        if (producto.isPresentable()) {
            precios = precioService.getPrecios(producto.getId(), stock.getPresentacion().getId());
        } else {
            precios = precioService.getPrecios(producto.getId(), null);
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

        return rootObj.toString();
    }

}
