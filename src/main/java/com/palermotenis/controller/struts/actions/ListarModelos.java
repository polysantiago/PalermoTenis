package com.palermotenis.controller.struts.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;

import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.service.modelos.ModeloService;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;
import com.palermotenis.xstream.XStreamMarshaller;

public class ListarModelos extends JsonActionSupport {

    private static final String XML = "xml";

    private static final long serialVersionUID = -3479740110175074239L;

    private Integer idMarca;
    private Integer idTipoProducto;
    private Boolean root;

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private TipoProductoService tipoProductoService;

    @Autowired
    private XStreamMarshaller xstreamMarshaller;

    public String listAll() {
        List<Modelo> modelos = null;

        if (idMarca != null) {
            modelos = modeloService.getModelosByMarcaAndTipoProducto(idMarca, idTipoProducto);
        } else {
            modelos = modeloService.getModelosByTipoProducto(idTipoProducto);
        }

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
        writeResponse(JSONSerializer.toJSON(modelos, jsonConfig));
        return SUCCESS;
    }

    public String listAllXML() {
        List<TipoProducto> tiposProducto = tipoProductoService.getAllTipoProducto();
        writeResponse(xstreamMarshaller.toXML(tiposProducto));
        return XML;
    }

    public String listActive() {
        List<Modelo> modelos = modeloService.getModelosByMarcaAndActiveTipoProducto(idMarca, idTipoProducto);

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
        writeResponse(doCleanUp(new JSONArray(), jArray));
        return SUCCESS;
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

    /**
     * @param idTipoProducto
     *            the idTipoProducto to set
     */
    public void setIdTipoProducto(Integer idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    /**
     * @param idMarca
     *            the idMarca to set
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
     * @param root
     *            the root to set
     */
    public void setRoot(Boolean root) {
        this.root = root;
    }
}
