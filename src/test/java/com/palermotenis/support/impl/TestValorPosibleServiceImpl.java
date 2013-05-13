package com.palermotenis.support.impl;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.valores.ValorPosible;
import com.palermotenis.model.service.valores.ValorService;
import com.palermotenis.support.TestTipoAtributoService;
import com.palermotenis.support.TestValorPosibleService;

@Service("testValorPosibleService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor =
    { NoResultException.class, EntityNotFoundException.class })
public class TestValorPosibleServiceImpl implements TestValorPosibleService {

    @Autowired
    private ValorService valorService;

    @Autowired
    private TestTipoAtributoService testTipoAtributoService;

    @Override
    public ValorPosible refresh(ValorPosible valorPosible) {
        if (valorPosible != null) {
            return valorService.getValorPosibleById(valorPosible.getId());
        }
        return valorPosible;
    }

    @Override
    @Transactional(readOnly = false)
    public ValorPosible create() {
        return null;
    }

    @Override
    public ValorPosible getAny() {
        // TODO Auto-generated method stub
        return null;
    }

}
