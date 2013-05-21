package com.palermotenis.controller.struts.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.number.CurrencyFormatter;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.compras.Costo;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.service.costos.CostoService;
import com.palermotenis.model.service.pagos.PagoService;
import com.palermotenis.model.service.precios.impl.PrecioService;
import com.palermotenis.model.service.stock.StockService;

public class GetProductosAutoCompleteList extends InputStreamActionSupport {

    private static final int UNA_CUOTA = 1;
    private static final long serialVersionUID = -7155980564935536463L;
    private static final Locale LOCALE_ES_AR = new Locale("es", "AR");

    private String q;
    private int proveedorId;
    private int limit;

    @Autowired
    private PagoService pagoService;

    @Autowired
    private StockService stockService;

    @Autowired
    private CostoService costoService;

    @Autowired
    private CurrencyFormatter currencyFormatter;

    @Autowired
    private PrecioService precioService;

    public String active() {
        writeResponse(buildInformationWithPrecios(getActiveStocks()));
        return SUCCESS;
    }

    public String all() {
        writeResponse(buildInformationWithPrecios(getStocks()));
        return SUCCESS;
    }

    public String allCostos() {
        List<String> stocksStrings = new ArrayList<String>();
        for (Stock stock : getStocks()) {

            List<Costo> costos = stock.getProducto().getTipoProducto().isPresentable() ? getCostosPresentable(stock)
                    : getCostos(stock);

            List<String> stringsList = buildCommonString(stock);

            stringsList.add(CollectionUtils.isEmpty(costos) ? "0.00" : costos.get(0).getCosto().toString());

            stocksStrings.add(Joiner.on('|').join(stringsList));
        }
        writeResponse(Joiner.on("\n").join(stocksStrings));
        return SUCCESS;
    }

    private String buildInformationWithPrecios(List<Stock> stocks) {
        List<String> stocksStrings = new ArrayList<String>();
        for (Stock stock : getStocks()) {
            Precio precio = precioService.estimarPrecio(stock, UNA_CUOTA);

            List<String> stringsList = buildCommonString(stock);

            if (hasPrecio(precio)) {
                Double valor = precioService.calculatePrecioUnitarioPesos(precio);
                stringsList.add(currencyFormatter.print(valor, LOCALE_ES_AR));
            }
            stocksStrings.add(Joiner.on('|').join(stringsList));
        }
        return Joiner.on("\n").join(stocksStrings);
    }

    private List<String> buildCommonString(Stock stock) {
        Producto producto = stock.getProducto();
        Modelo modelo = producto.getModelo();
        Presentacion presentacion = stock.getPresentacion();
        TipoProducto tipoProducto = producto.getTipoProducto();

        List<String> stringsToJoin = new ArrayList<String>();

        stringsToJoin.add(stock.getId().toString());
        stringsToJoin.add(tipoProducto.getNombre());
        stringsToJoin.add(modelo.getMarca().getNombre());

        List<String> nombresPadres = Lists.transform(Lists.newArrayList(modelo.getPadres()),
            new Function<Modelo, String>() {
                @Override
                @Nullable
                public String apply(@Nullable Modelo padre) {
                    return padre.getNombre();
                }
            });
        stringsToJoin.add(Joiner.on(' ').join(nombresPadres));
        stringsToJoin.add(modelo.getNombre());

        if (stock.getPresentacion() != null) {
            stringsToJoin.add(presentacion.getTipoPresentacion().getNombre());
            stringsToJoin.add(presentacion.getNombre());
        }

        if (stock.getValorClasificatorio() != null) {
            stringsToJoin.add(stock.getValorClasificatorio().getTipoAtributo().getNombre());
            stringsToJoin.add(stock.getValorClasificatorio().getNombre());
        }
        return stringsToJoin;
    }

    private List<Costo> getCostos(Stock stock) {
        return costoService.getCostos(stock.getProducto(), proveedorId);
    }

    private List<Costo> getCostosPresentable(Stock stock) {
        return costoService.getCostos(stock.getProducto(), proveedorId, stock.getPresentacion());
    }

    private boolean hasPrecio(Precio precio) {
        return precio != null && (precio.getValor() != null || precio.getValorOferta() != null);
    }

    private List<Stock> getActiveStocks() {
        return stockService.getActiveStocksByNombre("%" + q + "%", limit);
    }

    private List<Stock> getStocks() {
        return stockService.getStocksByNombre("%" + q + "%", limit);
    }

    public void setQ(String q) {
        this.q = q;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

}
