package com.palermotenis.support.impl;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Categoria;
import com.palermotenis.model.service.categorias.CategoriaService;
import com.palermotenis.support.TestCategoriaService;

@Service("testCategoriaService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor =
    { NoResultException.class, EntityNotFoundException.class })
public class TestCategoriaServiceImpl implements TestCategoriaService {

    @Autowired
    private CategoriaService categoriaService;

    @Override
    public Categoria refresh(Categoria categoria) {
        if (categoria != null) {
            return categoriaService.getCategoriaById(categoria.getId());
        }
        return categoria;
    }

    @Override
    @Transactional(readOnly = false)
    public Categoria create() {
        String codigo = RandomStringUtils.randomAlphabetic(3).toUpperCase();
        categoriaService.createNewCategoria(codigo, "TestCategoria." + RandomStringUtils.randomAlphabetic(5));
        return categoriaService.getCategoriaByCodigo(codigo);
    }

    @Override
    public Categoria getAny() {
        return categoriaService.getCategoriaByCodigo(TEST_CATEGORIA_CODIGO);
    }

}
