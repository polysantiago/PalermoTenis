package com.palermotenis.commandline.setup;

import javax.persistence.NoResultException;

import com.palermotenis.model.service.marcas.MarcaService;
import com.palermotenis.support.TestMarcaService;

public class MarcaTestData implements MasterData {

    private static final String TEST_MARCA = TestMarcaService.TEST_MARCA_NAME;

    private final MarcaService marcaService;

    public MarcaTestData(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @Override
    public void createOrUpdate() {
        try {
            marcaService.getMarcaByNombre(TEST_MARCA);
        } catch (NoResultException ex) {
            marcaService.createNewMarca(TEST_MARCA);
        }
    }

}
