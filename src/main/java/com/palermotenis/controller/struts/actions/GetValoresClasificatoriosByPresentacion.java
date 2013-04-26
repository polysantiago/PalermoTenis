package com.palermotenis.controller.struts.actions;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.model.dao.Dao;
import com.palermotenis.util.StringUtility;

/**
 * 
 * @author Poly
 */
public class GetValoresClasificatoriosByPresentacion extends ActionSupport {

    private static final long serialVersionUID = -6113618729166238111L;

    private Integer productoId;
    private Integer presentacionId;

    private InputStream inputStream;

    @Autowired
    private Dao<Stock, Integer> stockDao;

    @Autowired
    private Dao<Producto, Integer> productoDao;

    @Autowired
    private Dao<Presentacion, Integer> presentacionDao;

    @Override
    public String execute() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("producto", productoDao.find(productoId));
        args.put("presentacion", presentacionDao.find(presentacionId));

        List<Stock> stocks = stockDao.queryBy("Producto,Presentacion-Active", args);
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
}
