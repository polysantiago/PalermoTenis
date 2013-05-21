package com.palermotenis.model.service.costos.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.compras.Costo;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.proveedores.Proveedor;
import com.palermotenis.model.dao.costos.CostoDao;
import com.palermotenis.model.service.costos.CostoService;
import com.palermotenis.model.service.monedas.MonedaService;
import com.palermotenis.model.service.presentaciones.PresentacionService;
import com.palermotenis.model.service.productos.ProductoService;
import com.palermotenis.model.service.proveedores.ProveedorService;

@Service("costoService")
@Transactional(propagation = Propagation.REQUIRED)
public class CostoServiceImpl implements CostoService {

    @Autowired
    private CostoDao costoDao;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private MonedaService monedaService;

    @Autowired
    private PresentacionService presentacionService;

    @Override
    public void createCosto(Double valor, Integer productoId, Integer monedaId, Integer proveedorId,
            Integer presentacionId) {
        Producto producto = getProducto(productoId);
        Moneda moneda = getMoneda(monedaId);
        Proveedor proveedor = getProveedor(proveedorId);

        Costo costo = new Costo(valor, producto, proveedor, moneda);

        if (producto.getTipoProducto().isPresentable()) {
            Presentacion presentacion = getPresentacion(presentacionId);
            costo.setPresentacion(presentacion);
        }

        costoDao.create(costo);
    }

    @Override
    public void updateCosto(Integer costoId, Double valor, Integer productoId, Integer monedaId, Integer proveedorId,
            Integer presentacionId) {
        Costo costo = getCostoById(costoId);
        Producto producto = getProducto(productoId);
        Moneda moneda = getMoneda(monedaId);
        Proveedor proveedor = getProveedor(proveedorId);

        costo.setProducto(producto);
        costo.setProveedor(proveedor);
        costo.setMoneda(moneda);
        costo.setCosto(valor);

        if (producto.getTipoProducto().isPresentable()) {
            Presentacion presentacion = getPresentacion(presentacionId);
            costo.setPresentacion(presentacion);
        }

        costoDao.edit(costo);
    }

    @Override
    public void deleteCosto(Integer costoId) {
        Costo costo = getCostoById(costoId);
        costoDao.destroy(costo);
    }

    @Override
    public Costo getCostoById(Integer costoId) {
        return costoDao.find(costoId);
    }

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

    private Producto getProducto(Integer productoId) {
        return productoService.getProductById(productoId);
    }

    private Moneda getMoneda(Integer monedaId) {
        return monedaService.getMonedaById(monedaId);
    }

    private Presentacion getPresentacion(Integer presentacionId) {
        return presentacionService.getPresentacionById(presentacionId);
    }

}
