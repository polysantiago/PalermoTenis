package com.palermotenis.model.dao.proveedores;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.proveedores.Proveedor;
import com.palermotenis.model.dao.AbstractHibernateDao;

@Repository("proveedorDao")
public class ProveedorDaoHibernateImpl extends AbstractHibernateDao<Proveedor, Integer> implements ProveedorDao {

}
