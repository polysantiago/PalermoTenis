package com.palermotenis.controller.struts.actions;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.infrastructure.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.model.service.stock.StockService;

public class GetValoresClasificatoriosByPresentacionTest extends
    BaseSpringStrutsTestCase<GetValoresClasificatoriosByPresentacion> {

    private static final String ACTION_NAME = "GetValoresClasificatoriosByPresentacion";
    private static final String ACTION_NAMESPACE = "/";
    private static final String ACTION_MAPPING = "/GetValoresClasificatoriosByPresentacion";

    @Autowired
    private StockService stockService;

    @Test
    public void testGetValoresClasificatoriosByPresentacion() throws UnsupportedEncodingException, ServletException {
        request.addParameter("productoId", "120");
        request.addParameter("presentacionId", "1");

        String expected = buildExpectedResult(120, 1);
        String result = executeAction(ACTION_MAPPING);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        assertEquals(expected, result);
    }

    private String buildExpectedResult(Integer productoId, Integer presentacionId) {
        List<Stock> stocks = stockService.getStocksByProductoAndPresentacion(productoId, presentacionId);
        JSONArray jsonArray = new JSONArray();
        for (Stock stock : stocks) {
            JSONObject jsonObject = new JSONObject();
            ValorClasificatorio valor = stock.getValorClasificatorio();
            jsonObject.element("id", valor.getId());
            jsonObject.element("nombre", valor.getNombre());
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }

    @Override
    protected String getActionUrl() {
        return ACTION_MAPPING;
    }

    @Override
    protected String getActionNamespace() {
        return ACTION_NAMESPACE;
    }

    @Override
    protected String getActionName() {
        return ACTION_NAME;
    }

}
