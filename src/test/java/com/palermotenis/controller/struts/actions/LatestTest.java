package com.palermotenis.controller.struts.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;

import org.apache.struts2.StrutsSpringJUnit4TestCase;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.service.productos.ProductoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/spring/applicationContext-business.xml")
public class LatestTest extends StrutsSpringJUnit4TestCase<Latest> {

    private static final String ACTION_NAME = "Latest";
    private static final String ACTION_NAMESPACE = "/";
    private static final String ACTION_MAPPING = "/Latest";

    @Autowired
    private ProductoService productoService;

    @Test
    public void testGetLatest() throws UnsupportedEncodingException, ServletException {
        ActionMapping mapping = getActionMapping(ACTION_MAPPING);
        assertNotNull(mapping);
        assertEquals(ACTION_NAMESPACE, mapping.getNamespace());
        assertEquals(ACTION_NAME, mapping.getName());

        String actual = executeAction("/Latest");

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonBeanProcessor(Producto.class, new JsonBeanProcessor() {

            @Override
            public JSONObject processBean(Object bean, JsonConfig jsonConfig) {
                Producto producto = (Producto) bean;
                JSONObject jsonObject = new JSONObject();
                jsonObject.element("id", producto.getId());
                jsonObject.element("text", producto.getModelo().getNombre());
                jsonObject.element("activo", producto.isActivo());
                jsonObject.element("hasStock", producto.hasStock());
                return jsonObject;
            }
        });

        JSONArray jArray = (JSONArray) JSONSerializer.toJSON(productoService.getLatest8Ofertas(), jsonConfig);

        assertEquals(jArray.toString(), actual);
        assertEquals("application/json;charset=UTF-8", response.getContentType());
    }
}
