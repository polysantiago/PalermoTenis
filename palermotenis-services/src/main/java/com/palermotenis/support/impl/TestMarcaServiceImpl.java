package com.palermotenis.support.impl;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.service.marcas.MarcaService;
import com.palermotenis.support.TestMarcaService;

@Service("testMarcaService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor =
    { NoResultException.class, EntityNotFoundException.class })
public class TestMarcaServiceImpl implements TestMarcaService {

    @Autowired
    private MarcaService marcaService;

    @Override
    public Marca refresh(Marca marca) {
        if (marca != null) {
            return marcaService.getMarcaById(marca.getId());
        }
        return marca;
    }

    @Override
    @Transactional(readOnly = false)
    public Marca create() {
        String nombre = "Marca." + RandomStringUtils.randomAlphabetic(5);
        marcaService.createNewMarca(nombre);
        return marcaService.getMarcaByNombre(nombre);
    }

    @Override
    public Marca getAny() {
        return marcaService.getMarcaByNombre(TEST_MARCA_NAME);
    }

}
