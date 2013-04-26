package com.palermotenis.model.dao.sucursales.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.sucursales.SucursalDao;

@Repository("sucursalDao")
public class SucursalDaoHibernateImpl extends AbstractHibernateDao<Sucursal, Integer> implements SucursalDao {

    @Override
    public List<Sucursal> getAllSucursales() {
        return findAll();
    }

}
