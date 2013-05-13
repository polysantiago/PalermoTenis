package com.palermotenis.commandline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;
import com.palermotenis.commandline.setup.IdiomaMasterData;
import com.palermotenis.commandline.setup.MasterData;
import com.palermotenis.commandline.setup.MonedaMasterData;
import com.palermotenis.commandline.setup.PagoMasterData;
import com.palermotenis.commandline.setup.PaisMasterData;
import com.palermotenis.commandline.setup.ProvinciaMasterData;
import com.palermotenis.commandline.setup.RolMasterData;
import com.palermotenis.commandline.setup.TipoAtributoClasificatorioMetadataMasterData;
import com.palermotenis.commandline.setup.TipoImagenMasterData;
import com.palermotenis.commandline.setup.UnidadMasterData;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoClasificatorioMetadataDao;
import com.palermotenis.model.dao.authorities.RolDao;
import com.palermotenis.model.dao.geograficos.PaisDao;
import com.palermotenis.model.dao.geograficos.ProvinciaDao;
import com.palermotenis.model.dao.idiomas.IdiomaDao;
import com.palermotenis.model.dao.imagenes.TipoImagenDao;
import com.palermotenis.model.dao.monedas.MonedaDao;
import com.palermotenis.model.dao.pagos.PagoDao;
import com.palermotenis.model.dao.unidades.UnidadDao;
import com.palermotenis.model.service.authorities.RolService;
import com.palermotenis.model.service.geograficos.GeographicService;
import com.palermotenis.model.service.idiomas.IdiomaService;
import com.palermotenis.model.service.imagenes.ImagenService;
import com.palermotenis.model.service.monedas.MonedaService;
import com.palermotenis.model.service.pagos.PagoService;
import com.palermotenis.model.service.unidades.UnidadService;

public class CreateOrUpdateMasterData extends AbstractCommandLine {

    @Autowired
    private RolService rolService;

    @Autowired
    private RolDao rolDao;

    @Autowired
    private MonedaService monedaService;

    @Autowired
    private MonedaDao monedaDao;

    @Autowired
    private IdiomaService idiomaService;

    @Autowired
    private IdiomaDao idiomaDao;

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoDao pagoDao;

    @Autowired
    private GeographicService geographicService;

    @Autowired
    private PaisDao paisDao;

    @Autowired
    private ProvinciaDao provinciaDao;

    @Autowired
    private TipoAtributoClasificatorioMetadataDao tipoAtributoClasificatorioMetadataDao;

    @Autowired
    private UnidadService unidadService;

    @Autowired
    private UnidadDao unidadDao;

    @Autowired
    private TipoImagenDao tipoImagenDao;

    @Autowired
    private ImagenService imagenService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    protected int runWithin(ApplicationContext springContext) {
        List<MasterData> masterDataList = new ImmutableList.Builder<MasterData>()
            .add(new RolMasterData(rolService, rolDao))
            .add(new UnidadMasterData(unidadService, unidadDao))
            .add(new TipoImagenMasterData(tipoImagenDao, imagenService))
            .add(new TipoAtributoClasificatorioMetadataMasterData(tipoAtributoClasificatorioMetadataDao))
            .add(new IdiomaMasterData(idiomaService, idiomaDao))
            .add(new MonedaMasterData(monedaDao, monedaService))
            .add(new PagoMasterData(pagoService, pagoDao))
            .add(new PaisMasterData(geographicService, paisDao, monedaService, idiomaService))
            .add(new ProvinciaMasterData(geographicService, provinciaDao))
            .build();

        // create master data
        for (MasterData masterData : masterDataList) {
            masterData.createOrUpdate();
        }

        return EXIT_SUCCESS;
    }

    /***********************************************************************************************
     * 
     * A P P L I C A T I O N - E N T R Y - P O I N T
     * 
     **********************************************************************************************/

    public static void main(String[] args) {
        new CreateOrUpdateMasterData().run(args);
    }

}
