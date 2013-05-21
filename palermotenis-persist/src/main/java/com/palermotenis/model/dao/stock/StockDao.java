package com.palermotenis.model.dao.stock;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.Dao;

public interface StockDao extends Dao<Stock, Integer> {

    List<Stock> getAllStocks(int maxResults, int firstResult, Map<String, Boolean> orderBy) throws SQLException;

    List<Stock> getStocksByModelo(Modelo modelo, int maxResults, int firstResult, Map<String, Boolean> orderBy)
            throws SQLException;

    List<Stock> getStocksByTipoProducto(TipoProducto tipoProducto, int maxResults, int firstResult,
            Map<String, Boolean> orderBy) throws SQLException;

    List<Stock> getStocksByTipoProductoAndMarca(TipoProducto tipoProducto, Marca marca, int maxResults,
            int firstResult, Map<String, Boolean> orderBy) throws SQLException;

}
