package com.palermotenis.model.service.marcas.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
    public void createNewMarca(String nombre) {
        Assert.notNull(nombre);
        Marca marca = new Marca();
        marca.setNombre(nombre);
        marcaDao.create(marca);
    }

    @Override
    public void updateMarca(Integer marcaId, String nombre) {
        Assert.notNull(marcaId);
        Assert.notNull(nombre);
        Marca marca = getMarcaById(marcaId);
        if (!StringUtils.equals(nombre, marca.getNombre())) {
            marca.setNombre(nombre);
            marcaDao.edit(marca);
        }
    }

    @Override
    public void deleteMarca(Integer marcaId) {
        Assert.notNull(marcaId);
        Marca marca = getMarcaById(marcaId);
        marcaDao.destroy(marca);
    }

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
