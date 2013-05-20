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
    public void createMoneda(Integer contrarioId, String nombre, String simbolo, String codigo) {
        Moneda contrario = getMonedaById(contrarioId);

        Moneda moneda = new Moneda(simbolo, codigo, nombre, contrario);

        monedaDao.create(moneda);
    }

    @Override
    public void updateMoneda(Integer monedaId, Integer contrarioId, String nombre, String simbolo, String codigo) {
        Moneda moneda = getMonedaById(monedaId);
        Moneda contrario = getMonedaById(contrarioId);

        moneda.setNombre(nombre);
        moneda.setSimbolo(simbolo);
        moneda.setCodigo(codigo);

        if (!contrario.equals(moneda.getContrario())) {
            moneda.setContrario(contrario);
        }

        monedaDao.edit(moneda);
    }

    @Override
    public void deleteMoneda(Integer monedaId) {
        Moneda moneda = getMonedaById(monedaId);
        monedaDao.destroy(moneda);
    }

    @Override
    public Moneda getMonedaByCodigo(String codigo) throws NoResultException {
        return monedaDao.findBy("Codigo", "codigo", codigo);
    }

    @Override
    public Moneda getMonedaById(Integer monedaId) {
        return monedaDao.find(monedaId);
    }

    @Override
    public List<Moneda> getAllMonedas() {
        return monedaDao.findAll();
    }

}
