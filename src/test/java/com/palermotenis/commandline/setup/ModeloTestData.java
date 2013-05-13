package com.palermotenis.commandline.setup;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.palermotenis.model.beans.Categoria;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.service.categorias.CategoriaService;
import com.palermotenis.model.service.marcas.MarcaService;
import com.palermotenis.model.service.modelos.ModeloService;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;

public class ModeloTestData implements MasterData {

    private final ModeloService modeloService;
    private final TipoProductoService tipoProductoService;
    private final MarcaService marcaService;
    private final CategoriaService categoriaService;

    public ModeloTestData(ModeloService modeloService, TipoProductoService tipoProductoService,
            MarcaService marcaService, CategoriaService categoriaService) {
        this.modeloService = modeloService;
        this.tipoProductoService = tipoProductoService;
        this.marcaService = marcaService;
        this.categoriaService = categoriaService;
    }

    @Override
    public void createOrUpdate() {
        Marca testMarca = getTestMarca();
        Categoria testCategoria = getTestCategoria();

        for (TipoProducto tipoProducto : getTiposProductos()) {
            String nombre = "Modelo" + tipoProducto.getNombre().substring(tipoProducto.getNombre().indexOf('_'));
            List<Modelo> modelos = getModelos(testMarca, tipoProducto);
            List<Categoria> categorias = Lists.newArrayList(testCategoria);
            if (CollectionUtils.isEmpty(modelos)) {
                modeloService.createNewModelo(tipoProducto, testMarca, nombre, categorias);
            } else {
                Modelo dbInstance = modelos.get(0);
                if (!StringUtils.equals(dbInstance.getNombre(), nombre)) {
                    modeloService.updateModelo(dbInstance, nombre, categorias);
                }
            }
        }

    }

    private Categoria getTestCategoria() {
        return categoriaService.getCategoriaByCodigo("TST");
    }

    private Marca getTestMarca() {
        return marcaService.getMarcaByNombre("TestMarca");
    }

    private List<TipoProducto> getTiposProductos() {
        return tipoProductoService.getAllTipoProducto();
    }

    private List<Modelo> getModelos(Marca testMarca, TipoProducto tipoProducto) {
        return modeloService.getModelosByMarcaAndTipoProducto(testMarca.getId(), tipoProducto.getId());
    }

}
