package com.palermotenis.controller.struts.actions;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.infrastructre.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.service.marcas.MarcaService;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;

public class ListarTipoProductosTest extends BaseSpringStrutsTestCase<ListarTipoProductos> {

    private static final String ACTION_NAME = "ListarTipoProductos";
    private static final String ACTION_NAMESPACE = "/";
    private static final String ACTION_MAPPING = "/ListarTipoProductos";

    @Autowired
    private TipoProductoService tipoProductoService;

    @Autowired
    private MarcaService marcaService;

    @Test
    public void testListarTipoProductos() throws UnsupportedEncodingException, ServletException {
        String expected = buildExpectedResult();
        String result = executeAction(ACTION_MAPPING);

        assertEquals(expected, result);
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
    }

    private String buildExpectedResult() {
        Collection<TipoProducto> tipoProductos = getRoots();
        JSONArray jsonArray = new JSONArray();
        for (TipoProducto tipoProducto : tipoProductos) {
            JSONObject jsonObject = createTipoProducto(tipoProducto);
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString().replace("/", "\\/");
    }

    private JSONObject createTipoProducto(TipoProducto tipoProducto) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("id", tipoProducto.getId());
        jsonObject.element("text", tipoProducto.getNombre());

        JSONArray jsonArrMarcas = createMarcas(tipoProducto);
        jsonObject.element("children", jsonArrMarcas);
        return jsonObject;
    }

    private List<TipoProducto> getRoots() {
        return tipoProductoService.getRootsTipoProducto();
    }

    private List<Marca> getActiveMarcas(TipoProducto tipoProducto) {
        return marcaService.getActiveMarcasByTipoProducto(tipoProducto);
    }

    private JSONArray createMarcas(TipoProducto tipoProducto) {
        Collection<Marca> marcas = getActiveMarcas(tipoProducto);
        JSONArray jsonArrMarcas = new JSONArray();
        for (Marca marca : marcas) {
            jsonArrMarcas.add(createMarca(marca));
        }
        return jsonArrMarcas;
    }

    private JSONObject createMarca(Marca marca) {
        JSONObject jsonObjMarca = new JSONObject();
        jsonObjMarca.element("id", marca.getId());
        jsonObjMarca.element("text", marca.getNombre());
        jsonObjMarca.element("leaf", "true");
        return jsonObjMarca;
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
