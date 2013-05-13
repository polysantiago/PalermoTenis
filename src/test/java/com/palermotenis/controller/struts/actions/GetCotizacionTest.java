package com.palermotenis.controller.struts.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.palermotenis.infrastructre.testsupport.base.BaseSpringStrutsTestCase;

public class GetCotizacionTest extends BaseSpringStrutsTestCase<GetCotizacion> {

    private static final String ACTION_NAME = "GetCotizacion";
    private static final String ACTION_NAMESPACE = "/";
    private static final String ACTION_MAPPING = "/GetCotizacion";

    @Test
    public void testGetCotizacion() throws UnsupportedEncodingException, ServletException {
        request.setParameter("from", "ARS");
        request.setParameter("to", "USD");

        String actual = executeAction(ACTION_MAPPING);

        assertNotEquals(StringUtils.EMPTY, actual);
        assertEquals("text/plain;charset=ISO-8859-1", response.getContentType());
        assertTrue(Pattern.matches("\\d+\\.\\d+", actual));
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
