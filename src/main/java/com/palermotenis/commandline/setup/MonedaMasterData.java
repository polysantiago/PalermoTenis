package com.palermotenis.commandline.setup;

import java.util.List;

import javax.persistence.NoResultException;

import com.google.common.collect.Lists;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.dao.monedas.MonedaDao;
import com.palermotenis.model.service.monedas.MonedaService;

public class MonedaMasterData implements MasterData {

    private static final Moneda PESOS = new Moneda("$", "ARS", "Pesos");
    private static final Moneda DOLARS = new Moneda("U$S", "USD", "Dólares");

    private static final List<Moneda> ALL_MONEDAS = Lists.newArrayList();

    static {
        ALL_MONEDAS.add(PESOS);
        ALL_MONEDAS.add(DOLARS);
    }

    private final MonedaDao monedaDao;
    private final MonedaService monedaService;

    public MonedaMasterData(MonedaDao monedaDao, MonedaService monedaService) {
        this.monedaDao = monedaDao;
        this.monedaService = monedaService;
    }

    @Override
    public void createOrUpdate() {
        for (Moneda moneda : ALL_MONEDAS) {
            Moneda dbInstance = null;
            try {
                dbInstance = monedaService.findByCodigo(moneda.getCodigo());
                dbInstance.setSimbolo(moneda.getSimbolo());
                dbInstance.setNombre(moneda.getNombre());
                monedaDao.edit(dbInstance);
            } catch (NoResultException ex) {
                monedaDao.create(moneda);
            }
        }
        setContrario(PESOS, DOLARS);
        setContrario(DOLARS, PESOS);
    }

    private void setContrario(Moneda moneda, Moneda contrario) {
        moneda.setContrario(contrario);
        monedaDao.edit(moneda);
    }

}
