package com.palermotenis.controller.struts.actions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;

public class ListarTipoProductoMarcas extends JsonActionSupport {

    private static final long serialVersionUID = -226270066237959031L;

    private TipoProductoService tipoProductoService;

    @Override
    public String execute() {

        Map<TipoProducto, ArrayList<LinkedHashMap<Marca, Long>>> map = tipoProductoService
            .getTiposProductoAndMarcasAndProductoCount();
        JSONArray jsonArray = transformToJsonArray(map);
        writeResponse(jsonArray);
        return SUCCESS;
    }

    private JSONArray transformToJsonArray(Map<TipoProducto, ArrayList<LinkedHashMap<Marca, Long>>> map) {
        JSONArray jsonArray = new JSONArray();
        for (TipoProducto tipoProducto : map.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.element("id", tipoProducto.getId());
            jsonObject.element("text", tipoProducto.getNombre());

            JSONArray jsonArrMarcas = new JSONArray();
            for (Map<Marca, Long> marcaProductoCountMap : map.get(tipoProducto)) {
                for (Marca marca : marcaProductoCountMap.keySet()) {
                    JSONObject jsonObjMarca = new JSONObject();
                    jsonObjMarca.element("id", marca.getId());
                    jsonObjMarca.element("text", marca.getNombre());
                    jsonObjMarca.element("leaf", "true");
                    jsonObjMarca.element("productosCount", marcaProductoCountMap.get(marca));
                    jsonArrMarcas.add(jsonObjMarca);
                }
            }
            jsonObject.element("children", jsonArrMarcas);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

}
