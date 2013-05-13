package com.palermotenis.model.service.monedas;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.dao.monedas.MonedaDao;

@Service("monedaService")
@Transactional(propagation = Propagation.REQUIRED)
public class MonedaServiceImpl implements MonedaService {

    @Autowired
    private MonedaDao monedaDao;

    @Override
    public Moneda findByCodigo(String codigo) throws NoResultException {
        return monedaDao.findBy("Codigo", "codigo", codigo);
    }

    @Override
    public List<Moneda> getAllMonedas() {
        return monedaDao.findAll();
    }

}
