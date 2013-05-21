package com.palermotenis.model.dao.proveedores.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.proveedores.Proveedor;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.proveedores.ProveedorDao;

@Repository("proveedorDao")
public class ProveedorDaoHibernateImpl extends AbstractHibernateDao<Proveedor, Integer> implements ProveedorDao {

}
