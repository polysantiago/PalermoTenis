/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.ventas;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.clientes.Cliente;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.ventas.Listado;
import com.palermotenis.model.beans.ventas.StockListado;
import com.palermotenis.model.beans.ventas.StockListadoPK;
import com.palermotenis.util.Convertor;
import com.palermotenis.util.StringUtility;

/**
 * 
 * @author Poly
 */
public class Resumen extends ActionSupport {

    private static final long serialVersionUID = -205691888898397486L;

    private int clienteId;
    private int pagoId;
    private int cuotas;

    private Listado listado;

    private List<String> stocks = new ArrayList<String>();

    @Autowired
    private GenericDao<Pago, Integer> pagoDao;

    @Autowired
    private GenericDao<Stock, Integer> stockDao;

    @Autowired
    private GenericDao<Listado, String> listadoDao;

    @Autowired
    private GenericDao<Cliente, Integer> clienteDao;

    @Autowired
    private Convertor convertor;

    @Override
    public String execute() {

        double total = 0.00;
        listado = new Listado(StringUtility.buildRandomString());
        List<StockListado> stocksListado = new ArrayList<StockListado>();
        Pago pago = pagoDao.find(pagoId);

        for (String str : stocks) {
            String[] s = str.split(",");
            Integer stockId = Integer.parseInt(s[0]);
            Integer cantidad = Integer.parseInt(s[1]);

            StockListado stockListado = new StockListado();
            Stock stock = stockDao.find(stockId);
            Precio precio = convertor.estimarPrecio(stock, pago, cuotas);

            stockListado.setStockListadoPK(new StockListadoPK(listado, stock));
            stockListado.setCantidad(cantidad);
            stockListado.setPrecioUnitario(convertor.calculatePrecioUnitarioPesos(precio));
            stockListado.setSubtotal(convertor.calculateSubtotalPesos(precio, cantidad));

            stocksListado.add(stockListado);
            total += stockListado.getSubtotal();
        }

        listado.setTotal(total);
        listado.setStockListados(stocksListado);
        listado.setPago(pago);
        listado.setCuotas(cuotas);
        listado.setCodAutorizacion(StringUtility.buildRandomString());
        listado.setCliente(clienteDao.find(clienteId));
        listado.setAutorizado(true);

        try {
            listadoDao.create(listado);
        } catch (HibernateException ex) {
            Logger.getLogger(Resumen.class.getName()).log(Level.SEVERE, null, ex);
            return ERROR;
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
