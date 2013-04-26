package com.palermotenis.model.dao.precios.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableMap;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.precios.PrecioPresentacion;
import com.palermotenis.model.beans.precios.pks.PrecioPK;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.precios.PrecioPresentacionDao;

@Repository("precioPresentacionDao")
public class PrecioPresentacionDaoHibernateImpl extends AbstractHibernateDao<PrecioPresentacion, PrecioPK> implements
    PrecioPresentacionDao {

    @Override
    public List<PrecioPresentacion> getPrecios(Producto producto) {
        return query("Producto", "producto", producto);
    }

    @Override
    public List<PrecioPresentacion> getPrecios(Producto producto, Presentacion presentacion) {
        return queryBy("Producto,Presentacion", "producto", producto);
    }

    @Override
    public List<PrecioPresentacion> getPrecios(Producto producto, Presentacion presentacion, Pago pago) {
        Map<String, Object> args = new ImmutableMap.Builder<String, Object>()
            .put("producto", producto)
            .put("presentacion", presentacion)
            .put("pago", pago)
            .build();
        return queryBy("Producto,Pago,Presentacion", args);
    }

}
