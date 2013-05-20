package com.palermotenis.commandline.setup;

import java.util.List;

import javax.persistence.NoResultException;

import com.google.common.collect.Lists;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.dao.sucursales.SucursalDao;

public class SucursalMasterData implements MasterData {

    private static final Sucursal PALERMO = new Sucursal("Palermo", "4821 3117 / 4823 0763",
        "Güemes 3372 / Capital Federal");
    private static final Sucursal NUNIEZ = new Sucursal("Nuñez", "4701 1985", "Av. Libertador 7532 / Capital Federal");
    private static final Sucursal DEPOSITO = new Sucursal("Depósito", null, null);

    private static final List<Sucursal> ALL_SUCURSALES = Lists.newArrayList(PALERMO, NUNIEZ, DEPOSITO);

    private final SucursalDao sucursalDao;

    public SucursalMasterData(SucursalDao sucursalDao) {
        this.sucursalDao = sucursalDao;
    }

    @Override
    public void createOrUpdate() {
        for (Sucursal sucursal : ALL_SUCURSALES) {
            try {
                sucursalDao.findBy("Nombre", "nombre", sucursal.getNombre());
            } catch (NoResultException ex) {
                sucursalDao.create(sucursal);
            }
        }
    }

}
