package com.palermotenis.controller.struts.actions;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.PropertyNameProcessor;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.infrastructure.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.service.productos.ProductoService;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;

public class PrepararPresentacionesTest extends BaseSpringStrutsTestCase<PrepararPresentaciones> {

    private static final String ACTION_NAME = "PrepararPresentaciones";
    private static final String ACTION_NAMESPACE = "/";
    private static final String ACTION_MAPPING = "/PrepararPresentaciones";

    @Autowired
    private ProductoService productoService;

    @Autowired
    private TipoProductoService tipoProductoService;

    @Test
    @Transactional
    public void testPrepararPresentaciones() throws UnsupportedEncodingException, ServletException {
        request.setParameter("productoId", "2");
        String result = executeAction(ACTION_MAPPING);
        String expected = buildExpectedResult(2);

        assertEquals(expected, result);
    }

    private String buildExpectedResult(Integer productoId) {
        JsonConfig tipoPresentacionConfig = new JsonConfig();
        tipoPresentacionConfig.setExcludes(new String[]
            { "tipoProducto", "presentaciones", "presentacionesByProd" });
        tipoPresentacionConfig.registerJavaPropertyNameProcessor(TipoPresentacion.class, new PropertyNameProcessor() {
            @Override
            @SuppressWarnings("rawtypes")
            public String processPropertyName(Class beanClass, String name) {
                return StringUtils.equals(name, "nombre") ? "text" : name;
            }
        });

        JSONObject mainJson = new JSONObject();
        JSONArray tiposProdArray = new JSONArray();
        for (TipoProducto t : getTiposProductos()) {
            if (t.isPresentable()) {
                JSONObject jObj = new JSONObject();
                jObj.element("id", t.getId());
                jObj.element("text", t.getNombre());
                jObj.element("children", t.getTiposPresentacion(), tipoPresentacionConfig);
                tiposProdArray.add(jObj);
            }
        }
        Producto producto = getProducto(productoId);

        mainJson.element("tiposProducto", tiposProdArray);
        mainJson.element("preselectFirst", producto.getTipoProducto().getId());

        return mainJson.toString();
    }

    private Producto getProducto(Integer productoId) {
        return productoService.getProductById(productoId);
    }

    private List<TipoProducto> getTiposProductos() {
        return tipoProductoService.getAllTipoProducto();
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
