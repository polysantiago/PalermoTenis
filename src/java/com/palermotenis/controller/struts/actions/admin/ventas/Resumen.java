/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.ventas;

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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;

/**
 *
 * @author Poly
 */
public class Resumen extends ActionSupport {
    
    private GenericDao<Pago, Integer> pagoService;
    private GenericDao<Stock, Integer> stockService;
    private GenericDao<Listado, String> listadoService;
    private GenericDao<Cliente, Integer> clienteService;
    private Convertor convertor;
    private int clienteId;
    private int pagoId;
    private int cuotas;
    private Listado listado;
    private List<String> stocks = new ArrayList<String>();

    @Override
    public String execute() {

        double total = 0.00;
        listado = new Listado(StringUtility.buildRandomString());
        List<StockListado> stocksListado = new ArrayList<StockListado>();
        Pago pago = pagoService.find(pagoId);

        for (String str : stocks) {
            String[] s = str.split(",");
            Integer stockId = Integer.parseInt(s[0]);
            Integer cantidad = Integer.parseInt(s[1]);

            StockListado stockListado = new StockListado();
            Stock stock = stockService.find(stockId);
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
        listado.setCliente(clienteService.find(clienteId));
        listado.setAutorizado(true);

        try {
            listadoService.create(listado);
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

    public Listado getListado(){
        return listado;
    }

    public void setConvertor(Convertor convertor) {
        this.convertor = convertor;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public void setListadoService(GenericDao<Listado, String> listadoService) {
        this.listadoService = listadoService;
    }

    public void setPagoId(int pagoId) {
        this.pagoId = pagoId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public void setPagoService(GenericDao<Pago, Integer> pagoService) {
        this.pagoService = pagoService;
    }

    public void setClienteService(GenericDao<Cliente, Integer> clienteService) {
        this.clienteService = clienteService;
    }


    public void setStockService(GenericDao<Stock, Integer> stockService) {
        this.stockService = stockService;
    }

    public void setStocks(List<String> stocks) {
        this.stocks = stocks;
    }

}
