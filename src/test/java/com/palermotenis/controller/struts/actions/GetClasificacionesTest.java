package com.palermotenis.controller.struts.actions;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.infrastructre.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.service.atributos.tipos.TipoAtributoService;

public class GetClasificacionesTest extends BaseSpringStrutsTestCase<GetClasificaciones> {

    private static final String ACTION_NAME = "GetClasificaciones";
    private static final String ACTION_NAMESPACE = "/";
    private static final String ACTION_MAPPING = "/GetClasificaciones";

    @Autowired
    private TipoAtributoService tipoAtributoService;

    @Test
    public void testGetClasificaciones() throws UnsupportedEncodingException, ServletException {
        Collection<TipoAtributoClasificatorio> clasificaciones = getTiposAtributos();
        JSONArray clasifArr = (JSONArray) JSONSerializer.toJSON(clasificaciones);
        String result = executeAction(ACTION_MAPPING);
        assertEquals(clasifArr.toString(), result);
        assertEquals("application/json;charset=UTF-8", response.getContentType());
    }

    private List<TipoAtributoClasificatorio> getTiposAtributos() {
        return tipoAtributoService.getAllTiposAtributosClasificatorios();
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
