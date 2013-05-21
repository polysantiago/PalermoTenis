package com.palermotenis.controller.struts.actions.admin.crud;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.infrastructure.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.service.proveedores.ProveedorService;

public class ProveedorActionTest extends BaseSpringStrutsTestCase<ProveedorAction> {

    @Autowired
    private ProveedorService proveedorService;

    @Test
    public void testList() throws UnsupportedEncodingException, ServletException {
        String expected = buildExpectedResult();
        String result = executeAction("/admin/crud/ProveedorAction_list");

        assertEquals(expected, result);
    }

    private String buildExpectedResult() {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]
            { "costos" });
        return ((JSONArray) JSONSerializer.toJSON(proveedorService.getAllProveedores(), jsonConfig)).toString();
    }

    @Override
    protected String getActionName() {
        return "ProveedorAction_*";
    }

    @Override
    protected String getActionNamespace() {
        return "/admin/crud";
    }

}
