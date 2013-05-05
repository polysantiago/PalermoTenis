package com.palermotenis.controller.struts.actions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;

public class ListarTipoProductoMarcas extends JsonActionSupport {

    private static final long serialVersionUID = -226270066237959031L;

    @Autowired
    private TipoProductoService tipoProductoService;

    private final List<Map<String, Object>> resultList = Lists.newArrayList();

    @Override
    public String execute() {
        Map<TipoProducto, ArrayList<LinkedHashMap<Marca, Long>>> map = getMap();
        for (TipoProducto tipoProducto : map.keySet()) {
            Map<String, Object> jsonMap = Maps.newLinkedHashMap();
            jsonMap.put("id", tipoProducto.getId());
            jsonMap.put("text", tipoProducto.getNombre());

            List<Map<String, Object>> marcasList = Lists.newArrayList();
            for (Map<Marca, Long> marcaProductoCountMap : map.get(tipoProducto)) {
                for (Marca marca : marcaProductoCountMap.keySet()) {
                    Map<String, Object> jsonMarcaMap = Maps.newLinkedHashMap();
                    jsonMarcaMap.put("id", marca.getId());
                    jsonMarcaMap.put("text", marca.getNombre());
                    jsonMarcaMap.put("leaf", "true");
                    jsonMarcaMap.put("productosCount", marcaProductoCountMap.get(marca));
                    marcasList.add(jsonMarcaMap);
                }
            }
            jsonMap.put("children", marcasList);
            resultList.add(jsonMap);
        }
        return SUCCESS;
    }

    private Map<TipoProducto, ArrayList<LinkedHashMap<Marca, Long>>> getMap() {
        return tipoProductoService.getTiposProductoAndMarcasAndProductoCount();
    }

    public List<Map<String, Object>> getResultList() {
        return resultList;
    }

}
