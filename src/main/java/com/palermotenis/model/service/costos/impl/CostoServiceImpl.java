package com.palermotenis.model.service.costos.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.compras.Costo;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.proveedores.Proveedor;
import com.palermotenis.model.dao.costos.CostoDao;
import com.palermotenis.model.service.costos.CostoService;
import com.palermotenis.model.service.proveedores.ProveedorService;

@Service("costoService")
@Transactional(propagation = Propagation.REQUIRED)
public class CostoServiceImpl implements CostoService {

    @Autowired
    private CostoDao costoDao;

    @Autowired
    private ProveedorService proveedorService;

    @Override
    public List<Costo> getCostos(Producto producto, Integer proveedorId) {
        Proveedor proveedor = getProveedor(proveedorId);
        return costoDao.getCostosOfProducto(producto, proveedor);
    }

    @Override
    public List<Costo> getCostos(Producto producto, Integer proveedorId, Presentacion presentacion) {
        Proveedor proveedor = getProveedor(proveedorId);
        return costoDao.getCostosOfProductoPresentable(producto, proveedor, presentacion);
    }

    private Proveedor getProveedor(Integer proveedorId) {
        return proveedorService.getProveedorById(proveedorId);
    }

}
