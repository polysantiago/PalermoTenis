package com.palermotenis.model.beans.productos.tipos;

import com.palermotenis.model.service.stock.StockService;

public interface State {

    void createStock();

    void setStockService(StockService stockService);

}
