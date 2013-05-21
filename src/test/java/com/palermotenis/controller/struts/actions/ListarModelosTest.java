package com.palermotenis.controller.struts.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
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
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.opensymphony.xwork2.ActionProxy;
import com.palermotenis.controller.struts.actions.exceptions.JsonErrorResponse;
import com.palermotenis.infrastructre.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.service.modelos.ModeloService;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;
import com.palermotenis.support.TestModeloService;
import com.palermotenis.support.TestTipoProductoService;

public class ListarModelosTest extends BaseSpringStrutsTestCase<ListarModelos> {

    private static final String ACTION_NAME = "ListarModelos_*";
    private static final String ACTION_NAMESPACE = "/";
    private static final String ACTION_MAPPING = "/ListarModelos_*";

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private TipoProductoService tipoProductoService;

    @Autowired
    private TestTipoProductoService testTipoProductoService;

    @Autowired
    private TestModeloService testModeloService;

    @Test
    @Transactional
    public void testListarModelosAll() throws UnsupportedEncodingException, ServletException {
        TipoProducto tipoProducto = testTipoProductoService.getAny();

        request.addParameter("idTipoProducto", tipoProducto.getId().toString());

        String expected = buildExpectedResult(getModelosByTipoProducto());
        String result = executeAction("/ListarModelos_listAll");

        assertEquals(expected, result);
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
    }

    @Test
    @Transactional
    public void testListarModelosByMarcaAll() throws UnsupportedEncodingException, ServletException {

        Modelo modelo = testModeloService.getAny();

        Integer tipoProductoId = modelo.getTipoProducto().getId();
        Integer marcaId = modelo.getMarca().getId();

        request.addParameter("idTipoProducto", tipoProductoId.toString());
        request.addParameter("idMarca", marcaId.toString());

        String expected = buildExpectedResult(getModelosByMarcaAndTipoProducto(tipoProductoId, marcaId));
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

    @Test
    public void testListarModelosError() throws Exception {
        ActionProxy proxy = getActionProxy("/ListarModelos_listAll");

        proxy.execute();

        String result = response.getContentAsString();

        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());

        JsonErrorResponse errorResponse = new GsonBuilder()
            .registerTypeAdapter(JsonErrorResponse.class, new JsonErrorResponseDeserializer())
            .create()
            .fromJson(result, JsonErrorResponse.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorResponse.getErrorCode());
        assertEquals("invalid_parameter", errorResponse.getErrorKey());
        assertEquals("idTipoProducto must not be null", errorResponse.getErrorMessage());
    }

    private List<Modelo> getModelosByMarcaAndTipoProducto(Integer tipoProductoId, Integer marcaId) {
        return modeloService.getModelosByMarcaAndTipoProducto(marcaId, tipoProductoId);
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
        List<Modelo> modelos = modeloService.getModelosWithRootActiveProductos(marcaId, tipoProductoId);

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

    private class JsonErrorResponseDeserializer implements JsonDeserializer<JsonErrorResponse> {

        @Override
        public JsonErrorResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            if (json.isJsonObject()) {
                JsonObject jsonObject = json.getAsJsonObject();
                JsonElement errorCode = jsonObject.get("error-code");
                JsonElement errorKey = jsonObject.get("error-key");
                JsonElement errorMessage = jsonObject.get("error-message");
                return new JsonErrorResponse(errorCode.getAsInt(), errorKey.getAsString(), errorMessage.getAsString());
            }
            return null;
        }

    }

}
