package com.palermotenis.model.dao.costos.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableMap;
import com.palermotenis.model.beans.compras.Costo;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.proveedores.Proveedor;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.costos.CostoDao;

@Repository("costoDao")
public class CostoDaoHibernateImpl extends AbstractHibernateDao<Costo, Integer> implements CostoDao {

    @Override
    public List<Costo> getCostosOfProducto(Producto producto, Proveedor proveedor) {
        return queryBy("Producto,Proveedor",
            new ImmutableMap.Builder<String, Object>().put("producto", producto).put("proveedor", proveedor).build());
    }

    @Override
    public List<Costo> getCostosOfProductoPresentable(Producto producto, Proveedor proveedor,
            Presentacion presentacion) {
        return queryBy(
            "Producto,Proveedor,Presentacion",
            new ImmutableMap.Builder<String, Object>()
                .put("producto", producto)
                .put("proveedor", proveedor)
                .put("presentacion", presentacion)
                .build());
    }

}
