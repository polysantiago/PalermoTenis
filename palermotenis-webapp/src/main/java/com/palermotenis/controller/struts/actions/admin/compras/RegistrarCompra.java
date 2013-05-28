package com.palermotenis.controller.struts.actions.admin.compras;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.common.collect.Lists;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.service.compras.CompraService;

public class RegistrarCompra extends ActionSupport {

    private static final long serialVersionUID = 795333396375900822L;

    private static final Logger LOGGER = Logger.getLogger(RegistrarCompra.class);

    private List<String> stocks = Lists.newArrayList();

    private final Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @Autowired
    private CompraService compraService;

    @Override
    public String execute() {
        List<List<? extends Number>> stocksList = Lists.newArrayList();
        for (String stock : stocks) {
            String[] splitted = stock.split(",");
            Integer stockId = Integer.parseInt(splitted[0]);
            Integer cantidad = Integer.parseInt(splitted[1]);
            Integer proveedorId = Integer.parseInt(splitted[2]);
            Double costo = Double.parseDouble(splitted[3]);

            stocksList.add(Lists.newArrayList(stockId, cantidad, proveedorId, costo));
        }

        try {
            compraService.registerNewPurchase(usuario, stocksList);
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
