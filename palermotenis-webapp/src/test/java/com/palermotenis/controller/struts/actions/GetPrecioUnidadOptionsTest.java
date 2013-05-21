package com.palermotenis.controller.struts.actions;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.infrastructure.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.service.monedas.MonedaService;
import com.palermotenis.model.service.pagos.PagoService;

public class GetPrecioUnidadOptionsTest extends BaseSpringStrutsTestCase<GetPrecioUnidadOptions> {

    private static final String ACTION_NAME = "GetPrecioUnidadOptions";
    private static final String ACTION_NAMESPACE = "/";
    private static final String ACTION_MAPPING = "/GetPrecioUnidadOptions";

    @Autowired
    private PagoService pagoService;

    @Autowired
    private MonedaService monedaService;

    @Test
    public void testGetPrecioUnidadOptions() throws UnsupportedEncodingException, ServletException {
        String expected = buildExpectedResult();
        String result = executeAction(ACTION_MAPPING);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        assertEquals(expected, result);
    }

    private String buildExpectedResult() {
        Collection<Pago> pagos = pagoService.getAllPagos();
        Collection<Moneda> monedas = monedaService.getAllMonedas();

        JSONObject jsonObject = new JSONObject();

        JsonConfig jsonConfigMonedas = new JsonConfig();
        jsonConfigMonedas.setExcludes(new String[]
            { "nombre", "contrario", "paises", "formatter", "locale" });

        jsonObject.element("monedas", monedas, jsonConfigMonedas);
        jsonObject.element("pagos", pagos);

        return jsonObject.toString().replace("/", "\\/");
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
