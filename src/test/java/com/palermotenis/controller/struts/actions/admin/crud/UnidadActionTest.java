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
import com.palermotenis.model.beans.Unidad;
import com.palermotenis.model.service.unidades.UnidadService;

public class UnidadActionTest extends BaseSpringStrutsTestCase<UnidadAction> {

    @Autowired
    private UnidadService unidadService;

    @Test
    public void testGetJson() throws UnsupportedEncodingException, ServletException {
        String expected = buildExpectedResult(unidadService.getAllUnidades());
        String result = executeAction("/admin/crud/UnidadAction_getJson");

        assertEquals(expected, result);
    }

    private String buildExpectedResult(List<Unidad> unidades) {
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]
            { "tipoAtributoCollection" });

        return ((JSONArray) JSONSerializer.toJSON(unidades, config)).toString();
    }

    @Override
    protected String getActionName() {
        return "UnidadAction_*";
    }

    @Override
    protected String getActionNamespace() {
        return "/admin/crud";
    }

}
