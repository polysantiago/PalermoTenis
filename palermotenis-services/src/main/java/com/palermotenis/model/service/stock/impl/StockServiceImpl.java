package com.palermotenis.model.service.stock.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableMap;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.model.dao.stock.StockDao;
import com.palermotenis.model.service.atributos.AtributoService;
import com.palermotenis.model.service.marcas.MarcaService;
import com.palermotenis.model.service.modelos.ModeloService;
import com.palermotenis.model.service.presentaciones.PresentacionService;
import com.palermotenis.model.service.productos.ProductoService;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;
import com.palermotenis.model.service.stock.StockService;
import com.palermotenis.model.service.valores.ValorService;

@Service("stockService")
@Transactional(propagation = Propagation.REQUIRED)
public class StockServiceImpl implements StockService {

    @Autowired
    private StockDao stockDao;

    @Autowired
    private ValorService valorService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private AtributoService atributoService;

    @Autowired
    private PresentacionService presentacionService;

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private TipoProductoService tipoProductoService;

    @Autowired
    private MarcaService marcaService;

    @Override
    public void createNewStock(Integer productoId, Integer cantidad) {
        createNewStock(productoId, null, cantidad);
    }

    @Override
    public void createNewStock(Integer productoId, Integer valorClasificatorioId, Integer cantidad) {
        Producto producto = getProducto(productoId);
        Stock stock = new Stock();
        stock.setProducto(producto);
        stock.setStock(cantidad);
        if (valorClasificatorioId != null) {
            stock.setValorClasificatorio(valorService.getValorClasificatorioById(valorClasificatorioId));
        }
        stockDao.create(stock);
    }

    @Override
    public void createStock(Producto producto, Sucursal sucursal) {
        createStock(producto, sucursal, null, null);
    }

    @Override
    public void createStock(Producto producto, Sucursal sucursal, ValorClasificatorio valorClasificatorio) {
        createStock(producto, sucursal, null, valorClasificatorio);
    }

    @Override
    public void createStock(Producto producto, Sucursal sucursal, Presentacion presentacion) {
        createStock(producto, sucursal, presentacion, null);
    }

    @Override
    public void createStock(Producto producto, Sucursal sucursal, Presentacion presentacion,
            ValorClasificatorio valorClasificatorio) {
        Stock stock = new Stock(producto, sucursal);
        if (presentacion != null) {
            stock.setPresentacion(presentacion);
        }
        if (valorClasificatorio != null) {
            stock.setValorClasificatorio(valorClasificatorio);
        }
        stockDao.create(stock);
        producto.addStock(stock);
    }

    @Override
    public Stock getStockById(Integer stockId) {
        return stockDao.find(stockId);
    }

    @Override
    public void delete(Stock stock) {
        stockDao.destroy(stock);
    }

    @Override
    public void delete(Integer stockId) {
        Stock stock = getStockById(stockId);
        delete(stock);
    }

    @Override
    public void deleteStockForProduct(Producto producto) {
        for (Stock stock : producto.getStocks()) {
            delete(stock);
        }
    }

    @Override
    public void updateQuantity(Integer stockId, Integer quantity) {
        Stock stock = getStockById(stockId);
        updateQuantity(stock, quantity);
    }

    @Override
    public void updateQuantity(Stock stock, Integer quantity) {
        stock.setStock(quantity);
        stockDao.edit(stock);
    }

    @Override
    public void clearPresentacion(Stock stock) {
        stock.setPresentacion(null);
        stockDao.edit(stock);
    }

    @Override
    public void fixStock(Integer productoId) {
        Producto producto = getProducto(productoId);
        deleteStockForProduct(producto);
        atributoService.persistAtributosClasificatorios(producto);
    }

    @Override
    public Stock getStockByProductoClasificable(Integer productoId) {
        Producto producto = getProducto(productoId);
        return stockDao.findBy("ProductoClasificable", "producto", producto);
    }

    @Override
    public List<Stock> getStocksByProductoClasificable(Integer productoId) {
        Producto producto = getProducto(productoId);
        return stockDao.queryBy("ProductoClasificable", "producto", producto);
    }

    @Override
    public int getSumOfStockByProducto(Integer productoId) {
        Producto producto = getProducto(productoId);
        return stockDao.getIntResultBy("Producto,SumOfStock", "producto", producto);
    }

    @Override
    public List<Stock> getStocksByProductoAndPresentacion(Integer productoId, Integer presentacionId) {
        Producto producto = getProducto(productoId);
        Presentacion presentacion = getPresentacion(presentacionId);
        return stockDao.queryBy("Producto,Presentacion-Active",
            new ImmutableMap.Builder<String, Object>()
                .put("producto", producto)
                .put("presentacion", presentacion)
                .build());
    }

    @Override
    public List<Stock> getStocksByNombre(String nombre, int maxResults) {
        return stockDao.queryBy("Nombre", new ImmutableMap.Builder<String, Object>().put("nombre", nombre).build(),
            maxResults, 0);
    }

    @Override
    public List<Stock> getActiveStocksByNombre(String nombre, int maxResults) {
        return stockDao.queryBy("Nombre-Active", new ImmutableMap.Builder<String, Object>()
            .put("nombre", nombre)
            .build(), maxResults, 0);
    }

    @Override
    public List<Stock> getAllStocks(int maxResults, int firstResult, Map<String, Boolean> orderBy) {
        try {
            return stockDao.getAllStocks(maxResults, firstResult, orderBy);
        } catch (SQLException e) {
            throw new RuntimeException("Bad query", e);
        }
    }

    @Override
    public List<Stock> getStocksByModelo(Integer modeloId, int maxResults, int firstResult, Map<String, Boolean> orderBy) {
        Modelo modelo = modeloService.getModeloById(modeloId);
        try {
            return stockDao.getStocksByModelo(modelo, maxResults, firstResult, orderBy);
        } catch (SQLException e) {
            throw new RuntimeException("Bad query", e);
        }
    }

    @Override
    public List<Stock> getStocksByTipoProducto(Integer tipoProductoId) {
        TipoProducto tipoProducto = tipoProductoService.getTipoProductoById(tipoProductoId);
        return stockDao.queryBy("TipoProducto", "tipoProducto", tipoProducto);
    }

    @Override
    public List<Stock> getStocksByTipoProducto(Integer tipoProductoId, int maxResults, int firstResult,
            Map<String, Boolean> orderBy) {
        TipoProducto tipoProducto = tipoProductoService.getTipoProductoById(tipoProductoId);
        try {
            return stockDao.getStocksByTipoProducto(tipoProducto, maxResults, firstResult, orderBy);
        } catch (SQLException e) {
            throw new RuntimeException("Bad query", e);
        }
    }

    @Override
    public List<Stock> getStocksByTipoProductoAndMarca(Integer tipoProductoId, Integer marcaId, int maxResults,
            int firstResult, Map<String, Boolean> orderBy) {
        TipoProducto tipoProducto = tipoProductoService.getTipoProductoById(tipoProductoId);
        Marca marca = marcaService.getMarcaById(marcaId);
        try {
            return stockDao.getStocksByTipoProductoAndMarca(tipoProducto, marca, firstResult, maxResults, orderBy);
        } catch (SQLException e) {
            throw new RuntimeException("Bad query", e);
        }
    }

    private Producto getProducto(Integer productoId) {
        return productoService.getProductById(productoId);
    }

    private Presentacion getPresentacion(Integer presentacionId) {
        return presentacionService.getPresentacionById(presentacionId);
    }

}
