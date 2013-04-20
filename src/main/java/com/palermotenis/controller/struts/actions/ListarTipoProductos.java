/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import java.io.InputStream;
import java.util.Collection;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.util.StringUtility;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Poly
 */
public class ListarTipoProductos extends ActionSupport {

    private GenericDao<TipoProducto, Integer> tipoProductoService;
    private GenericDao<Marca, Integer> marcaService;
    private InputStream inputStream;

    @Override
    public String execute() {

        Collection<TipoProducto> productos = tipoProductoService.query("Childless");
        JSONArray jsonArray = new JSONArray();

        for (TipoProducto tp : productos) {
            Map<String, Object> args = new HashMap<String, Object>();
            args.put("tipoProducto", tp);
            Collection<Marca> marcas = marcaService.queryBy("TipoProductoAndActiveProducto", args);

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
     * @param tipoProductoService the productosService to set
     */
    public void setTipoProductoService(GenericDao<TipoProducto, Integer> tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    public void setMarcaService(GenericDao<Marca, Integer> marcaService) {
        this.marcaService = marcaService;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }
}
