/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Poly
 */
public class GetValoresClasificatoriosByPresentacion extends ActionSupport {

    private GenericDao<Stock, Integer> stockService;
    private GenericDao<Producto, Integer> productoService;
    private GenericDao<Presentacion, Integer> presentacionService;

    private Integer productoId;
    private Integer presentacionId;

    private InputStream inputStream;

    @Override
    public String execute() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("producto", productoService.find(productoId));
        args.put("presentacion", presentacionService.find(presentacionId));

        List<Stock> stocks = stockService.queryBy("Producto,Presentacion-Active", args);
        JSONArray arr = new JSONArray();
        for (Stock s : stocks) {
            JSONObject jo = new JSONObject();
            ValorClasificatorio v = s.getValorClasificatorio();
            jo.element("id", v.getId());
            jo.element("nombre", v.getNombre());
            arr.add(jo);
        }

        inputStream = StringUtility.getInputString(arr.toString());

        return SUCCESS;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setPresentacionId(Integer presentacionId) {
        this.presentacionId = presentacionId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public void setStockService(GenericDao<Stock, Integer> stockService) {
        this.stockService = stockService;
    }

    public void setPresentacionService(GenericDao<Presentacion, Integer> presentacionService) {
        this.presentacionService = presentacionService;
    }

    public void setProductoService(GenericDao<Producto, Integer> productoService) {
        this.productoService = productoService;
    }
}
