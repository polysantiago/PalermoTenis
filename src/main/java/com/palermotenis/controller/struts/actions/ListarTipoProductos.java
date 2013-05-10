package com.palermotenis.controller.struts.actions;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.service.marcas.MarcaService;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;

public class ListarTipoProductos extends JsonActionSupport {

    private static final long serialVersionUID = 1772931751114222306L;

    @Autowired
    private TipoProductoService tipoProductoService;

    @Autowired
    private MarcaService marcaService;

    private final List<Map<String, Object>> resultList = Lists.newArrayList();

    @Override
    public String execute() {
        for (TipoProducto tipoProducto : getRoots()) {
            Map<String, Object> map = Maps.newLinkedHashMap();
            map.put("id", tipoProducto.getId());
            map.put("text", tipoProducto.getNombre());
            map.put("children", createMarcas(tipoProducto));
            resultList.add(map);
        }
        return SUCCESS;
    }

    private List<Map<String, Object>> createMarcas(TipoProducto tipoProducto) {
        List<Map<String, Object>> list = Lists.newArrayList();
        for (Marca marca : getActiveMarcas(tipoProducto)) {
            Map<String, Object> marcaMap = Maps.newLinkedHashMap();
            marcaMap.put("id", marca.getId());
            marcaMap.put("text", marca.getNombre());
            marcaMap.put("leaf", "true");
            list.add(marcaMap);
        }
        return list;
    }

    private List<TipoProducto> getRoots() {
        return tipoProductoService.getRootsTipoProducto();
    }

    private List<Marca> getActiveMarcas(TipoProducto tipoProducto) {
        return marcaService.getActiveMarcasByTipoProducto(tipoProducto);
    }

    public List<Map<String, Object>> getResultList() {
        return resultList;
    }

}
