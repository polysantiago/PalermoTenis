/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Santiago
 */
public class Latest extends ActionSupport {
    
    private static final String JSON = "json";
    private GenericDao<Producto, Integer> productoService;
    private List<Producto> productos;
    private InputStream inputStream;
    
    @Override
    public String execute() {
        productos = productoService.query("Ofertas", 8, 0);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonBeanProcessor(Producto.class, new JsonBeanProcessor() {

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
    
    @Autowired
    public void setProductoService(GenericDao<Producto, Integer> productoService) {
        this.productoService = productoService;
    }

    public Collection<Producto> getProductos() {
        return productos;
    }

    public InputStream getInputStream() {
        return inputStream;
    }       
    
}
