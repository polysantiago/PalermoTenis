package com.palermotenis.support.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.palermotenis.model.beans.Categoria;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoSimple;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.service.productos.ProductoService;
import com.palermotenis.support.TestCategoriaService;
import com.palermotenis.support.TestModeloService;
import com.palermotenis.support.TestProductoService;
import com.palermotenis.support.TestTipoAtributoService;
import com.palermotenis.support.TestTipoProductoService;

@Service("testProductoService")
public class TestProductoServiceImpl implements TestProductoService {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private TestModeloService testModeloService;

    @Autowired
    private TestTipoProductoService testTipoProductoService;

    @Autowired
    private TestTipoAtributoService testTipoAtributoService;

    @Autowired
    private TestCategoriaService testCategoriaService;

    @Override
    public Producto refresh(Producto producto) {
        if (producto != null) {
            return productoService.getProductById(producto.getId());
        }
        return producto;
    }

    @Override
    public Producto create() {
        Modelo modelo = testModeloService.create();
        List<Integer> categoriasIds = Lists.transform(Lists.newArrayList(modelo.getCategorias()),
            new Function<Categoria, Integer>() {
                @Override
                @Nullable
                public Integer apply(@Nullable Categoria categoria) {
                    return categoria.getId();
                }
            });

        TipoAtributoSimple tipoAtributoSimple = testTipoAtributoService.getTipoAtributoSimple();

        Map<Integer, String> atributosSimples = Maps.newHashMap();
        Map<Integer, Integer> atributosTipados = Maps.newHashMap();
        Map<Integer, Collection<String>> atributosMultiples = Maps.newHashMap();

        atributosSimples.put(tipoAtributoSimple.getId(), "TestAtributoSimple");

        productoService.createNewProducto(modelo.getId(), modelo.getTipoProducto().getId(), "TestDescripcion",
            categoriasIds, atributosSimples, atributosTipados, atributosMultiples);

        return productoService.getProductoByModelo(modelo.getId());

    }

    @Override
    public Producto getAny() {
        // TODO Auto-generated method stub
        return null;
    }

}
