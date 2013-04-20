/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.analytics;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author poly
 */
public class VerStock2 extends ActionSupport {

    private InputStream inputStream;
    private GenericDao<Stock, Integer> stockService;
    private GenericDao<Marca, Integer> marcaService;
    private GenericDao<TipoProducto, Integer> tipoProductoService;
    private GenericDao<Modelo, Integer> modeloService;
    private EntityManager entityManager;
    private Integer tipoProductoId;
    private Integer marcaId;
    private Integer modeloId;
    private int rows;
    private int page;
    private String sidx;
    private String sord;

    @Override
    public String execute() {
        TipoProducto tp = tipoProductoId != null ? tipoProductoService.find(tipoProductoId) : null;
        JSONObject rootObj = new JSONObject();
        JSONArray rowsArr = new JSONArray();

        Map<String, Object> args = new HashMap<String, Object>();
        if (tp != null) {
            args.put("tipoProducto", tp);
        }

        if (sidx.contains(",")) {
            String[] as = sidx.split(",");
            as[0] += " " + sord;
            sidx = as[0] + "," + as[1];
        }

        String namedQuery = "Stock.findBy";
        String countQuery = null;

        if (modeloId != null) {
            namedQuery += "Modelo";
            countQuery = "Modelo-Count";
            args.put("modelo", modeloService.find(modeloId));
            args.remove("tipoProducto");
        } else if (marcaId != null) {
            namedQuery += "TipoProducto,Marca";
            countQuery = "TipoProducto,Marca-Count";
            args.put("marca", marcaService.find(marcaId));
        } else if (tipoProductoId != null) {
            namedQuery += "TipoProducto";
            countQuery = "TipoProducto-Count";
        } else {
            namedQuery = "Stock.findAll";
        }



        Session session = (Session) entityManager.getDelegate();
        Query query = session.createQuery(
                session.getNamedQuery(namedQuery).getQueryString() + " ORDER BY " + sidx + " " + sord).setMaxResults(rows).setFirstResult(rows * (page - 1));
        for (Map.Entry<String, ?> entry : args.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        List<Stock> stocks = (List<Stock>) query.list();

        int records = countQuery != null ? stockService.getIntResultBy(countQuery, args) : stockService.count();

        for (Stock stock : stocks) {
            Producto producto = stock.getProducto();
            TipoProducto tipoProducto = producto.getTipoProducto();
            Modelo modelo = producto.getModelo();
            Marca marca = modelo.getMarca();
            Presentacion presentacion = stock.getPresentacion();
            Sucursal sucursal = stock.getSucursal();
            ValorClasificatorio valorClasificatorio = stock.getValorClasificatorio();

            JSONObject row = new JSONObject();
            row.element("id", stock.getId());
            JSONArray cellArr = new JSONArray();

            cellArr.add(tipoProducto.getNombre());
            cellArr.add(marca.getNombre());

            StringBuilder nombreProducto = new StringBuilder();
            for (Modelo padre : modelo.getPadres()) {
                nombreProducto.append(padre.getNombre()).append(" > ");
            }
            nombreProducto.append(modelo.getNombre());
            cellArr.add(nombreProducto.toString());

            cellArr.add(tipoProducto.isClasificable() && valorClasificatorio != null
                    ? valorClasificatorio.getTipoAtributo().getNombre() + ": " + valorClasificatorio.getNombre() : "");

            cellArr.add(tipoProducto.isPresentable() && presentacion != null
                    ? presentacion.getTipoPresentacion().getNombre() + ": " + presentacion.getNombre() : "");

            cellArr.add(sucursal.getNombre());
            cellArr.add(stock.getStock());

            row.element("cell", cellArr);
            rowsArr.add(row);
        }

        rootObj.element("rows", rowsArr);
        rootObj.element("records", records);
        rootObj.element("total", Math.ceil((double) records / rows));
        rootObj.element("page", page);

        inputStream = StringUtility.getInputString(rootObj.toString());

        return SUCCESS;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setMarcaId(Integer marcaId) {
        this.marcaId = marcaId;
    }

    public void setMarcaService(GenericDao<Marca, Integer> marcaService) {
        this.marcaService = marcaService;
    }

    public void setModeloId(Integer modeloId) {
        this.modeloId = modeloId;
    }

    public void setModeloService(GenericDao<Modelo, Integer> modeloService) {
        this.modeloService = modeloService;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public void setStockService(GenericDao<Stock, Integer> stockService) {
        this.stockService = stockService;
    }

    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    public void setTipoProductoService(GenericDao<TipoProducto, Integer> tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}