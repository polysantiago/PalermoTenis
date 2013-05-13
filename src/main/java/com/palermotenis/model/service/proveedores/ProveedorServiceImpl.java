package com.palermotenis.model.service.proveedores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.proveedores.Proveedor;
import com.palermotenis.model.dao.proveedores.ProveedorDao;

@Service("proveedorService")
@Transactional(propagation = Propagation.REQUIRED)
public class ProveedorServiceImpl implements ProveedorService {

    @Autowired
    private ProveedorDao proveedorDao;

    @Override
    public Proveedor getProveedorById(Integer proveedorId) {
        return proveedorDao.find(proveedorId);
    }

}
