package com.palermotenis.controller.struts.actions;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.infrastructure.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.service.presentaciones.PresentacionService;

public class GetPresentacionesByTipoTest extends BaseSpringStrutsTestCase<GetPresentacionesByTipo> {

    private static final String ACTION_NAME = "GetPresentacionesByTipo";
    private static final String ACTION_NAMESPACE = "/";
    private static final String ACTION_MAPPING = "/GetPresentacionesByTipo";

    @Autowired
    private PresentacionService presentacionService;

    @Test
    @Transactional
    public void testGetPresentacionesByTipo() throws UnsupportedEncodingException, ServletException {
        request.setParameter("tipoPresentacionId", "1");
        String result = executeAction(ACTION_MAPPING);
        String expected = buildExpectedResult(1);

        assertEquals(expected, result);
        assertEquals("application/json;charset=UTF-8", response.getContentType());
    }

    private String buildExpectedResult(Integer tipoPresentacionId) {
        Collection<Presentacion> presentaciones = getPresentaciones(tipoPresentacionId);

        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]
            { "tipoPresentacion", "productos", "stocks", "precios" });

        JSONArray jObject = (JSONArray) JSONSerializer.toJSON(presentaciones, config);
        return jObject.toString().replaceAll("\"cantidad\":([\\d]+)", "\"cantidad\":$1.0");
    }

    private List<Presentacion> getPresentaciones(Integer tipoPresentacionId) {
        return presentacionService.getPresentacionesByTipo(tipoPresentacionId);
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
