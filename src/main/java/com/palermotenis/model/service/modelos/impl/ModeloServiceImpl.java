package com.palermotenis.model.service.modelos.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Categoria;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.marca.MarcaDao;
import com.palermotenis.model.dao.modelos.ModeloDao;
import com.palermotenis.model.dao.productos.tipos.TipoProductoDao;
import com.palermotenis.model.service.modelos.ModeloService;

@Service("modeloService")
@Transactional(propagation = Propagation.REQUIRED)
public class ModeloServiceImpl implements ModeloService {

    @Autowired
    private ModeloDao modeloDao;

    @Autowired
    private MarcaDao marcaDao;

    @Autowired
    private TipoProductoDao tipoProductoDao;

    @Override
    public void updateModelo(Modelo modelo, String nombre, Collection<Categoria> categorias) {
        if (!StringUtils.equals(nombre, modelo.getNombre())) {
            modelo.setNombre(nombre);
        }
        modelo.setCategorias(categorias);
        modeloDao.edit(modelo);
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
        return tipoProductoDao.find(tipoProductoId);
    }

    private Marca getMarca(int marcaId) {
        return marcaDao.find(marcaId);
    }

}
