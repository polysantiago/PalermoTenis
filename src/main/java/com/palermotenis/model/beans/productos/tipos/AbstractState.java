/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.palermotenis.model.beans.productos.tipos;

import java.util.ArrayList;
import java.util.List;

import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.productos.Producto;

/**
 * 
 * @author poly
 */
public abstract class AbstractState implements State {

    private final Producto producto;
    private final List<List<?>> list = new ArrayList<List<?>>();

    private GenericDao<Stock, Integer> stockDao;

    public AbstractState(Producto producto) {
        this.producto = producto;
    }

    @Override
    public void createStock() {
        for (List<?> l : cartesianProduct(list)) {
            for (Object o : l) {
                create(o);
            }
        }
    }

    protected abstract void create(Object obj);

    protected void addToList(List<?> l) {
        list.add(l);
    }

    protected Producto getProducto() {
        return producto;
    }

    protected static List<List<Object>> cartesianProduct(List<List<?>> lists) {
        if (lists.size() < 2) {
            List<List<Object>> ret = new ArrayList<List<Object>>();
            ret.add((List<Object>) lists.get(0));
            return ret;
        }
        return cartesianProduct(0, lists);
    }

    protected static List<List<Object>> cartesianProduct(int index, List<List<?>> lists) {
        List<List<Object>> ret = new ArrayList<List<Object>>();
        if (index == lists.size()) {
            ret.add(new ArrayList<Object>());
        } else {
            for (Object obj : lists.get(index)) {
                for (List<Object> list : cartesianProduct(index + 1, lists)) {
                    list.add(obj);
                    ret.add(list);
                }
            }
        }
        return ret;
    }

    public GenericDao<Stock, Integer> getStockDao() {
        return stockDao;
    }

    @Override
    public void setStockDao(GenericDao<Stock, Integer> stockDao) {
        this.stockDao = stockDao;
    }

}
