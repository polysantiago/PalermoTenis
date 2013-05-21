package com.palermotenis.model.service.proveedores.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.proveedores.Proveedor;
import com.palermotenis.model.dao.proveedores.ProveedorDao;
import com.palermotenis.model.service.proveedores.ProveedorService;

@Service("proveedorService")
@Transactional(propagation = Propagation.REQUIRED)
public class ProveedorServiceImpl implements ProveedorService {

    @Autowired
    private ProveedorDao proveedorDao;

    @Override
    public void createNewProveedor(String nombre) {
        proveedorDao.create(new Proveedor(nombre));
    }

    @Override
    public void updateProveedor(Integer proveedorId, String nombre) {
        Proveedor proveedor = getProveedorById(proveedorId);
        proveedor.setNombre(nombre);
        proveedorDao.edit(proveedor);
    }

    @Override
    public void deleteProveedor(Integer proveedorId) {
        Proveedor proveedor = getProveedorById(proveedorId);
        proveedorDao.destroy(proveedor);
    }

    @Override
    public Proveedor getProveedorById(Integer proveedorId) {
        return proveedorDao.find(proveedorId);
    }

    @Override
    public List<Proveedor> getAllProveedores() {
        return proveedorDao.findAll();
    }

}
