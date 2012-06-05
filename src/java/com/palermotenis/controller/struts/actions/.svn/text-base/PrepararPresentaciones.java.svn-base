/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.beans.productos.Producto;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.PropertyNameProcessor;

/**
 *
 * @author Poly
 */
public class PrepararPresentaciones extends ActionSupport {

    private Integer productoId;
    private GenericDao<TipoProducto, Integer> tipoProductoService;
    private GenericDao<Producto, Integer> productoService;
    private InputStream inputStream;

    @Override
    public String execute() {

        JsonConfig tipoPresentacionConfig = new JsonConfig();
        tipoPresentacionConfig.setExcludes(new String[]{"tipoProducto", "presentaciones", "presentacionesByProd"});
        tipoPresentacionConfig.registerJsonPropertyNameProcessor(TipoPresentacion.class, new PropertyNameProcessor() {

            public String processPropertyName(Class beanClass, String name) {
                if (name.equalsIgnoreCase("nombre")) {
                    return "text";
                } else {
                    return name;
                }
            }
        });

        JSONObject mainJson = new JSONObject();
        JSONArray tiposProdArray = new JSONArray();
        for (TipoProducto t : tipoProductoService.findAll()) {
            if (t.isPresentable()) {
                JSONObject jObj = new JSONObject();
                jObj.element("id", t.getId());
                jObj.element("text", t.getNombre());
                jObj.element("children", t.getTiposPresentacion(), tipoPresentacionConfig);
                tiposProdArray.add(jObj);
            }
        }
        Producto producto = productoService.find(productoId);

        mainJson.element("tiposProducto", tiposProdArray);
        mainJson.element("preselectFirst", producto.getTipoProducto().getId());

        byte[] bytes = mainJson.toString().getBytes();
        inputStream = new ByteArrayInputStream(bytes);

        return SUCCESS;
    }

    /**
     * @param productoId the productoId to set
     */
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    /**
     * @param tipoProductoService the tiposProductoService to set
     */
    public void setTipoProductoService(GenericDao<TipoProducto, Integer> tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    /**
     * @param productoService the productoService to set
     */
    public void setProductoService(GenericDao<Producto, Integer> productoService) {
        this.productoService = productoService;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }
}
