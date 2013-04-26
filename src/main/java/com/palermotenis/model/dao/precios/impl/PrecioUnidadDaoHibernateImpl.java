package com.palermotenis.model.dao.precios.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableMap;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.precios.PrecioUnidad;
import com.palermotenis.model.beans.precios.pks.PrecioPK;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.precios.PrecioUnidadDao;

@Repository("precioUnidadDao")
public class PrecioUnidadDaoHibernateImpl extends AbstractHibernateDao<PrecioUnidad, PrecioPK> implements
    PrecioUnidadDao {

    @Override
    public List<PrecioUnidad> getPrecios(Producto producto) {
        return queryBy("Producto", "producto", producto);
    }

    @Override
    public List<PrecioUnidad> getPrecios(Producto producto, Pago pago) {
        Map<String, Object> args = new ImmutableMap.Builder<String, Object>()
            .put("producto", producto)
            .put("pago", pago)
            .build();
        return queryBy("Producto,Pago", args);
    }

}
