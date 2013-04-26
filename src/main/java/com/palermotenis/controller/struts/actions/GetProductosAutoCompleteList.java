package com.palermotenis.controller.struts.actions;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.number.CurrencyFormatter;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.compras.Costo;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.proveedores.Proveedor;
import com.palermotenis.model.dao.Dao;
import com.palermotenis.model.service.precios.impl.PrecioService;
import com.palermotenis.util.StringUtility;

public class GetProductosAutoCompleteList extends ActionSupport {

    private static final long serialVersionUID = -7155980564935536463L;
    private static final Locale LOCALE_ES_AR = new Locale("es", "AR");

    private InputStream inputStream;
    private String q;
    private int proveedorId;
    private int limit;

    @Autowired
    private Dao<Pago, Integer> pagoDao;

    @Autowired
    private Dao<Stock, Integer> stockDao;

    @Autowired
    private Dao<Costo, Integer> costoDao;

    @Autowired
    private Dao<Proveedor, Integer> proveedorDao;

    @Autowired
    private CurrencyFormatter currencyFormatter;

    @Autowired
    private PrecioService precioService;

    public String active() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("nombre", "%" + q + "%");

        List<Stock> stocks = stockDao.queryBy("Nombre-Active", args, limit, 0);
        StringBuilder sb = new StringBuilder();
        for (Stock s : stocks) {
            Producto producto = s.getProducto();
            Modelo modelo = producto.getModelo();
            Presentacion presentacion = s.getPresentacion();
            Precio precio = precioService.estimarPrecio(s, pagoDao.find(1), 1);

            sb.append(s.getId()).append('|');
            sb.append(producto.getTipoProducto().getNombre()).append('|');
            sb.append(modelo.getMarca().getNombre()).append('|');
            for (Modelo m : modelo.getPadres()) {
                sb.append(m.getNombre()).append(" ");
            }
            sb.append(modelo.getNombre()).append('|');
            if (presentacion != null) {
                sb.append(presentacion.getTipoPresentacion().getNombre()).append('|');
                sb.append(presentacion.getNombre()).append('|');
            } else {
            }
            if (s.getValorClasificatorio() != null) {
                sb.append(s.getValorClasificatorio().getTipoAtributo().getNombre()).append(':').append('|');
                sb.append(s.getValorClasificatorio().getNombre()).append('|');
            }
            if (precio != null && (precio.getValor() != null || precio.getValorOferta() != null)) {
                Double valor = precioService.calculatePrecioUnitarioPesos(precio);
                sb.append(currencyFormatter.print(valor, LOCALE_ES_AR)).append('|');
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("\n");
        }
        inputStream = StringUtility.getInputString(sb.toString());

        return SUCCESS;
    }

    public String all() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("nombre", "%" + q + "%");

        List<Stock> stocks = stockDao.queryBy("Nombre", args, limit, 0);
        StringBuilder sb = new StringBuilder();
        for (Stock s : stocks) {
            Producto producto = s.getProducto();
            Modelo modelo = producto.getModelo();
            Presentacion presentacion = s.getPresentacion();
            Precio precio = precioService.estimarPrecio(s, pagoDao.find(1), 1);

            sb.append(s.getId()).append('|');
            sb.append(producto.getTipoProducto().getNombre()).append('|');
            sb.append(modelo.getMarca().getNombre()).append('|');
            for (Modelo m : modelo.getPadres()) {
                sb.append(m.getNombre()).append(" ");
            }
            sb.append(modelo.getNombre()).append('|');
            if (presentacion != null) {
                sb.append(presentacion.getTipoPresentacion().getNombre()).append('|');
                sb.append(presentacion.getNombre()).append('|');
            } else {
            }
            if (s.getValorClasificatorio() != null) {
                sb.append(s.getValorClasificatorio().getTipoAtributo().getNombre()).append(':').append('|');
                sb.append(s.getValorClasificatorio().getNombre()).append('|');
            }
            if (precio != null && (precio.getValor() != null || precio.getValorOferta() != null)) {
                sb.append(precioService.calculatePrecioUnitarioPesos(precio)).append('|');
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("\n");
        }
        inputStream = StringUtility.getInputString(sb.toString());

        return SUCCESS;
    }

    public String allCostos() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("nombre", "%" + q + "%");

        List<Stock> stocks = stockDao.queryBy("Nombre", args, limit, 0);
        Proveedor proveedor = proveedorDao.find(proveedorId);
        StringBuilder sb = new StringBuilder();
        for (Stock s : stocks) {
            Map<String, Object> costoArgs = new HashMap<String, Object>();

            Producto producto = s.getProducto();
            Modelo modelo = producto.getModelo();
            Presentacion presentacion = s.getPresentacion();
            costoArgs.put("producto", s.getProducto());
            costoArgs.put("proveedor", proveedor);
            List<Costo> costos = null;
            if (presentacion != null) {
                costoArgs.put("presentacion", presentacion);
                costos = costoDao.queryBy("Producto,Proveedor,Presentacion", costoArgs);
            } else {
                costos = costoDao.queryBy("Producto,Proveedor", costoArgs);
            }

            sb.append(s.getId()).append('|');
            sb.append(producto.getTipoProducto().getNombre()).append('|');
            sb.append(modelo.getMarca().getNombre()).append('|');
            for (Modelo m : modelo.getPadres()) {
                sb.append(m.getNombre()).append(" ");
            }
            sb.append(modelo.getNombre()).append('|');
            if (presentacion != null) {
                sb.append(presentacion.getTipoPresentacion().getNombre()).append('|');
                sb.append(presentacion.getNombre()).append('|');
            } else {
            }
            if (s.getValorClasificatorio() != null) {
                sb.append(s.getValorClasificatorio().getTipoAtributo().getNombre()).append(':').append('|');
                sb.append(s.getValorClasificatorio().getNombre()).append('|');
            }
            sb.append((costos == null || costos.isEmpty()) ? 0.00 : costos.get(0).getCosto()).append('|');
            sb.deleteCharAt(sb.length() - 1);
            sb.append("\n");
        }
        inputStream = StringUtility.getInputString(sb.toString());

        return SUCCESS;
    }

    /**
     * @param q
     *            the q to set
     */
    public void setQ(String q) {
        this.q = q;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param limit
     *            the limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

}
