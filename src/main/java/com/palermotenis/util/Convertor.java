/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.util;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.pedidos.Pedido;
import com.palermotenis.model.beans.pedidos.PedidoProducto;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.precios.PrecioPresentacion;
import com.palermotenis.model.beans.precios.PrecioUnidad;
import com.palermotenis.model.beans.precios.pks.PrecioPresentacionPK;
import com.palermotenis.model.beans.precios.pks.PrecioProductoPK;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.ws.CurrencyConvertor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author Poly
 */
public class Convertor implements ApplicationContextAware {

    private CurrencyConvertor currencyConvertor;
    private ApplicationContext applicationContext;
    private static final Logger logger = Logger.getLogger(Convertor.class);

    public double calculatePrecioUnitarioPesos(Precio precio) {
        double valor = calculateValor(precio);
        Moneda moneda = precio.getId().getMoneda();
        if (moneda.getNombre().equalsIgnoreCase("Pesos")) {
            return valor;
        } else {
            return (calculateCotizacion(moneda) * valor);
        }
    }

    public double calculatePrecioUnitarioDolares(Precio precio) {
        double valor = calculateValor(precio);
        Moneda moneda = precio.getId().getMoneda();
        if (moneda.getNombre().equalsIgnoreCase("Dolares")) {
            return valor;
        } else {
            return (calculateCotizacion(moneda) * valor);
        }
    }

    public double calculateSubtotalPesos(Precio precio, int cantidad) {
        return (calculatePrecioUnitarioPesos(precio) * cantidad);
    }

    public double calculateSubtotalDolares(Precio precio, int cantidad) {
        return (calculatePrecioUnitarioDolares(precio) * cantidad);
    }

    private double calculateCotizacion(Moneda moneda) {
        return calculateCotizacion(moneda.getCodigo(), moneda.getContrario().getCodigo());
    }

    public double calculateCotizacion(String from, String to) {
        double cotizacion = 1.00;

        try {
            logger.info("Intentando con el servicio de conversión de Yahoo...");
            String yahooURL = "http://quote.yahoo.com/d/quotes.txt?s=" + from + to + "=X&f=l1&e=.csv";
            URL url = new URL(yahooURL);
            HttpURLConnection m_con = (HttpURLConnection) url.openConnection();
            m_con.setDoOutput(true);

            BufferedReader reader = new BufferedReader(new InputStreamReader(m_con.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                cotizacion = Double.parseDouble(line);
            }
            reader.close();
            m_con.disconnect();

            logger.info("Se ha convertido la moneda gracias al servicio de conversión de Yahoo");
        } catch (MalformedURLException mex) {
            logger.error(mex);
        } catch (IOException ioex) {
            logger.warn("No se pudo acceder al servicio de conversión de Yahoo", ioex);
            try {
                logger.info("Intentando con el web service para convertir moneda...");
                cotizacion = currencyConvertor.ConversionRate(from, to);
            } catch (Exception ex) {
                logger.error("No se pudo utilizar el WS para convertir la moneda", ex);
            }
        }
        return cotizacion;
    }

    private double calculateValor(Precio precio) {
        if (precio == null || precio.getValor() == null) {
            return 0.00;
        }
        return (precio.isEnOferta()) ? precio.getValorOferta() : precio.getValor();
    }

    /**
     * @param currencyConvertor the currencyConvertor to set
     */
    public void setCurrencyConvertor(CurrencyConvertor currencyConvertor) {
        this.currencyConvertor = currencyConvertor;
    }

    public double calculateTotalPesos(Collection<PedidoProducto> pps) {
        double total = 0.00;
        for (PedidoProducto pp : pps) {
            total += calculateSubtotalPesos(estimarPrecio(pp), pp.getCantidad());
        }
        return total;
    }

    public double calculateTotalDolares(Collection<PedidoProducto> pps) {
        double total = 0.00;
        for (PedidoProducto pp : pps) {
            total += calculateSubtotalDolares(estimarPrecio(pp), pp.getCantidad());
        }
        return total;
    }

    public Precio estimarPrecio(PedidoProducto pedidoProducto) {
        Presentacion presentacion = pedidoProducto.getId().getStock().getPresentacion();
        Producto producto = pedidoProducto.getId().getStock().getProducto();
        Pedido pedido = pedidoProducto.getId().getPedido();
        Pago pago = pedido.getPago();
        return estimarPrecio(presentacion, producto, pedido.getCuotas(), pago);
    }

    public Precio estimarPrecio(Stock stock, Pago pago, int cuotas) {
        Presentacion presentacion = stock.getPresentacion();
        Producto producto = stock.getProducto();
        return estimarPrecio(presentacion, producto, cuotas, pago);
    }

    public Precio estimarPrecio(Presentacion presentacion, Producto producto, int cuotas, Pago pago) {
        Precio precio = null;
        int cercano = 99;
        List<? extends Precio> precios = findPrecios(producto, pago, presentacion); //traigo todos los de esa forma de pago
        if (precios == null || precios.isEmpty()) { //si no hay ninguno con esa forma de pago
            return findPrecio(producto, presentacion); //dame el primero listado
        } else { //hay por lo menos uno con esa forma de pago
            for (Precio pr : precios) {
                int cuotasBase = pr.getId().getCuotas();
                if (cuotasBase == cuotas) { //dame el que coincida las cuotas
                    return pr;
                } else if (Math.abs(cuotasBase - cuotas) < cercano) { //dame el más cercano que coincidan las cuotas (si hay más de uno, el último listado)
                    cercano = Math.abs(cuotasBase - cuotas);
                    precio = pr;
                }
            }
        }
        return precio;
    }

    private List<? extends Precio> findPrecios(Producto producto, Pago pago, Presentacion presentacion) {
        Map<String, Object> args = HashBiMap.create();
        args.put("producto", producto);
        args.put("pago", pago);
        if (presentacion == null) {
            return getPrecioUnidadService().queryBy("Producto,Pago", args);
        } else {
            args.put("presentacion", presentacion);
            return getPrecioPresentacionService().queryBy("Producto,Pago,Presentacion", args);
        }
    }

    public Precio findPrecio(Producto producto, Presentacion presentacion) {
        Map<String, Object> args = HashBiMap.create();
        args.put("producto", producto);
        List<? extends Precio> precios = null;
        if (presentacion == null) {
            precios = getPrecioUnidadService().queryBy("Producto", args);
        } else {
            args.put("presentacion", presentacion);
            precios = getPrecioPresentacionService().queryBy("Producto,Presentacion", args);
        }
        if (precios.isEmpty()) {
            logger.warn("Producto: " + producto.getModelo().getNombre() + " no tiene Precio! Creando Precio vacío...");
            return null;
        } else {
            return precios.get(0);
        }
    }

    public boolean hasPrecio(Producto producto) {
        Map<String, Object> args = new ImmutableMap.Builder<String, Object>().put("producto", producto).build();
        List<? extends Precio> precios = null;

        precios = !producto.isPresentable() ? getPrecioUnidadService().queryBy("Producto", args)
                : getPrecioPresentacionService().queryBy("Producto", args);

        return (!precios.isEmpty());
    }

    public GenericDao<PrecioPresentacion, PrecioPresentacionPK> getPrecioPresentacionService() {
        return (GenericDao<PrecioPresentacion, PrecioPresentacionPK>) applicationContext.getBean("precioPresentacionService");
    }

    public GenericDao<PrecioUnidad, PrecioProductoPK> getPrecioUnidadService() {
        return (GenericDao<PrecioUnidad, PrecioProductoPK>) applicationContext.getBean("precioUnidadService");
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
