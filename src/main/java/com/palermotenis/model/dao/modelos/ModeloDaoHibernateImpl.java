package com.palermotenis.model.dao.modelos;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableMap;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.AbstractHibernateDao;

@Repository("modeloDao")
public class ModeloDaoHibernateImpl extends AbstractHibernateDao<Modelo, Integer> implements ModeloDao {

    @Override
    public List<Modelo> getModelosByTipoProducto(TipoProducto tipoProducto) {
        return queryBy("TipoProducto", "tipoProduco", tipoProducto);
    }

    @Override
    public List<Modelo> getModelosByMarcaAndTipoProducto(Marca marca, TipoProducto tipoProducto) {
        Map<String, Object> args = buildMarcaAndTipoProductoArgs(marca, tipoProducto);
        return queryBy("Marca,TipoProducto", args);
    }

    @Override
    public List<Modelo> getModelosByMarcaAndActiveTipoProducto(Marca marca, TipoProducto tipoProducto) {
        Map<String, Object> args = buildMarcaAndTipoProductoArgs(marca, tipoProducto);
        return queryBy("Marca,TipoProducto-Active", args);
    }

    @Override
    public List<Modelo> getModelosByActiveParent(Modelo parent) {
        return queryBy("Padre-Active", "padre", parent);
    }

    private Map<String, Object> buildMarcaAndTipoProductoArgs(Marca marca, TipoProducto tipoProducto) {
        Map<String, Object> args = new ImmutableMap.Builder<String, Object>()
            .put("tipoProducto", tipoProducto)
            .put("marca", marca)
            .build();
        return args;
    }

}
