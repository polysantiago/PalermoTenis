/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.controller.results.GZIPCapable;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.util.StringUtility;
import com.palermotenis.xstream.XStreamMarshaller;

/**
 *
 * @author Poly
 */
public class ListarModelos extends ActionSupport implements GZIPCapable {

	private static final long serialVersionUID = -3479740110175074239L;
	
	private InputStream inputStream;
    private Integer idMarca;
    private Integer idTipoProducto;
    private Boolean root;
    private XStreamMarshaller xstreamMarshaller;
    
    @Autowired
    private GenericDao<Modelo, Integer> modeloDao;
    
    @Autowired
    private GenericDao<Marca, Integer> marcaDao;
    
    @Autowired
    private GenericDao<TipoProducto, Integer> tipoProductoDao;

    public String listAll() {
        List<Modelo> modelos = null;
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("tipoProducto", tipoProductoDao.find(idTipoProducto));
        
        if(idMarca != null){
            args.put("marca", marcaDao.find(idMarca));
            modelos = modeloDao.queryBy("Marca,TipoProducto", args);
        } else {
            modelos = modeloDao.queryBy("TipoProducto", args);
        }

        Collections.sort(modelos);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonBeanProcessor(Modelo.class, new JsonBeanProcessor() {

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

        JSONArray jArray = (JSONArray) JSONSerializer.toJSON(modelos, jsonConfig);

        inputStream = StringUtility.getInputString(jArray.toString());

        return SUCCESS;
    }

    public String listAllXML() {
        List<TipoProducto> tiposProducto = tipoProductoDao.findAll();
        inputStream = StringUtility.getInputString(xstreamMarshaller.toXML(tiposProducto));
        return "xml";
    }

    public String listActive() {
        Map<String, Object> args = new ImmutableMap.Builder<String, Object>().put("marca", marcaDao.find(idMarca)).put("tipoProducto", tipoProductoDao.find(idTipoProducto)).build();
        List<Modelo> modelos = modeloDao.queryBy("Marca,TipoProducto-Active", args);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonBeanProcessor(Modelo.class, new JsonBeanProcessor() {

            public JSONObject processBean(Object bean, JsonConfig jsonConfig) {
                Modelo m = (Modelo) bean;
                JSONObject o = new JSONObject();

                if (isValid(m)) {
                    o.element("id", m.getId());
                    o.element("text", m.getNombre());
                    o.element("leaf", m.isLeaf());
                    o.element("producible", m.isProducible());
                } else if (!m.isLeaf()) {
                    List<Modelo> rawHijos = modeloDao.queryBy("Padre-Active",
                            new ImmutableMap.Builder<String, Object>().put("padre", m).build());
                    List<Modelo> hijos = new ArrayList<Modelo>();
                    if (isValid(rawHijos)) {
                        for (Modelo h : rawHijos) {
                            if (isValid(h)) {
                                hijos.add(h);
                            } else if (!h.isLeaf()) {
                                List<Modelo> rawHijos2 = modeloDao.queryBy("Padre-Active",
                                        new ImmutableMap.Builder<String, Object>().put("padre", m).build());
                                add(o, rawHijos2, m, jsonConfig);
                            }
                        }
                        add(o, hijos, m, jsonConfig);
                    }
                }
                return o;
            }
        });

        JSONArray jArray = (JSONArray) JSONSerializer.toJSON(modelos, jsonConfig);
        JSONArray fArray = new JSONArray();
        inputStream = StringUtility.getInputString(doCleanUp(fArray, jArray).toString());

        return SUCCESS;
    }

    private JSONArray doCleanUp(JSONArray fArray, JSONArray jArray) {
        @SuppressWarnings("unchecked")
		Iterator<Object> li = jArray.iterator();
        while (li.hasNext()) {
            JSONObject o = (JSONObject) li.next();
            if (!o.isEmpty()) {
                fArray.add(o);
            }
        }
        return fArray;
    }

    private void add(JSONObject o, List<Modelo> hijos, Modelo m, JsonConfig jsonConfig) {
        if (isValid(hijos)) {
            o.element("id", m.getId());
            o.element("text", m.getNombre());
            o.element("children", hijos, jsonConfig);
        }
    }

    private boolean isValid(Modelo m) {
        Producto p = m.getProducto();
        return m.isLeaf() && p != null && p.hasStock();
    }

    private boolean isValid(List<Modelo> l) {
        return l != null && !l.isEmpty();
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param idTipoProducto the idTipoProducto to set
     */
    public void setIdTipoProducto(Integer idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    /**
     * @param idMarca the idMarca to set
     */
    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    /**
     * @return the root
     */
    public Boolean getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(Boolean root) {
        this.root = root;
    }

    public void setXstreamMarshaller(XStreamMarshaller xstreamMarshaller) {
        this.xstreamMarshaller = xstreamMarshaller;
    }
}
