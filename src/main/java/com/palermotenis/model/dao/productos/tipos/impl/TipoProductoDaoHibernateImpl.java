package com.palermotenis.model.dao.productos.tipos.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.productos.tipos.TipoProductoDao;

@Repository("tipoProductoDao")
public class TipoProductoDaoHibernateImpl extends AbstractHibernateDao<TipoProducto, Integer> implements
    TipoProductoDao {

    @Override
    public List<TipoProducto> getRootsTipoProducto() {
        return query("Childless");
    }

}
