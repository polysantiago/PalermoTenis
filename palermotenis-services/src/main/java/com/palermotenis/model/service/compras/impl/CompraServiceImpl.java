package com.palermotenis.model.service.compras.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.compras.Compra;
import com.palermotenis.model.beans.compras.ProductoCompra;
import com.palermotenis.model.beans.proveedores.Proveedor;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.dao.compras.CompraDao;
import com.palermotenis.model.service.compras.CompraService;
import com.palermotenis.model.service.proveedores.ProveedorService;
import com.palermotenis.model.service.stock.StockService;
import com.palermotenis.util.StringUtility;

@Service("compraService")
public class CompraServiceImpl implements CompraService {

    @Autowired
    private CompraDao compraDao;

    @Autowired
    private StockService stockService;

    @Autowired
    private ProveedorService proveedorService;

    @Override
    @Transactional
    public void registerNewPurchase(Usuario usuario, List<List<? extends Number>> stocks) {

        Compra compra = new Compra(usuario);

        for (List<? extends Number> list : stocks) {
            Integer stockId = (Integer) list.get(0);
            Integer cantidad = (Integer) list.get(1);
            Integer proveedorId = (Integer) list.get(2);
            Double costo = (Double) list.get(3);

            Stock stock = stockService.getStockById(stockId);
            Proveedor proveedor = proveedorService.getProveedorById(proveedorId);

            String stockName = StringUtility.buildNameFromStock(stock);
            ProductoCompra productoCompra = new ProductoCompra(stockName, costo, cantidad, proveedor, stock);

            compra.addProductoCompra(productoCompra);
        }

        compraDao.create(compra);
    }

}
