package com.palermotenis.commandline.setup;

import java.util.List;

import javax.persistence.NoResultException;

import com.google.common.collect.ImmutableList;
import com.palermotenis.model.beans.Idioma;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.geograficos.Pais;
import com.palermotenis.model.dao.geograficos.PaisDao;
import com.palermotenis.model.service.geograficos.GeographicService;
import com.palermotenis.model.service.idiomas.IdiomaService;
import com.palermotenis.model.service.monedas.MonedaService;

public class PaisMasterData implements MasterData {

    private final GeographicService geographicService;
    private final PaisDao paisDao;
    private final MonedaService monedaService;
    private final IdiomaService idiomaService;

    public PaisMasterData(GeographicService geographicService, PaisDao paisDao, MonedaService monedaService,
            IdiomaService idiomaService) {
        this.geographicService = geographicService;
        this.paisDao = paisDao;
        this.monedaService = monedaService;
        this.idiomaService = idiomaService;
    }

    @Override
    public void createOrUpdate() {
        Idioma spanish = getIdioma("es");
        Idioma english = getIdioma("en");
        Idioma portuguese = getIdioma("pr");

        Moneda pesos = getMoneda("ARS");
        Moneda dolares = getMoneda("USD");

        List<Pais> paises = new ImmutableList.Builder<Pais>()
            .add(new Pais("DE", "ALEMANIA", english, dolares))
            .add(new Pais("AR", "ARGENTINA", spanish, pesos))
            .add(new Pais("BL", "BOLIVIA", spanish, dolares))
            .add(new Pais("BR", "BRASIL", portuguese, dolares))
            .add(new Pais("CL", "CHILE", spanish, dolares))
            .add(new Pais("ES", "ESPAÑA", spanish, dolares))
            .add(new Pais("US", "ESTADOS UNIDOS", english, dolares))
            .add(new Pais("PG", "PARAGUAY", spanish, dolares))
            .add(new Pais("PU", "PERÚ", spanish, dolares))
            .add(new Pais("UR", "URUGUAY", spanish, dolares))
            .add(new Pais("VE", "VENEZUELA", spanish, dolares))
            .build();

        for (Pais pais : paises) {
            try {
                Pais dbInstance = geographicService.getPaisByCodigo(pais.getCodigo());
                if (!pais.equals(dbInstance)) {
                    dbInstance.setNombre(pais.getNombre());
                    dbInstance.setIdioma(pais.getIdioma());
                    dbInstance.setMoneda(pais.getMoneda());
                    paisDao.edit(dbInstance);
                }
            } catch (NoResultException ex) {
                paisDao.create(pais);
            }
        }
    }

    private Idioma getIdioma(String codigo) {
        return idiomaService.getIdioma(codigo);
    }

    private Moneda getMoneda(String codigo) {
        return monedaService.getMonedaByCodigo(codigo);
    }

}
