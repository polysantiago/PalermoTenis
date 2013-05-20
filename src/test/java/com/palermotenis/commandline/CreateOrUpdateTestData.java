package com.palermotenis.commandline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;

import com.google.common.collect.ImmutableList;
import com.palermotenis.commandline.setup.CategoriaTestData;
import com.palermotenis.commandline.setup.MarcaTestData;
import com.palermotenis.commandline.setup.MasterData;
import com.palermotenis.commandline.setup.ModeloTestData;
import com.palermotenis.commandline.setup.TipoAtributoTestData;
import com.palermotenis.commandline.setup.TipoProductoTestData;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoDao;
import com.palermotenis.model.service.atributos.tipos.TipoAtributoService;
import com.palermotenis.model.service.categorias.CategoriaService;
import com.palermotenis.model.service.marcas.MarcaService;
import com.palermotenis.model.service.modelos.ModeloService;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;
import com.palermotenis.support.TestTipoProductoService;

public class CreateOrUpdateTestData extends AbstractCommandLine {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private TipoProductoService tipoProductoService;

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private TipoAtributoDao tipoAtributoDao;

    @Autowired
    private TipoAtributoService tipoAtributoService;

    @Autowired
    private TestTipoProductoService testTipoProductoService;

    @Override
    protected int runWithin(ApplicationContext springContext) {
        final List<MasterData> masterDataList = new ImmutableList.Builder<MasterData>()
            .add(new TipoProductoTestData(tipoProductoService))
            .add(new MarcaTestData(marcaService))
            .add(new CategoriaTestData(categoriaService))
            .add(new ModeloTestData(modeloService, tipoProductoService, marcaService, categoriaService))
            .add(new TipoAtributoTestData(tipoAtributoDao, tipoAtributoService, testTipoProductoService))
            .build();

        startTransaction();

        for (MasterData masterData : masterDataList) {
            masterData.createOrUpdate();
        }

        commitTransaction();

        return EXIT_SUCCESS;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]
            { "spring/applicationContext-business.xml", "spring/test-auxiliary-services.xml" };
    }

    /***********************************************************************************************
     * 
     * A P P L I C A T I O N - E N T R Y - P O I N T
     * 
     **********************************************************************************************/

    public static void main(String[] args) {
        new CreateOrUpdateTestData().run(args);
    }

}
