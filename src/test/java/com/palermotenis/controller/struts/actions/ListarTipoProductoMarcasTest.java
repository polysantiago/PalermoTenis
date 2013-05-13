package com.palermotenis.controller.struts.actions;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.infrastructre.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;

public class ListarTipoProductoMarcasTest extends BaseSpringStrutsTestCase<ListarTipoProductoMarcas> {

    private static final String ACTION_NAME = "ListarTipoProductoMarcas";
    private static final String ACTION_NAMESPACE = "/";
    private static final String ACTION_MAPPING = "/ListarTipoProductoMarcas";

    @Autowired
    private TipoProductoService tipoProductoService;

    @Test
    public void testListarTipoProductoMarcas() throws UnsupportedEncodingException, ServletException {
        String expected = buildExpectedResult();
        String result = executeAction(ACTION_MAPPING);

        assertEquals(expected, result);
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
    }

    private String buildExpectedResult() {
        Map<TipoProducto, ArrayList<LinkedHashMap<Marca, Long>>> map = tipoProductoService
            .getTiposProductoAndMarcasAndProductoCount();
        JSONArray jsonArray = transformToJsonArray(map);
        return jsonArray.toString().replace("/", "\\/");
    }

    private JSONArray transformToJsonArray(Map<TipoProducto, ArrayList<LinkedHashMap<Marca, Long>>> map) {
        JSONArray jsonArray = new JSONArray();
        for (TipoProducto tipoProducto : map.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.element("id", tipoProducto.getId());
            jsonObject.element("text", tipoProducto.getNombre());

            JSONArray jsonArrMarcas = new JSONArray();
            for (Map<Marca, Long> marcaProductoCountMap : map.get(tipoProducto)) {
                for (Marca marca : marcaProductoCountMap.keySet()) {
                    JSONObject jsonObjMarca = new JSONObject();
                    jsonObjMarca.element("id", marca.getId());
                    jsonObjMarca.element("text", marca.getNombre());
                    jsonObjMarca.element("leaf", "true");
                    jsonObjMarca.element("productosCount", marcaProductoCountMap.get(marca));
                    jsonArrMarcas.add(jsonObjMarca);
                }
            }
            jsonObject.element("children", jsonArrMarcas);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
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
