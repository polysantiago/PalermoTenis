package com.palermotenis.controller.struts.actions.admin.crud;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.infrastructure.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.service.pagos.PagoService;

public class PagoActionTest extends BaseSpringStrutsTestCase<PagoAction> {

    @Autowired
    private PagoService pagoService;

    @Test
    public void testShow() {
        fail("Not yet implemented");
    }

    @Test
    public void testList() throws UnsupportedEncodingException, ServletException {
        String expected = buildExpectedJson();
        String result = executeAction("/admin/crud/PagoAction_list");

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
        return ((JSONArray) JSONSerializer.toJSON(pagoService.getAllPagos())).toString().replace("/", "\\/");
    }
}
