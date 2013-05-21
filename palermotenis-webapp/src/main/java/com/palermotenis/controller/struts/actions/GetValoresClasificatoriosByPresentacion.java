package com.palermotenis.controller.struts.actions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.model.service.stock.StockService;

public class GetValoresClasificatoriosByPresentacion extends ActionSupport {

    private static final long serialVersionUID = -6113618729166238111L;

    private Integer productoId;
    private Integer presentacionId;

    @Autowired
    private StockService stockService;

    private final List<ValorClasificatorio> valoresClasificatorios = Lists.newArrayList();

    @Override
    public String execute() {
        for (Stock stock : getStocks()) {
            valoresClasificatorios.add(stock.getValorClasificatorio());
        }
        return SUCCESS;
    }

    private List<Stock> getStocks() {
        return stockService.getStocksByProductoAndPresentacion(productoId, presentacionId);
    }

    public List<ValorClasificatorio> getValoresClasificatorios() {
        return valoresClasificatorios;
    }

    public void setPresentacionId(Integer presentacionId) {
        this.presentacionId = presentacionId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }
}
