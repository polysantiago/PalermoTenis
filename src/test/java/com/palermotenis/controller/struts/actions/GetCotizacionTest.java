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

    @Test
    public void testGetCotizacion() throws UnsupportedEncodingException, ServletException {
        request.setParameter("from", "ARS");
        request.setParameter("to", "USD");

        String actual = executeAction(getActionUrl());

        assertNotEquals(StringUtils.EMPTY, actual);
        assertEquals("text/plain;charset=ISO-8859-1", response.getContentType());
        assertTrue(Pattern.matches("\\d+\\.\\d+", actual));
    }

}
