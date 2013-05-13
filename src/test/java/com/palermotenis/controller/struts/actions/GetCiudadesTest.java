package com.palermotenis.controller.struts.actions;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.infrastructre.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.beans.geograficos.Ciudad;
import com.palermotenis.model.beans.geograficos.Provincia;
import com.palermotenis.model.service.geograficos.GeographicService;

public class GetCiudadesTest extends BaseSpringStrutsTestCase<GetCiudades> {

    private static final String ACTION_NAME = "GetCiudades";
    private static final String ACTION_NAMESPACE = "/";
    private static final String ACTION_MAPPING = "/GetCiudades";

    @Autowired
    private GeographicService geographicService;

    @Test
    public void testGetCiudades() throws UnsupportedEncodingException, ServletException {

        request.setParameter("q", "Adrogu");

        StringBuilder stringBuilder = new StringBuilder();
        for (Ciudad ciudad : getCiudades()) {
            Provincia provincia = ciudad.getProvincia();
            stringBuilder.append(ciudad.getId()).append("|");
            stringBuilder.append(ciudad.getNombre()).append('|');
            stringBuilder.append(ciudad.getCodigoPostal()).append('|');
            stringBuilder.append(provincia.getNombre()).append('|');
            stringBuilder.append(provincia.getPais().getNombre()).append("\n");
        }

        stringBuilder.setLength(stringBuilder.length() - 1);

        String result = executeAction(ACTION_MAPPING);
        assertEquals(stringBuilder.toString(), result);
        assertEquals("text/plain;charset=ISO-8859-1", response.getContentType());
    }

    private List<Ciudad> getCiudades() {
        return geographicService.getCiudadedByNombre("%Adrogu%");
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
