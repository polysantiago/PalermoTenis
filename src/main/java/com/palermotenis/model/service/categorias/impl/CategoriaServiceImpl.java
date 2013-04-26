package com.palermotenis.model.service.categorias.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Categoria;
import com.palermotenis.model.dao.categorias.CategoriaDao;
import com.palermotenis.model.service.categorias.CategoriaService;

@Service("categoriaService")
@Transactional(propagation = Propagation.REQUIRED)
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaDao categoriaDao;

    @Override
    public Categoria getCategoriaById(Integer categoriaId) {
        return categoriaDao.find(categoriaId);
    }

    @Override
    public List<Categoria> getAllCategorias() {
        return categoriaDao.findAll();
    }

}
