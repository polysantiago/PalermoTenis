package com.palermotenis.controller.struts.actions;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;

import org.junit.Test;

import com.google.common.base.Joiner;
import com.palermotenis.infrastructure.testsupport.base.BaseSpringStrutsTestCase;

public class GetProductosAutoCompleteListTest extends BaseSpringStrutsTestCase<GetProductosAutoCompleteList> {

    private static final String ACTION_NAME = "GetProductosAutoCompleteList_*";
    private static final String ACTION_NAMESPACE = "/";
    private static final String ACTION_MAPPING = "/GetProductosAutoCompleteList_*";

    @Test
    public void testGetActive() throws UnsupportedEncodingException, ServletException {
        setParameters();
        String actual = executeAction("/GetProductosAutoCompleteList_active");
        String expected = Joiner.on('|').join("4225", "Raquetas Tenis", "Babolat", "Aero", "Aero Pro Drive Cortex",
            "TamaÃ±o de Grip", "4 1/4\"", "$699,00");
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAll() throws UnsupportedEncodingException, ServletException {
        setParameters();
        String actual = executeAction("/GetProductosAutoCompleteList_all");
        String expected = Joiner.on('|').join("4225", "Raquetas Tenis", "Babolat", "Aero", "Aero Pro Drive Cortex",
            "TamaÃ±o de Grip", "4 1/4\"", "$699,00");
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllCostos() throws UnsupportedEncodingException, ServletException {
        setParameters();
        request.setParameter("proveedorId", "4");
        String actual = executeAction("/GetProductosAutoCompleteList_allCostos");
        String expected = Joiner.on('|').join("4225", "Raquetas Tenis", "Babolat", "Aero", "Aero Pro Drive Cortex",
            "TamaÃ±o de Grip", "4 1/4\"", "0.00");
        assertEquals(expected, actual);
    }

    private void setParameters() {
        request.setParameter("q", "Aero");
        request.setParameter("limit", "1");
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
