package com.palermotenis.model.service.productos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.palermotenis.model.beans.Categoria;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.atributos.AtributoMultipleValores;
import com.palermotenis.model.beans.atributos.AtributoSimple;
import com.palermotenis.model.beans.atributos.AtributoTipado;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoSimple;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoTipado;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.valores.ValorPosible;
import com.palermotenis.model.dao.atributos.AtributoMultipleValoresDao;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoSimpleDao;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoTipadoDao;
import com.palermotenis.model.dao.categorias.CategoriaDao;
import com.palermotenis.model.dao.productos.ProductoDao;
import com.palermotenis.model.dao.productos.tipos.TipoProductoDao;
import com.palermotenis.model.service.atributos.AtributoService;
import com.palermotenis.model.service.modelos.ModeloService;
import com.palermotenis.model.service.valores.ValorService;

@Service("productoService")
@Transactional(propagation = Propagation.REQUIRED)
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoDao productoDao;

    @Autowired
    private TipoProductoDao tipoProductoDao;

    @Autowired
    private AtributoService atributoService;

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private ValorService valorService;

    @Autowired
    private CategoriaDao categoriaDao;

    @Autowired
    private AtributoMultipleValoresDao atributoMultipleValoresDao;

    @Autowired
    private TipoAtributoTipadoDao tipoAtributoTipadoDao;

    @Autowired
    private TipoAtributoSimpleDao tipoAtributoSimpleDao;

    @Override
    public List<Producto> getProductosOnSale() {
        return productoDao.query("Ofertas");
    }

    @Override
    public List<Producto> getLatest8Ofertas() {
        return productoDao.getProductosInOferta(8);
    }

    @Override
    public void createNewProducto(int modeloId, int tipoProductoId, String descripcion,
            Collection<Integer> categoriasIds, Map<Integer, String> atributosSimples,
            Map<Integer, Integer> atributosTipados, Map<Integer, Collection<String>> atributosMultiples) {
        // Producto
        Producto producto = persistProducto(modeloId, tipoProductoId, descripcion, categoriasIds);

        // Atributos Simples
        persistAtributosSimples(producto, atributosSimples);

        // Atributos Tipados
        persistAtributosTipados(producto, atributosTipados);

        // Atributos Múltiples Valores
        persistAtributosMultiplesValores(producto, atributosMultiples);

        // Atributos Clasificatorios
        persistAtributosClasificatorios(producto);
    }

    private void persistAtributosSimples(Producto producto, Map<Integer, String> atributosSimples) {
        for (Integer tipoAtributoId : atributosSimples.keySet()) {
            persistAtributoSimple(producto, tipoAtributoId, atributosSimples.get(tipoAtributoId));
        }
    }

    private void persistAtributoSimple(Producto producto, Integer tipoAtributoId, String valor) {
        if (!StringUtils.isBlank(valor)) {
            atributoService.persistAtributoSimple(producto, tipoAtributoId, valor);
        }
    }

    private void persistAtributosTipados(Producto producto, Map<Integer, Integer> atributosTipados) {
        for (Integer tipoAtributoId : atributosTipados.keySet()) {
            persistAtributoTipado(producto, tipoAtributoId, atributosTipados.get(tipoAtributoId));
        }
    }

    private void persistAtributoTipado(Producto producto, Integer tipoAtributoId, Integer valorPosibleId) {
        if (valorPosibleId != null) {
            atributoService.persistAtributoTipado(producto, tipoAtributoId, valorPosibleId);
        }
    }

    private void persistAtributosMultiplesValores(Producto producto, Map<Integer, Collection<String>> atributosMultiples) {
        for (Integer tipoAtributoId : atributosMultiples.keySet()) {
            for (String valorPosibleId : atributosMultiples.get(tipoAtributoId)) {
                persistAtributoMultiplesValores(producto, tipoAtributoId, valorPosibleId);
            }
        }
    }

    private void persistAtributoMultiplesValores(Producto producto, Integer tipoAtributoId, String valorPosibleId) {
        atributoService.persistAtributoMultipleValores(producto, tipoAtributoId, Integer.parseInt(valorPosibleId));
    }

    private void persistAtributosClasificatorios(Producto producto) {
        atributoService.persistAtributosClasificatorios(producto);
    }

    private Producto persistProducto(int modeloId, int tipoProductoId, String descripcion,
            Collection<Integer> categoriasIds) {
        Modelo modelo = getModelo(modeloId);
        TipoProducto tipoProducto = tipoProductoDao.find(tipoProductoId);

        Collection<Categoria> categorias = getCategorias(categoriasIds);
        for (Categoria categoria : categorias) {
            modelo.addCategoria(categoria);
        }

        Producto producto = new Producto(descripcion, tipoProducto, modelo);

        productoDao.create(producto);

        return producto;
    }

    private Modelo getModelo(int modeloId) {
        return modeloService.getModeloById(modeloId);
    }

    private Collection<Categoria> getCategorias(Collection<Integer> categoriasIds) {
        Collection<Categoria> categorias = new ArrayList<Categoria>();
        for (Integer categoriaId : categoriasIds) {
            categorias.add(categoriaDao.find(categoriaId));
        }
        return categorias;
    }

    @Override
    public void updateProducto(int modeloId, String nombre, String descripcion, boolean activo,
            Collection<Integer> categoriasIds, Map<Integer, String> atributosSimples,
            Map<Integer, Integer> atributosTipados, Map<Integer, Collection<String>> atributosMultiples) {
        Producto producto = updateProducto(modeloId, nombre, descripcion, activo, categoriasIds);

        // Atributos Simples
        updateAtributosSimples(atributosSimples, producto);

        // Atributos Tipados
        updateAtributosTipados(atributosTipados, producto);

        // Atributos Multiples
        updateAtributosMultiplesValores(producto, atributosMultiples);
    }

    private void updateAtributosSimples(Map<Integer, String> atributosSimples, Producto producto) {
        for (Integer tipoAtributoId : atributosSimples.keySet()) {
            String valor = atributosSimples.get(tipoAtributoId);
            TipoAtributoSimple tipoAtributo = tipoAtributoSimpleDao.getTipoAtributoSimpleById(tipoAtributoId);
            try {
                AtributoSimple atributoSimple = atributoService.getAtributoSimple(producto, tipoAtributo);
                if (!StringUtils.isBlank(valor)) {
                    Object object = ConvertUtils.convert(valor, tipoAtributo.getClase());
                    atributoSimple.getValor().setUnidad(object);
                } else {
                    atributoService.delete(atributoSimple);
                }
            } catch (NoResultException e) {
                persistAtributoSimple(producto, tipoAtributoId, valor);
            }
        }
    }

    private void updateAtributosTipados(Map<Integer, Integer> atributosTipados, Producto producto) {
        for (Integer tipoAtributoId : atributosTipados.keySet()) {
            Integer valorPosibleId = atributosTipados.get(tipoAtributoId);
            TipoAtributoSimple tipoAtributo = getTipoAtributo(tipoAtributoId);
            try {
                AtributoTipado atributoTipado = atributoService.getAtributoTipado(producto, tipoAtributo);
                if (valorPosibleId != null) {
                    ValorPosible valorPosible = valorService.getValorPosibleById(valorPosibleId);
                    atributoService.updateValorPosible(atributoTipado, valorPosible);
                } else {
                    atributoService.delete(atributoTipado);
                }
            } catch (NoResultException e) {
                persistAtributoTipado(producto, tipoAtributoId, valorPosibleId);
            }
        }
    }

    private void updateAtributosMultiplesValores(Producto producto, Map<Integer, Collection<String>> atributosMultiples) {
        Collection<AtributoMultipleValores> atributosMultiplesValores = atributoService
            .getAtributosByProducto(producto);
        if (!CollectionUtils.isEmpty(atributosMultiplesValores)) {
            for (AtributoMultipleValores atributoMultipleValores : atributosMultiplesValores) {
                atributoMultipleValoresDao.destroy(atributoMultipleValores);
            }
        }
        persistAtributosMultiplesValores(producto, atributosMultiples);
    }

    private TipoAtributoTipado getTipoAtributo(Integer tipoAtributoId) {
        return tipoAtributoTipadoDao.getTipoAtributoTipadoById(tipoAtributoId);
    }

    private Producto updateProducto(int modeloId, String nombre, String descripcion, boolean activo,
            Collection<Integer> categoriasIds) {
        Modelo modelo = getModelo(modeloId);
        Producto producto = modelo.getProducto();
        producto.setDescripcion(descripcion);
        producto.setActivo(activo);
        modeloService.updateModelo(modelo, nombre, getCategorias(categoriasIds));
        return producto;
    }

    @Override
    public void delete(Producto producto) {
        productoDao.destroy(producto);
    }

    @Override
    public Producto getProductById(Integer productoId) {
        return productoDao.find(productoId);
    }

}
