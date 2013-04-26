package com.palermotenis.model.service.productos.tipos.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.productos.tipos.TipoProductoDao;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;

@Service("tipoProductoService")
@Transactional(propagation = Propagation.REQUIRED)
public class TipoProductoServiceImpl implements TipoProductoService {

    @Autowired
    private TipoProductoDao tipoProductoDao;

    @Override
    public TipoProducto getTipoProductoById(Integer tipoProductoId) {
        return tipoProductoDao.find(tipoProductoId);
    }

    @Override
    public List<TipoProducto> getAllTipoProducto() {
        return tipoProductoDao.findAll();
    }

    @Override
    public List<TipoProducto> getRootsTipoProducto() {
        return tipoProductoDao.query("Childless");
    }

}
