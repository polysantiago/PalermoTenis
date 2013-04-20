package com.palermotenis.controller.struts.actions;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.util.StringUtility;

/**
 * 
 * @author Poly
 */
public class ListarTipoProductos extends ActionSupport {

    private static final long serialVersionUID = 1772931751114222306L;

    private InputStream inputStream;

    @Autowired
    private GenericDao<TipoProducto, Integer> tipoProductoDao;

    @Autowired
    private GenericDao<Marca, Integer> marcaDao;

    @Override
    public String execute() {

        Collection<TipoProducto> productos = tipoProductoDao.query("Childless");
        JSONArray jsonArray = new JSONArray();

        for (TipoProducto tp : productos) {
            Map<String, Object> args = new HashMap<String, Object>();
            args.put("tipoProducto", tp);
            Collection<Marca> marcas = marcaDao.queryBy("TipoProductoAndActiveProducto", args);

            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArrMarcas = new JSONArray();

            jsonObject.element("id", tp.getId());
            jsonObject.element("text", tp.getNombre());

            for (Marca m : marcas) {
                JSONObject jsonObjMarca = new JSONObject();
                jsonObjMarca.element("id", m.getId());
                jsonObjMarca.element("text", m.getNombre());
                jsonObjMarca.element("leaf", "true");
                jsonArrMarcas.add(jsonObjMarca);
            }
            jsonObject.element("children", jsonArrMarcas);

            jsonArray.add(jsonObject);

        }
        inputStream = StringUtility.getInputString(jsonArray.toString());
        return SUCCESS;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }
}
