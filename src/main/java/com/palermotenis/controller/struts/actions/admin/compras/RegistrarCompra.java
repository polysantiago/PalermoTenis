/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.controller.struts.actions.admin.compras;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.controller.struts.actions.admin.ventas.ConfirmarVenta;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.compras.Compra;
import com.palermotenis.model.beans.compras.ProductoCompra;
import com.palermotenis.model.beans.proveedores.Proveedor;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.util.StringUtility;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Poly
 */
public class RegistrarCompra extends ActionSupport {

    private List<String> stocks = new ArrayList<String>();
    private GenericDao<Stock, Integer> stockService;
    private GenericDao<Proveedor, Integer> proveedorService;
    private GenericDao<Compra, Integer> compraService;

    private Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private static final Logger logger = Logger.getLogger(ConfirmarVenta.class);

    @Override
    public String execute(){

        Compra compra = new Compra();
        List<ProductoCompra> productosCompra = new ArrayList<ProductoCompra>();

        for(String str : stocks){
            String[] s = str.split(",");
            Integer stockId = Integer.parseInt(s[0]);
            Integer cantidad = Integer.parseInt(s[1]);
            Integer proveedorId = Integer.parseInt(s[2]);
            Double costo = Double.parseDouble(s[3]);
            
            Stock stock = stockService.find(stockId);
            Proveedor proveedor = proveedorService.find(proveedorId);

            ProductoCompra pc = new ProductoCompra(StringUtility.buildNameFromStock(stock),
                    costo, cantidad, compra, proveedor, stock);
            productosCompra.add(pc);
        }
        compra.setUsuario(usuario);
        compra.setProductosCompra(productosCompra);

        try {
        compraService.create(compra);
        } catch (Exception e){
            logger.error("Error al crear la compra", e);
            return ERROR;
        }

        return SUCCESS;
    }

    public void setCompraService(GenericDao<Compra, Integer> compraService) {
        this.compraService = compraService;
    }

    public void setProveedorService(GenericDao<Proveedor, Integer> proveedorService) {
        this.proveedorService = proveedorService;
    }

    public void setStockService(GenericDao<Stock, Integer> stockService) {
        this.stockService = stockService;
    }

    public void setStocks(List<String> stocks) {
        this.stocks = stocks;
    }

    public List<String> getStocks() {
        return stocks;
    }

}
