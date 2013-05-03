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

    @Override
    @SuppressWarnings("unchecked")
    public List<Object[]> getTiposProductoAndMarcasAndProductoCount() {
        return getEntityManager().createQuery(
            "select t,m, (select count(*) from Producto p where p.tipoProducto = t and p.modelo.marca = m) "
                    + "from TipoProducto t, Marca m order by t.nombre, m.nombre").getResultList();
    }

}
