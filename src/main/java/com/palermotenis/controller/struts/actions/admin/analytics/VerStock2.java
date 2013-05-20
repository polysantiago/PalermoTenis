package com.palermotenis.controller.struts.actions.admin.analytics;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.model.service.stock.StockService;

public class VerStock2 extends ActionSupport {

    private static final long serialVersionUID = 4782320935871175440L;

    private Integer tipoProductoId;
    private Integer marcaId;
    private Integer modeloId;
    private int rows;
    private int page;
    private String sidx;
    private String sord;

    private final Map<String, Object> resultsMap = Maps.newLinkedHashMap();

    @Autowired
    private StockService stockService;

    @Override
    public String execute() {
        Map<String, Boolean> orderBy = Maps.newLinkedHashMap();
        for (String clause : sidx.split(",")) {
            orderBy.put(clause, "ASC".equalsIgnoreCase(sord) ? true : false);
        }

        int maxResults = rows;
        int firstResult = rows * (page - 1);

        List<Stock> stocks = null;

        if (modeloId != null) {
            stocks = stockService.getStocksByModelo(modeloId, maxResults, firstResult, orderBy);
        } else if (marcaId != null) {
            stocks = stockService.getStocksByTipoProductoAndMarca(tipoProductoId, marcaId, maxResults, firstResult,
                orderBy);
        } else if (tipoProductoId != null) {
            stocks = stockService.getStocksByTipoProducto(tipoProductoId, maxResults, firstResult, orderBy);
        } else {
            stocks = stockService.getAllStocks(maxResults, firstResult, orderBy);
        }

        int records = stocks.size();

        List<Map<String, Object>> rowsArr = Lists.newArrayList();
        for (Stock stock : stocks) {
            Map<String, Object> row = new ImmutableMap.Builder<String, Object>()
                .put("id", stock.getId())
                .put("cell", buildCell(stock))
                .build();
            rowsArr.add(row);
        }
        resultsMap.put("rows", rowsArr);
        resultsMap.put("records", records);
        resultsMap.put("total", (int) Math.ceil((double) records / rows));
        resultsMap.put("page", page);
        return SUCCESS;
    }

    private List<Object> buildCell(Stock stock) {
        Producto producto = stock.getProducto();
        TipoProducto tipoProducto = producto.getTipoProducto();
        Modelo modelo = producto.getModelo();
        Marca marca = modelo.getMarca();
        Presentacion presentacion = stock.getPresentacion();
        Sucursal sucursal = stock.getSucursal();
        ValorClasificatorio valorClasificatorio = stock.getValorClasificatorio();

        List<Object> cellArr = Lists.newArrayList();

        cellArr.add(tipoProducto.getNombre());
        cellArr.add(marca.getNombre());

        StringBuilder nombreProducto = new StringBuilder();
        for (Modelo padre : modelo.getPadres()) {
            nombreProducto.append(padre.getNombre()).append(" > ");
        }
        nombreProducto.append(modelo.getNombre());
        cellArr.add(nombreProducto.toString());

        cellArr.add(tipoProducto.isClasificable() && valorClasificatorio != null ? valorClasificatorio
            .getTipoAtributo()
            .getNombre() + ": " + valorClasificatorio.getNombre() : StringUtils.EMPTY);

        cellArr.add(tipoProducto.isPresentable() && presentacion != null ? presentacion
            .getTipoPresentacion()
            .getNombre() + ": " + presentacion.getNombre() : StringUtils.EMPTY);

        cellArr.add(sucursal.getNombre());
        cellArr.add(stock.getStock());
        return cellArr;
    }

    public void setMarcaId(Integer marcaId) {
        this.marcaId = marcaId;
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

    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    public Map<String, Object> getResultsMap() {
        return resultsMap;
    }

}
