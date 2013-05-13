package com.palermotenis.commandline.setup;

import java.util.List;

import javax.persistence.NoResultException;

import com.google.common.collect.ImmutableList;
import com.palermotenis.model.beans.geograficos.Pais;
import com.palermotenis.model.beans.geograficos.Provincia;
import com.palermotenis.model.dao.geograficos.ProvinciaDao;
import com.palermotenis.model.service.geograficos.GeographicService;

public class ProvinciaMasterData implements MasterData {

    private final GeographicService geographicService;
    private final ProvinciaDao provinciaDao;

    public ProvinciaMasterData(GeographicService geographicService, ProvinciaDao provinciaDao) {
        this.geographicService = geographicService;
        this.provinciaDao = provinciaDao;
    }

    @Override
    public void createOrUpdate() {
        Pais argentina = getPais("AR");
        Pais alemania = getPais("DE");
        Pais bolivia = getPais("BL");
        Pais brasil = getPais("BR");
        Pais chile = getPais("CL");
        Pais espania = getPais("ES");
        Pais eeuu = getPais("US");
        Pais paraguay = getPais("PG");
        Pais peru = getPais("PU");
        Pais uruguay = getPais("UR");
        Pais venezuela = getPais("VE");

        List<Provincia> provincias = new ImmutableList.Builder<Provincia>()
            .add(new Provincia("Asunción", paraguay))
            .add(new Provincia("Berlín", alemania))
            .add(new Provincia("Capital Federal", argentina))
            .add(new Provincia("Cachapoal", chile))
            .add(new Provincia("Caracas", venezuela))
            .add(new Provincia("Catamarca", argentina))
            .add(new Provincia("Chaco", argentina))
            .add(new Provincia("Chubut", argentina))
            .add(new Provincia("Colonia", uruguay))
            .add(new Provincia("Córdoba", argentina))
            .add(new Provincia("Corrientes", argentina))
            .add(new Provincia("Entre Ríos", argentina))
            .add(new Provincia("Formosa", argentina))
            .add(new Provincia("Jujuy", argentina))
            .add(new Provincia("La Pampa", argentina))
            .add(new Provincia("La Rioja", argentina))
            .add(new Provincia("Lima", peru))
            .add(new Provincia("Madrid", espania))
            .add(new Provincia("Maldonado", uruguay))
            .add(new Provincia("Mendoza", argentina))
            .add(new Provincia("Misiones", argentina))
            .add(new Provincia("Montevideo", uruguay))
            .add(new Provincia("Neuquén", argentina))
            .add(new Provincia("Nueva York", eeuu))
            .add(new Provincia("Paraná", brasil))
            .add(new Provincia("Paysandú", uruguay))
            .add(new Provincia("Pontevedra", espania))
            .add(new Provincia("Buenos Aires", argentina))
            .add(new Provincia("Río De Janeiro", brasil))
            .add(new Provincia("Río Gde. Do Sul", brasil))
            .add(new Provincia("Río Negro", argentina))
            .add(new Provincia("Santiago Del Estero", argentina))
            .add(new Provincia("Salta", argentina))
            .add(new Provincia("San Juan", argentina))
            .add(new Provincia("San Luis", argentina))
            .add(new Provincia("San Pablo", brasil))
            .add(new Provincia("Santiago De Chile", chile))
            .add(new Provincia("Santa Catarina", brasil))
            .add(new Provincia("Santa Cruz", argentina))
            .add(new Provincia("Santa Cruz", bolivia))
            .add(new Provincia("Santa Fé", argentina))
            .add(new Provincia("Tierra Del Fuego", argentina))
            .add(new Provincia("Tucumán", argentina))
            .add(new Provincia("Valparaíso", chile))
            .add(new Provincia("Vizcaya", espania))
            .add(new Provincia("Río Negro", uruguay))
            .build();

        for (Provincia provincia : provincias) {
            try {
                Provincia provinciaByNombre = geographicService.getProvinciaByNombre(provincia.getNombre());
                if (!provincia.getPais().equals(provinciaByNombre.getPais())) {
                    provinciaByNombre.setPais(provincia.getPais());
                    provinciaDao.edit(provinciaByNombre);
                }
            } catch (NoResultException ex) {
                provinciaDao.create(provincia);
            }
        }
    }

    private Pais getPais(String codigo) {
        return geographicService.getPaisByCodigo(codigo);
    }
}
