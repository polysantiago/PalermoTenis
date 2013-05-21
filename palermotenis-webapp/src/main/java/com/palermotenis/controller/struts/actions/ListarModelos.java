package com.palermotenis.controller.struts.actions;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.palermotenis.controller.struts.actions.exceptions.JsonException;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.service.modelos.ModeloService;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;

public class ListarModelos extends InputStreamActionSupport {

    private static final long serialVersionUID = -3479740110175074239L;

    private Integer idMarca;
    private Integer idTipoProducto;
    private Boolean root;

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private TipoProductoService tipoProductoService;

    private List<Map<String, Object>> resultList;

    public String listAll() {
        if (idTipoProducto == null) {
            throw new JsonException("invalid_parameter", new RuntimeException("idTipoProducto must not be null"));
        }

        List<Modelo> modelos = isMarcaRequired() ? getModelosByMarca() : getModelos();
        Collections.sort(modelos);
        resultList = createModelosList(new BasicMapCreator(), modelos);
        return SUCCESS;
    }

    public String listActive() {
        resultList = createModelosList(new ActiveMapCreator(), getActiveModelos());
        return SUCCESS;
    }

    private List<Map<String, Object>> createModelosList(MapCreator mapCreator, List<Modelo> modelos) {
        List<Map<String, Object>> list = Lists.newArrayList();
        for (Modelo modelo : modelos) {
            list.add(mapCreator.createMap(modelo));
        }
        Iterables.removeIf(list, new Predicate<Map<String, Object>>() {
            @Override
            public boolean apply(@Nullable Map<String, Object> input) {
                return MapUtils.isEmpty(input);
            }
        });
        return list;
    }

    private void addBasicInfoToMap(Map<String, Object> map, Modelo modelo) {
        map.put("id", modelo.getId());
        map.put("text", modelo.getNombre());
        map.put("leaf", modelo.isLeaf());
        map.put("producible", modelo.isProducible());
    }

    private boolean isActive(Modelo modelo) {
        Producto producto = modelo.getProducto();
        return modelo.isLeaf() && producto != null && producto.hasStock();
    }

    private boolean isMarcaRequired() {
        return idMarca != null;
    }

    private List<Modelo> getModelos() {
        return modeloService.getModelosByTipoProducto(idTipoProducto);
    }

    private List<Modelo> getModelosByMarca() {
        return modeloService.getModelosByMarcaAndTipoProducto(idMarca, idTipoProducto);
    }

    private List<Modelo> getActiveModelos() {
        return modeloService.getModelosWithRootActiveProductos(idMarca, idTipoProducto);
    }

    private List<Modelo> getHijos(Modelo modelo) {
        return modeloService.getModelosByActiveParent(modelo);
    }

    public List<Map<String, Object>> getResultList() {
        return resultList;
    }

    public void setIdTipoProducto(Integer idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    public Boolean getRoot() {
        return root;
    }

    public void setRoot(Boolean root) {
        this.root = root;
    }

    private interface MapCreator {
        Map<String, Object> createMap(Modelo modelo);
    }

    private class BasicMapCreator implements MapCreator {

        @Override
        public Map<String, Object> createMap(Modelo modelo) {
            Map<String, Object> map = Maps.newLinkedHashMap();
            addBasicInfoToMap(map, modelo);
            if (modelo.isProducible()) {
                map.put("activo", modelo.getProducto().isActivo());
                map.put("hasStock", modelo.getProducto().hasStock());
            } else {
                map.put("children", createModelosList(this, Lists.newArrayList(modelo.getHijos())));
            }
            return map;
        }
    }

    private class ActiveMapCreator implements MapCreator {

        @Override
        public Map<String, Object> createMap(Modelo modelo) {
            Map<String, Object> map = Maps.newLinkedHashMap();
            if (isActive(modelo)) {
                addBasicInfoToMap(map, modelo);
            } else if (!modelo.isLeaf()) {
                List<Map<String, Object>> list = createModelosList(this, getHijos(modelo));
                if (!list.isEmpty()) {
                    map.put("id", modelo.getId());
                    map.put("text", modelo.getNombre());
                    map.put("children", list);
                }
            }
            return map;
        }
    }
}
