package com.palermotenis.controller.struts.actions.admin.compras;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.common.collect.Lists;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.compras.ProductoCompra;
import com.palermotenis.model.beans.proveedores.Proveedor;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.service.compras.CompraService;
import com.palermotenis.model.service.proveedores.ProveedorService;
import com.palermotenis.model.service.stock.StockService;
import com.palermotenis.util.StringUtility;

public class RegistrarCompra extends ActionSupport {

    private static final long serialVersionUID = 795333396375900822L;

    private static final Logger LOGGER = Logger.getLogger(RegistrarCompra.class);

    private List<String> stocks = Lists.newArrayList();

    private final Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @Autowired
    private CompraService compraService;

    @Autowired
    private StockService stockService;

    @Autowired
    private ProveedorService proveedorService;

    @Override
    public String execute() {
        List<ProductoCompra> productosCompra = new ArrayList<ProductoCompra>();
        for (String str : stocks) {
            String[] s = str.split(",");
            Integer stockId = Integer.parseInt(s[0]);
            Integer cantidad = Integer.parseInt(s[1]);
            Integer proveedorId = Integer.parseInt(s[2]);
            Double costo = Double.parseDouble(s[3]);

            Stock stock = stockService.getStockById(stockId);
            Proveedor proveedor = proveedorService.getProveedorById(proveedorId);

            String stockName = StringUtility.buildNameFromStock(stock);
            ProductoCompra productoCompra = new ProductoCompra(stockName, costo, cantidad, proveedor, stock);
            productosCompra.add(productoCompra);
        }

        try {
            compraService.registerNewPurchase(usuario, productosCompra);
        } catch (Exception e) {
            LOGGER.error("Error al crear la compra", e);
            return ERROR;
        }

        return SUCCESS;
    }

    public void setStocks(List<String> stocks) {
        this.stocks = stocks;
    }

    public List<String> getStocks() {
        return stocks;
    }

}
