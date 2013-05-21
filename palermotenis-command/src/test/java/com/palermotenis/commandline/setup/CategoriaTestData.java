package com.palermotenis.commandline.setup;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.service.categorias.CategoriaService;
import com.palermotenis.support.TestCategoriaService;

public class CategoriaTestData implements MasterData {

    private static final String DEFAULT_NOMBRE = TestCategoriaService.TEST_CATEGORIA_NAME;
    private static final String DEFAULT_CODIGO = TestCategoriaService.TEST_CATEGORIA_CODIGO;

    private final CategoriaService categoriaService;

    public CategoriaTestData(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Override
    @Transactional(noRollbackFor =
        { NoResultException.class, EntityNotFoundException.class }, readOnly = false)
    public void createOrUpdate() {
        try {
            categoriaService.getCategoriaByCodigo(DEFAULT_CODIGO);
        } catch (NoResultException ex) {
            categoriaService.createNewCategoria(DEFAULT_CODIGO, DEFAULT_NOMBRE);
        }
    }

}
