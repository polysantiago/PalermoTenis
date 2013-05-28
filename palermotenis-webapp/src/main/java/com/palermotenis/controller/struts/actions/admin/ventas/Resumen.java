package com.palermotenis.controller.struts.actions.admin.ventas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.ventas.Listado;
import com.palermotenis.model.service.ventas.VentaService;

public class Resumen extends ActionSupport {

    private static final long serialVersionUID = -205691888898397486L;

    private int clienteId;
    private int pagoId;
    private int cuotas;

    private Listado listado;

    private List<String> stocks = new ArrayList<String>();

    @Autowired
    private VentaService ventaService;

    @Override
    public String execute() {
        Map<Integer, Integer> stocksMap = Maps.newLinkedHashMap();
        for (String stock : stocks) {
            String[] splitted = stock.split(",");
            Integer stockId = Integer.parseInt(splitted[0]);
            Integer cantidad = Integer.parseInt(splitted[1]);
            stocksMap.put(stockId, cantidad);
        }

        try {
            listado = ventaService.registerNewPurchase(pagoId, cuotas, clienteId, stocksMap);
        } catch (Exception ex) {
            Logger.getLogger(Resumen.class.getName()).log(Level.SEVERE, null, ex);
            return ERROR;
        }
        return SUCCESS;
    }

    public List<String> getStocks() {
        return stocks;
    }

    public Listado getListado() {
        return listado;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public void setPagoId(int pagoId) {
        this.pagoId = pagoId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public void setStocks(List<String> stocks) {
        this.stocks = stocks;
    }

}
