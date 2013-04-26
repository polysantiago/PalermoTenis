package com.palermotenis.model.service.sucursales.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.sucursales.SucursalDao;
import com.palermotenis.model.service.atributos.AtributoService;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;
import com.palermotenis.model.service.sucursales.SucursalService;

@Service("sucursalService")
@Transactional(propagation = Propagation.REQUIRED)
public class SucursalServiceImpl implements SucursalService {

    private static final Logger logger = Logger.getLogger(SucursalServiceImpl.class);

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    private TipoProductoService tipoProductoService;

    @Autowired
    private AtributoService atributoService;

    @Override
    public List<Sucursal> getAllSucursales() {
        return sucursalDao.getAllSucursales();
    }

    @Override
    public void createNewSucursal(String nombre, String direccion, String telefono) {
        Sucursal sucursal = null;
        try {
            sucursal = new Sucursal(nombre, telefono, direccion);
            sucursalDao.create(sucursal);
            for (TipoProducto tipoProducto : tipoProductoService.getAllTipoProducto()) {
                for (Producto producto : tipoProducto.getProductos()) {
                    atributoService.persistAtributosClasificatorios(producto, sucursal);
                }
            }
        } catch (Exception ex) {
            logger.error("Error creating sucursal [" + nombre + "]", ex);
            sucursalDao.destroy(sucursal);
        }
    }

    @Override
    public void updateSucursal(Integer sucursalId, String nombre, String direccion, String telefono) {
        Sucursal sucursal = getSucursal(sucursalId);
        sucursal.setNombre(nombre);
        if (StringUtils.isNotBlank(direccion)) {
            sucursal.setDireccion(direccion);
        }
        if (StringUtils.isNotBlank(telefono)) {
            sucursal.setTelefono(telefono);
        }
        sucursalDao.edit(sucursal);
    }

    private Sucursal getSucursal(Integer sucursalId) {
        return sucursalDao.find(sucursalId);
    }

    @Override
    public void deleteSucursal(Integer sucursalId) {
        Sucursal sucursal = getSucursal(sucursalId);
        sucursalDao.destroy(sucursal);
    }

}
