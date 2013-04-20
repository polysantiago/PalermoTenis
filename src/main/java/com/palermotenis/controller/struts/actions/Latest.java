package com.palermotenis.controller.struts.actions;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.util.StringUtility;

/**
 * 
 * @author Santiago
 */
public class Latest extends ActionSupport {

    private static final long serialVersionUID = -9117974645418468534L;
    private static final String JSON = "json";

    private List<Producto> productos;
    private InputStream inputStream;

    @Autowired
    private GenericDao<Producto, Integer> productoDao;

    @Override
    public String execute() {
        productos = productoDao.query("Ofertas", 8, 0);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonBeanProcessor(Producto.class, new JsonBeanProcessor() {

            @Override
            public JSONObject processBean(Object bean, JsonConfig jsonConfig) {
                Producto p = (Producto) bean;
                JSONObject o = new JSONObject();
                o.element("id", p.getId());
                o.element("text", p.getModelo().getNombre());
                o.element("activo", p.isActivo());
                o.element("hasStock", p.hasStock());
                return o;
            }
        });

        JSONArray jArray = (JSONArray) JSONSerializer.toJSON(productos, jsonConfig);

        inputStream = StringUtility.getInputString(jArray.toString());

        return JSON;
    }

    public Collection<Producto> getProductos() {
        return productos;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

}
