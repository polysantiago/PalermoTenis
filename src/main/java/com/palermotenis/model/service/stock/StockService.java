package com.palermotenis.model.service.stock;

import java.util.List;
import java.util.Map;

import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;

public interface StockService {

    void delete(Stock stock);

    void delete(Integer stockId);

    void deleteStockForProduct(Producto producto);

    void createNewStock(Integer productoId, Integer cantidad);

    void createNewStock(Integer productoId, Integer valorClasificatorioId, Integer cantidad);

    void createStock(Producto producto, Sucursal sucursal);

    void createStock(Producto producto, Sucursal sucursal, ValorClasificatorio valorClasificatorio);

    void createStock(Producto producto, Sucursal sucursal, Presentacion presentacion);

    void createStock(Producto producto, Sucursal sucursal, Presentacion presentacion,
            ValorClasificatorio valorClasificatorio);

    void updateQuantity(Integer stockId, Integer quantity);

    void updateQuantity(Stock stock, Integer quantity);

    void fixStock(Integer productoId);

    void clearPresentacion(Stock stock);

    Stock getStockById(Integer stockId);

    Stock getStockByProductoClasificable(Integer productoId);

    List<Stock> getStocksByProductoAndPresentacion(Integer productoId, Integer presentacionId);

    List<Stock> getStocksByProductoClasificable(Integer productoId);

    List<Stock> getStocksByNombre(String nombre, int maxResults);

    List<Stock> getActiveStocksByNombre(String nombre, int maxResults);

    List<Stock> getAllStocks(int maxResults, int firstResult, Map<String, Boolean> orderBy);

    List<Stock> getStocksByModelo(Integer modeloId, int maxResults, int firstResult, Map<String, Boolean> orderBy);

    List<Stock> getStocksByTipoProducto(Integer tipoProductoId);

    List<Stock> getStocksByTipoProducto(Integer tipoProductoId, int maxResults, int firstResult,
            Map<String, Boolean> orderBy);

    List<Stock> getStocksByTipoProductoAndMarca(Integer tipoProductoId, Integer marcaId, int maxResults,
            int firstResult, Map<String, Boolean> orderBy);

    int getSumOfStockByProducto(Integer productoId);

}
