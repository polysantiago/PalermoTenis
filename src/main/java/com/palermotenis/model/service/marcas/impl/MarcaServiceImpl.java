package com.palermotenis.model.service.marcas.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.marca.MarcaDao;
import com.palermotenis.model.service.marcas.MarcaService;

@Service("marcaService")
@Transactional(propagation = Propagation.REQUIRED)
public class MarcaServiceImpl implements MarcaService {

    @Autowired
    private MarcaDao marcaDao;

    @Override
    public List<Marca> getAllMarcas() {
        return marcaDao.findAll();
    }

    @Override
    public List<Marca> getActiveMarcasByTipoProducto(TipoProducto tipoProducto) {
        return marcaDao.getActiveMarcasByTipoProducto(tipoProducto);
    }

    @Override
    public Marca getMarcaById(Integer marcaId) {
        return marcaDao.find(marcaId);
    }

}
