package com.palermotenis.controller.struts.actions.admin.crud;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;

import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.infrastructre.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.service.monedas.MonedaService;

public class MonedaActionTest extends BaseSpringStrutsTestCase<MonedaAction> {

    @Autowired
    private MonedaService monedaService;

    @Test
    public void testShow() {
        fail("Not yet implemented");
    }

    @Test
    public void testList() throws UnsupportedEncodingException, ServletException {
        String expected = buildExpectedJson();
        String result = executeAction("/admin/crud/MonedaAction_list");

        assertEquals(expected, result);
    }

    @Test
    public void testCreate() {
        fail("Not yet implemented");
    }

    @Test
    public void testEdit() {
        fail("Not yet implemented");
    }

    @Test
    public void testDestroy() {
        fail("Not yet implemented");
    }

    private String buildExpectedJson() {
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]
            { "nombre", "contrario", "paises", "locale", "formatter" });

        return JSONSerializer.toJSON(monedaService.getAllMonedas(), config).toString();
    }

}
