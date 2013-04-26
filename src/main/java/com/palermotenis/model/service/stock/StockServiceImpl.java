package com.palermotenis.model.service.stock;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.model.dao.stock.StockDao;
import com.palermotenis.model.service.atributos.AtributoService;
import com.palermotenis.model.service.productos.ProductoService;
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

    private Producto getProducto(Integer productoId) {
        return productoService.getProductById(productoId);
    }

}
