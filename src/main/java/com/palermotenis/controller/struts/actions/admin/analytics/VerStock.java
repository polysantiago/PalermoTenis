package com.palermotenis.controller.struts.actions.admin.analytics;

import java.io.InputStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.number.CurrencyFormatter;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.util.StringUtility;

/**
 * 
 * @author Poly
 */
public class VerStock extends ActionSupport {

    private static final long serialVersionUID = -6943870064331378208L;

    private final Map<Stock, Collection<? extends Precio>> stocksPrecio = new HashMap<Stock, Collection<? extends Precio>>();

    private static final Locale LOCALE_ES_AR = new Locale("es", "AR");
    private Integer tipoProductoId;
    private Integer marcaId;
    private Integer modeloId;
    private int rows;
    private int page;
    private String sidx;
    private String sord;
    private InputStream inputStream;

    @Autowired
    private GenericDao<Stock, Integer> stockDao;

    @Autowired
    private GenericDao<Marca, Integer> marcaDao;

    @Autowired
    private GenericDao<TipoProducto, Integer> tipoProductoDao;

    @Autowired
    private GenericDao<Modelo, Integer> modeloDao;

    @Autowired
    private CurrencyFormatter currencyFormatter;

    @Override
    public String execute() {
        JSONObject rootObj = new JSONObject();
        JSONArray rowsArr = new JSONArray();
        TipoProducto tp = tipoProductoDao.find(tipoProductoId);
        int records = 0;
        List<Stock> stocks = null;

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("tipoProducto", tp);
        args.put("orderBy", "1");
        Comparator<SimpleEntry<Stock, Precio>> comparator = null;

        if (sidx.equalsIgnoreCase("precio")) {
            comparator = new ValorComparator();
        } else if (sidx.equalsIgnoreCase("pago")) {
            comparator = new PagoComparator();
        } else if (sidx.equalsIgnoreCase("moneda")) {
            comparator = new MonedaComparator();
        } else if (sidx.equalsIgnoreCase("enOferta")) {
            comparator = new EnOfertaComparator();
        } else if (sidx.equalsIgnoreCase("valorOferta")) {
            comparator = new ValorOfertaComparator();
        } else if (sidx.equalsIgnoreCase("valorClasificatorio") && tp.isClasificable()) {
            comparator = new ValorClasificatorioComparator();
        } else if (sidx.equalsIgnoreCase("stock")) {
            comparator = new StockComparator();
        } else {
            if (sidx.contains(",")) {
                String[] as = sidx.split(",");
                as[0] += " " + sord;
                sidx = as[0] + "," + as[1];
            }
            args.put("orderBy", sidx + " " + sord);
        }

        String namedQuery = null;
        String countQuery = null;

        if (modeloId != null) {
            namedQuery = "Modelo-OrderBy";
            countQuery = "Modelo-CountPrecio";
            args.put("modelo", modeloDao.find(modeloId));
            args.remove("tipoProducto");
        } else if (marcaId != null) {
            namedQuery = "TipoProducto,Marca-OrderBy";
            countQuery = "TipoProducto,Marca-CountPrecio";
            args.put("marca", marcaDao.find(marcaId));
        } else {
            namedQuery = "TipoProducto-OrderBy";
            countQuery = "TipoProducto-CountPrecio";
        }

        stocks = stockDao.queryBy(namedQuery, args);
        args.remove("orderBy");
        records = stockDao.getIntResultBy(countQuery, args);

        List<SimpleEntry<Stock, Precio>> objects = new ArrayList<SimpleEntry<Stock, Precio>>();
        boolean hasPrecios = false;
        for (Stock stock : stocks) {
            if (stock.getProducto().getPrecios() != null && !stock.getProducto().getPrecios().isEmpty()) {
                hasPrecios = true;
                for (Precio precio : stock.getProducto().getPrecios()) {
                    objects.add(new SimpleEntry<Stock, Precio>(stock, precio));
                }
            } else {
                objects.add(new SimpleEntry<Stock, Precio>(stock, null));
            }
        }

        objects = objects.subList(rows * (page - 1), ((rows * page) - 1 > records) ? records : (rows * page) - 1);

        if (comparator != null
                && (hasPrecios || comparator instanceof ValorClasificatorioComparator || comparator instanceof StockComparator)) {
            Collections.sort(objects, comparator);
            if (sord.equalsIgnoreCase("desc")) {
                Collections.reverse(objects);
            }
        }

        for (SimpleEntry<Stock, Precio> se : objects) {
            Stock stock = se.getKey();
            Precio precio = se.getValue();

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

            String nombreProducto = "";
            for (Modelo padre : modelo.getPadres()) {
                nombreProducto += padre.getNombre() + " ";
            }
            nombreProducto += modelo.getNombre();
            cellArr.add(nombreProducto);

            if (tipoProducto.isClasificable()) {
                cellArr.add(valorClasificatorio.getTipoAtributo().getNombre() + ": " + valorClasificatorio.getNombre());
            } else {
                cellArr.add("");
            }

            if (stock.getProducto().isPresentable()) {
                cellArr.add(presentacion.getTipoPresentacion().getNombre() + ": " + presentacion.getNombre());
            } else {
                cellArr.add("");
            }

            cellArr.add(sucursal.getNombre());
            cellArr.add(stock.getStock());
            if (precio != null) {
                cellArr.add(precio.getId().getPago().getNombre());
                cellArr.add(precio.getId().getMoneda().getCodigo());
                if (precio.getValor() != null) {
                    cellArr.add(currencyFormatter.print(precio.getValor(), LOCALE_ES_AR));
                } else {
                    cellArr.add("");
                }
                cellArr.add(precio.isEnOferta() ? "Sí" : "No");
                if (precio.isEnOferta() && precio.getValorOferta() != null) {
                    cellArr.add(currencyFormatter.print(precio.getValorOferta(), LOCALE_ES_AR));
                } else {
                    cellArr.add("");
                }
            } else {
                cellArr.add("");
                cellArr.add("");
                cellArr.add("No");
                cellArr.add("");
                cellArr.add("");
            }
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

    public Map<Stock, Collection<? extends Precio>> getStocksPrecio() {
        return stocksPrecio;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    public void setMarcaId(Integer marcaId) {
        this.marcaId = marcaId;
    }

    public void setModeloId(Integer modeloId) {
        this.modeloId = modeloId;
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

    private class ValorComparator implements Comparator<SimpleEntry<Stock, Precio>> {

        @Override
        public int compare(SimpleEntry<Stock, Precio> t, SimpleEntry<Stock, Precio> t1) {
            return t.getValue().getValor().compareTo(t1.getValue().getValor());
        }
    }

    private class PagoComparator implements Comparator<SimpleEntry<Stock, Precio>> {

        @Override
        public int compare(SimpleEntry<Stock, Precio> t, SimpleEntry<Stock, Precio> t1) {
            return t.getValue().getId().getPago().getNombre().compareTo(t1.getValue().getId().getPago().getNombre());
        }
    }

    private class MonedaComparator implements Comparator<SimpleEntry<Stock, Precio>> {

        @Override
        public int compare(SimpleEntry<Stock, Precio> t, SimpleEntry<Stock, Precio> t1) {
            return t
                .getValue()
                .getId()
                .getMoneda()
                .getSimbolo()
                .compareTo(t1.getValue().getId().getMoneda().getSimbolo());
        }
    }

    private class EnOfertaComparator implements Comparator<SimpleEntry<Stock, Precio>> {

        @Override
        public int compare(SimpleEntry<Stock, Precio> t, SimpleEntry<Stock, Precio> t1) {
            return Boolean.valueOf(t.getValue().isEnOferta()).compareTo(t1.getValue().isEnOferta());
        }
    }

    private class ValorOfertaComparator implements Comparator<SimpleEntry<Stock, Precio>> {

        @Override
        public int compare(SimpleEntry<Stock, Precio> t, SimpleEntry<Stock, Precio> t1) {
            return t.getValue().getValorOferta().compareTo(t1.getValue().getValorOferta());
        }
    }

    private class ValorClasificatorioComparator implements Comparator<SimpleEntry<Stock, Precio>> {

        @Override
        @SuppressWarnings("unchecked")
        public int compare(SimpleEntry<Stock, Precio> t, SimpleEntry<Stock, Precio> t1) {
            Comparable<Comparable<?>> c = (Comparable<Comparable<?>>) t.getKey().getValorClasificatorio().getUnidad();
            Comparable<?> c1 = (Comparable<?>) t1.getKey().getValorClasificatorio().getUnidad();
            return c.compareTo(c1);
        }
    }

    private class StockComparator implements Comparator<SimpleEntry<Stock, Precio>> {

        @Override
        public int compare(SimpleEntry<Stock, Precio> o1, SimpleEntry<Stock, Precio> o2) {
            return o1.getKey().getStock().compareTo(o2.getKey().getStock());
        }
    }
}
