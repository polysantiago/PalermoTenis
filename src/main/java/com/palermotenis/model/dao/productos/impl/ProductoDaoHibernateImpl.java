package com.palermotenis.model.dao.productos.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.productos.ProductoDao;

@Repository("productoDao")
public class ProductoDaoHibernateImpl extends AbstractHibernateDao<Producto, Integer> implements ProductoDao {

    @Override
    public List<Producto> getProductosOnSale() {
        return query("Ofertas");
    }

}
