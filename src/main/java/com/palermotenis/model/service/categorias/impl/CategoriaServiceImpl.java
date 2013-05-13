package com.palermotenis.model.service.categorias.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Categoria;
import com.palermotenis.model.dao.categorias.CategoriaDao;
import com.palermotenis.model.service.categorias.CategoriaService;

@Service("categoriaService")
@Transactional(propagation = Propagation.REQUIRED, noRollbackFor =
    { NoResultException.class, EntityNotFoundException.class })
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaDao categoriaDao;

    @Override
    public void createNewCategoria(String codigo, String nombre) {
        categoriaDao.create(new Categoria(codigo, nombre));
    }

    @Override
    public Categoria getCategoriaById(Integer categoriaId) {
        return categoriaDao.find(categoriaId);
    }

    @Override
    public Categoria getCategoriaByCodigo(String codigo) throws NoResultException {
        return categoriaDao.findBy("Codigo", "codigo", codigo);
    }

    @Override
    public List<Categoria> getAllCategorias() {
        return categoriaDao.findAll();
    }

}
