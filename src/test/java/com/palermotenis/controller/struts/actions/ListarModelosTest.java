package com.palermotenis.controller.struts.actions;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.infrastructre.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.service.modelos.ModeloService;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;

public class ListarModelosTest extends BaseSpringStrutsTestCase<ListarModelos> {

    private static final String ACTION_NAME = "ListarModelos_*";
    private static final String ACTION_NAMESPACE = "/";
    private static final String ACTION_MAPPING = "/ListarModelos_*";

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private TipoProductoService tipoProductoService;

    @Test
    @Transactional
    public void testListarModelosAll() throws UnsupportedEncodingException, ServletException {
        request.addParameter("idTipoProducto", "1");

        String expected = buildExpectedResult(getModelosByTipoProducto());
        String result = executeAction("/ListarModelos_listAll");

        assertEquals(expected, result);
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
    }

    @Test
    @Transactional
    public void testListarModelosByMarcaAll() throws UnsupportedEncodingException, ServletException {
        request.addParameter("idTipoProducto", "3");
        request.addParameter("idMarca", "1");

        String expected = buildExpectedResult(getModelosByMarcaAndTipoProducto());
        String result = executeAction("/ListarModelos_listAll");

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        assertEquals(expected, result);
    }

    @Test
    public void testListarModelosAllActive() throws UnsupportedEncodingException, ServletException {
        request.addParameter("idTipoProducto", "1");
        request.addParameter("idMarca", "1");

        String expected = buildExpectedActiveResult(1, 1);
        String result = executeAction("/ListarModelos_listActive");

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        assertEquals(expected, result);
    }

    private List<Modelo> getModelosByMarcaAndTipoProducto() {
        return modeloService.getModelosByMarcaAndTipoProducto(1, 3);
    }

    private List<Modelo> getModelosByTipoProducto() {
        return modeloService.getModelosByTipoProducto(1);
    }

    private String buildExpectedResult(List<Modelo> modelos) {
        Collections.sort(modelos);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonBeanProcessor(Modelo.class, new JsonBeanProcessor() {

            @Override
            public JSONObject processBean(Object bean, JsonConfig jsonConfig) {
                Modelo m = (Modelo) bean;
                JSONObject o = new JSONObject();
                o.element("id", m.getId());
                o.element("text", m.getNombre());
                o.element("leaf", m.isLeaf());
                o.element("producible", m.isProducible());
                if (m.getProducto() != null) {
                    o.element("activo", m.getProducto().isActivo());
                    o.element("hasStock", m.getProducto().hasStock());
                } else {
                    o.element("children", m.getHijos(), jsonConfig);
                }
                return o;
            }
        });
        return JSONSerializer.toJSON(modelos, jsonConfig).toString().replace("/", "\\/");
    }

    private String buildExpectedActiveResult(Integer marcaId, Integer tipoProductoId) {
        List<Modelo> modelos = modeloService.getModelosByMarcaAndActiveTipoProducto(marcaId, tipoProductoId);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonBeanProcessor(Modelo.class, new JsonBeanProcessor() {

            @Override
            public JSONObject processBean(Object bean, JsonConfig jsonConfig) {
                Modelo modelo = (Modelo) bean;
                JSONObject jsonObject = new JSONObject();

                if (isValid(modelo)) {
                    jsonObject.element("id", modelo.getId());
                    jsonObject.element("text", modelo.getNombre());
                    jsonObject.element("leaf", modelo.isLeaf());
                    jsonObject.element("producible", modelo.isProducible());
                } else if (!modelo.isLeaf()) {
                    List<Modelo> rawHijos = modeloService.getModelosByActiveParent(modelo);
                    List<Modelo> hijos = new ArrayList<Modelo>();
                    if (isValid(rawHijos)) {
                        for (Modelo hijo : rawHijos) {
                            if (isValid(hijo)) {
                                hijos.add(hijo);
                            } else if (!hijo.isLeaf()) {
                                List<Modelo> rawHijos2 = modeloService.getModelosByActiveParent(modelo);
                                add(jsonObject, rawHijos2, modelo, jsonConfig);
                            }
                        }
                        add(jsonObject, hijos, modelo, jsonConfig);
                    }
                }
                return jsonObject;
            }
        });

        JSONArray jArray = (JSONArray) JSONSerializer.toJSON(modelos, jsonConfig);
        return doCleanUp(new JSONArray(), jArray).toString().replace("/", "\\/");
    }

    @SuppressWarnings("unchecked")
    private JSONArray doCleanUp(JSONArray fArray, JSONArray jArray) {
        Iterator<Object> iterator = jArray.iterator();
        while (iterator.hasNext()) {
            JSONObject jsonObject = (JSONObject) iterator.next();
            if (!jsonObject.isEmpty()) {
                fArray.add(jsonObject);
            }
        }
        return fArray;
    }

    private void add(JSONObject jsonObject, List<Modelo> hijos, Modelo modelo, JsonConfig jsonConfig) {
        if (isValid(hijos)) {
            jsonObject.element("id", modelo.getId());
            jsonObject.element("text", modelo.getNombre());
            jsonObject.element("children", hijos, jsonConfig);
        }
    }

    private boolean isValid(Modelo modelo) {
        Producto producto = modelo.getProducto();
        return modelo.isLeaf() && producto != null && producto.hasStock();
    }

    private boolean isValid(List<Modelo> modelos) {
        return modelos != null && !modelos.isEmpty();
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
