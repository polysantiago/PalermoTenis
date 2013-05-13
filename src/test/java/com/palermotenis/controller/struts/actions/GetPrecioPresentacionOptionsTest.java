package com.palermotenis.controller.struts.actions;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.infrastructre.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.service.monedas.MonedaService;
import com.palermotenis.model.service.pagos.PagoService;
import com.palermotenis.model.service.presentaciones.tipos.TipoPresentacionService;

public class GetPrecioPresentacionOptionsTest extends BaseSpringStrutsTestCase<GetPrecioPresentacionOptions> {

    private static final String ACTION_NAME = "GetPrecioPresentacionOptions";
    private static final String ACTION_NAMESPACE = "/";
    private static final String ACTION_MAPPING = "/GetPrecioPresentacionOptions";

    @Autowired
    private PagoService pagoService;

    @Autowired
    private MonedaService monedaService;

    @Autowired
    private TipoPresentacionService tipoPresentacionService;

    @Test
    public void testGetPrecioPresentacionOptions() throws UnsupportedEncodingException, ServletException {
        request.addParameter("tipoProductoId", "1");

        String expected = buildExpectedResult(1);
        String result = executeAction(ACTION_MAPPING);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        assertEquals(expected, result);
    }

    private String buildExpectedResult(Integer tipoProductoId) {
        List<Pago> pagos = pagoService.getAllPagos();
        Collection<Moneda> monedas = monedaService.getAllMonedas();
        Collection<TipoPresentacion> tiposPresentacion = tipoPresentacionService
            .getTiposPresentacionByTipoProducto(tipoProductoId);

        JSONObject jsonObject = new JSONObject();
        JsonConfig jsonConfigMonedas = new JsonConfig();
        jsonConfigMonedas.setExcludes(new String[]
            { "nombre", "contrario", "paises", "formatter", "locale" });
        JsonConfig jsonConfigTiposPresentacion = new JsonConfig();
        jsonConfigTiposPresentacion.setExcludes(new String[]
            { "tipoProducto", "presentaciones", "presentacionesByProd" });
        jsonObject.element("monedas", monedas, jsonConfigMonedas);
        jsonObject.element("tiposPresentacion", tiposPresentacion, jsonConfigTiposPresentacion);
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
