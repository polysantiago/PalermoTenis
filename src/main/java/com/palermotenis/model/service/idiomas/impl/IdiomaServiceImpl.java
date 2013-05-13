package com.palermotenis.model.service.idiomas.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Idioma;
import com.palermotenis.model.dao.idiomas.IdiomaDao;
import com.palermotenis.model.service.idiomas.IdiomaService;

@Service("idiomaService")
@Transactional(propagation = Propagation.REQUIRED)
public class IdiomaServiceImpl implements IdiomaService {

    @Autowired
    private IdiomaDao idiomaDao;

    @Override
    public Idioma getIdioma(String codigo) {
        return idiomaDao.findBy("Codigo", "codigo", codigo);
    }

}
