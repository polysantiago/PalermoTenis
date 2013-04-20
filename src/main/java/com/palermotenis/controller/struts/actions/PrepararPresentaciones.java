package com.palermotenis.controller.struts.actions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.PropertyNameProcessor;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;

/**
 * 
 * @author Poly
 */
public class PrepararPresentaciones extends ActionSupport {

    private static final long serialVersionUID = -6953347495272026802L;

    private Integer productoId;
    private InputStream inputStream;

    @Autowired
    private GenericDao<TipoProducto, Integer> tipoProductoDao;

    @Autowired
    private GenericDao<Producto, Integer> productoDao;

    @Override
    public String execute() {

        JsonConfig tipoPresentacionConfig = new JsonConfig();
        tipoPresentacionConfig.setExcludes(new String[]
            { "tipoProducto", "presentaciones", "presentacionesByProd" });
        tipoPresentacionConfig.registerJavaPropertyNameProcessor(TipoPresentacion.class, new PropertyNameProcessor() {
            @Override
            public String processPropertyName(Class beanClass, String name) {
                return StringUtils.equals(name, "nombre") ? "text" : name;
            }
        });

        JSONObject mainJson = new JSONObject();
        JSONArray tiposProdArray = new JSONArray();
        for (TipoProducto t : tipoProductoDao.findAll()) {
            if (t.isPresentable()) {
                JSONObject jObj = new JSONObject();
                jObj.element("id", t.getId());
                jObj.element("text", t.getNombre());
                jObj.element("children", t.getTiposPresentacion(), tipoPresentacionConfig);
                tiposProdArray.add(jObj);
            }
        }
        Producto producto = productoDao.find(productoId);

        mainJson.element("tiposProducto", tiposProdArray);
        mainJson.element("preselectFirst", producto.getTipoProducto().getId());

        byte[] bytes = mainJson.toString().getBytes();
        inputStream = new ByteArrayInputStream(bytes);

        return SUCCESS;
    }

    /**
     * @param productoId
     *            the productoId to set
     */
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }
}
