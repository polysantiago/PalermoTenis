package com.palermotenis.controller.struts.actions.admin.crud;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.infrastructre.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.service.presentaciones.tipos.TipoPresentacionService;
import com.palermotenis.support.TestTipoPresentacionService;

public class TipoPresentacionActionTest extends BaseSpringStrutsTestCase<TipoPresentacionAction> {

    @Autowired
    private TipoPresentacionService tipoPresentacionService;

    @Autowired
    private TestTipoPresentacionService testTipoPresentacionService;

    @Test
    public void testList() throws UnsupportedEncodingException, ServletException {
        String expected = buildExpectedResult(tipoPresentacionService.getAllTiposPresentacion());
        String result = executeAction("/admin/crud/TipoPresentacionAction_list");

        assertEquals(expected, result);
    }

    @Test
    public void testListByTipoProducto() throws UnsupportedEncodingException, ServletException {
        TipoPresentacion tipoPresentacion = testTipoPresentacionService.create();
        Integer tipoProductoId = tipoPresentacion.getTipoProducto().getId();

        request.setParameter("tipoProductoId", tipoProductoId.toString());
        String expected = buildExpectedResult(tipoPresentacionService
            .getTiposPresentacionByTipoProducto(tipoProductoId));
        String result = executeAction("/admin/crud/TipoPresentacionAction_listByTipoProducto");

        assertEquals(expected, result);
    }

    private String buildExpectedResult(List<TipoPresentacion> tiposPresentacion) {
        return ((JSONArray) JSONSerializer.toJSON(tiposPresentacion, buildJsonConfig())).toString();
    }

    private JsonConfig buildJsonConfig() {
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]
            { "tipoProducto", "presentaciones", "presentacionesByProd" });
        return config;
    }

    @Override
    protected String getActionName() {
        return "TipoPresentacionAction_*";
    }

    @Override
    protected String getActionNamespace() {
        return "/admin/crud";
    }

}
