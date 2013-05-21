package com.palermotenis.model.states;

import com.palermotenis.model.service.stock.StockService;

public interface State {

    void createStock();

    void setStockService(StockService stockService);

}
