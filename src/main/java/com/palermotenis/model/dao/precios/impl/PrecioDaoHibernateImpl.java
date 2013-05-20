package com.palermotenis.model.dao.precios.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.precios.PrecioPresentacion;
import com.palermotenis.model.beans.precios.PrecioUnidad;
import com.palermotenis.model.beans.precios.pks.PrecioPK;
import com.palermotenis.model.beans.precios.pks.PrecioPresentacionPK;
import com.palermotenis.model.beans.precios.pks.PrecioProductoPK;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.precios.PrecioDao;
import com.palermotenis.model.dao.precios.PrecioPresentacionDao;
import com.palermotenis.model.dao.precios.PrecioUnidadDao;

@Repository("precioDao")
public class PrecioDaoHibernateImpl extends AbstractHibernateDao<Precio, PrecioPK> implements PrecioDao {

    @Autowired
    private PrecioUnidadDao precioUnidadDao;

    @Autowired
    private PrecioPresentacionDao precioPresentacionDao;

    @Override
    public Precio find(PrecioPK id) {
        if (id instanceof PrecioProductoPK) {
            return precioUnidadDao.find(id);
        } else if (id instanceof PrecioPresentacionPK) {
            return precioPresentacionDao.find(id);
        }
        return null;
    }

    @Override
    public void create(Precio precio) {
        if (precio instanceof PrecioUnidad) {
            precioUnidadDao.create((PrecioUnidad) precio);
        } else if (precio instanceof PrecioPresentacion) {
            precioPresentacionDao.create((PrecioPresentacion) precio);
        }
    }

    @Override
    public void edit(Precio precio) {
        if (precio instanceof PrecioUnidad) {
            precioUnidadDao.edit((PrecioUnidad) precio);
        } else if (precio instanceof PrecioPresentacion) {
            precioPresentacionDao.edit((PrecioPresentacion) precio);
        }
    }

    @Override
    public void destroy(Precio precio) {
        if (precio instanceof PrecioUnidad) {
            precioUnidadDao.destroy((PrecioUnidad) precio);
        } else if (precio instanceof PrecioPresentacion) {
            precioPresentacionDao.destroy((PrecioPresentacion) precio);
        }
    }

    @Override
    @Transactional
    public void moveOffer(Integer productoId, Integer productoOrden, Integer productoRgtId, Integer productoRgtOrden) {
        getEntityManager()
            .createNativeQuery("{call moverOferta(?,?,?,?)}")
            .setParameter(1, productoId)
            .setParameter(2, productoOrden)
            .setParameter(3, productoRgtId)
            .setParameter(4, productoRgtOrden)
            .executeUpdate();
    }

}
