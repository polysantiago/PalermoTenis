package com.palermotenis.commandline.setup;

import java.util.List;

import javax.persistence.NoResultException;

import com.google.common.collect.Lists;
import com.palermotenis.model.beans.Unidad;
import com.palermotenis.model.dao.unidades.UnidadDao;
import com.palermotenis.model.service.unidades.UnidadService;

public class UnidadMasterData implements MasterData {

    private static final Unidad GRAMOS = new Unidad("grs.", "Gramos");
    private static final Unidad CENTIMETROS = new Unidad("cms.", "Centímetros");
    private static final Unidad MILIMETROS = new Unidad("mm.", "Milímetros");
    private static final Unidad PUNTOS = new Unidad("ptos.", "Puntos");
    private static final Unidad LIBRAS = new Unidad("lbs.", "Libras");
    private static final Unidad METROS = new Unidad("mts.", "Metros");
    private static final Unidad LITROS = new Unidad("litros", "Litros");
    private static final Unidad CENTIMETROS_CUADRADOS = new Unidad("cms 2", "Centímetros cuadrados");
    private static final Unidad PULGADAS_CUADRADAS = new Unidad("in 2", "Pulgadas cuadradas");

    private static final List<Unidad> ALL_UNIDADES = Lists.newArrayList();

    static {
        ALL_UNIDADES.add(CENTIMETROS);
        ALL_UNIDADES.add(CENTIMETROS_CUADRADOS);
        ALL_UNIDADES.add(METROS);
        ALL_UNIDADES.add(MILIMETROS);
        ALL_UNIDADES.add(GRAMOS);
        ALL_UNIDADES.add(LIBRAS);
        ALL_UNIDADES.add(LITROS);
        ALL_UNIDADES.add(PULGADAS_CUADRADAS);
        ALL_UNIDADES.add(PUNTOS);
    }

    private final UnidadService unidadService;
    private final UnidadDao unidadDao;

    public UnidadMasterData(UnidadService unidadService, UnidadDao unidadDao) {
        this.unidadService = unidadService;
        this.unidadDao = unidadDao;
    }

    @Override
    public void createOrUpdate() {
        for (Unidad unidad : ALL_UNIDADES) {
            try {
                Unidad dbInstance = unidadService.getUnidadByNombre(unidad.getNombre());
                dbInstance.setDescripcion(unidad.getDescripcion());
            } catch (NoResultException ex) {
                unidadDao.create(unidad);
            }
        }
    }

}
