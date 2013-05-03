package com.palermotenis.model.service.modelos.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.palermotenis.model.beans.Categoria;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.modelos.ModeloDao;
import com.palermotenis.model.service.marcas.MarcaService;
import com.palermotenis.model.service.modelos.ModeloService;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;

@Service("modeloService")
@Transactional(propagation = Propagation.REQUIRED)
public class ModeloServiceImpl implements ModeloService {

    private static final Logger logger = Logger.getLogger(ModeloServiceImpl.class);

    @Autowired
    private ModeloDao modeloDao;

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private TipoProductoService tipoProductoService;

    @Override
    public Integer createNewModelo(Integer padreId, Integer tipoProductoId, Integer marcaId, String nombre) {
        Assert.notNull(nombre, "Nombre cannot be null");

        Modelo modelo = new Modelo();
        if (padreId != null) {
            modelo.setPadre(getModeloById(padreId));
        }

        modelo.setTipoProducto(getTipoProducto(tipoProductoId));
        modelo.setMarca(getMarca(marcaId));
        modelo.setNombre(nombre);

        modeloDao.create(modelo);

        logger.info("Se ha creado " + modelo);

        modelo.setOrden(modelo.getId());
        modeloDao.edit(modelo);

        return modelo.getId();
    }

    @Override
    public void updateModelo(Integer modeloId, String nombre) {
        Modelo modelo = getModeloById(modeloId);
        logger.info("Intentando editar nombre " + modelo + " a " + nombre);
        setNombre(modelo, nombre);
        modeloDao.edit(modelo);
        logger.info("Se ha editado " + modelo + " con éxito");
    }

    @Override
    public void updateModelo(Modelo modelo, String nombre, Collection<Categoria> categorias) {
        setNombre(modelo, nombre);
        modelo.setCategorias(categorias);
        modeloDao.edit(modelo);
    }

    @Override
    public void deleteModelo(Integer modeloId) {
        Modelo modelo = getModeloById(modeloId);
        if (modelo.getProducto() != null && modelo.getProducto().hasPedidos()) {
            throw new IllegalStateException(
                "El producto tiene pedidos asociados.\nPor favor, elimínelos o confírmelos antes de borrar este producto.");
        }
        modeloDao.destroy(modelo);
    }

    private void setNombre(Modelo modelo, String nombre) {
        if (!StringUtils.equals(nombre, modelo.getNombre())) {
            modelo.setNombre(nombre);
        }
    }

    @Override
    public void moveModelo(Integer modeloId, Integer modeloOrden, Integer modeloRgtId, Integer modeloRgtOrden) {
        modeloDao.moveModelo(modeloId, modeloOrden, modeloRgtId, modeloRgtOrden);
    }

    @Override
    public Modelo getModeloById(Integer modeloId) {
        return modeloDao.find(modeloId);
    }

    @Override
    public List<Modelo> getAllModelos() {
        return modeloDao.findAll();
    }

    @Override
    public List<Modelo> getModelosByTipoProducto(int tipoProductoId) {
        return modeloDao.getModelosByTipoProducto(getTipoProducto(tipoProductoId));
    }

    @Override
    public List<Modelo> getModelosByActiveParent(Modelo parent) {
        return modeloDao.getModelosByActiveParent(parent);
    }

    @Override
    public List<Modelo> getModelosByMarcaAndTipoProducto(int marcaId, int tipoProductoId) {
        Marca marca = getMarca(marcaId);
        TipoProducto tipoProducto = getTipoProducto(tipoProductoId);
        return modeloDao.getModelosByMarcaAndTipoProducto(marca, tipoProducto);
    }

    @Override
    public List<Modelo> getModelosByMarcaAndActiveTipoProducto(int marcaId, int tipoProductoId) {
        Marca marca = getMarca(marcaId);
        TipoProducto tipoProducto = getTipoProducto(tipoProductoId);
        return modeloDao.getModelosByMarcaAndActiveTipoProducto(marca, tipoProducto);
    }

    private TipoProducto getTipoProducto(int tipoProductoId) {
        return tipoProductoService.getTipoProductoById(tipoProductoId);
    }

    private Marca getMarca(int marcaId) {
        return marcaService.getMarcaById(marcaId);
    }

}
