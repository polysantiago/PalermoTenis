/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.model.beans.productos.tipos;

import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Stock;

/**
 *
 * @author poly
 */
public interface State {

    void createStock();

    void setStockDao(GenericDao<Stock, Integer> stockService);

}
