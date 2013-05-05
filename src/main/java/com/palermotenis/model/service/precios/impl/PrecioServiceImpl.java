package com.palermotenis.model.service.precios.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.pedidos.Pedido;
import com.palermotenis.model.beans.pedidos.PedidoProducto;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.precios.PrecioPresentacion;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.dao.precios.PrecioPresentacionDao;
import com.palermotenis.model.dao.precios.PrecioUnidadDao;
import com.palermotenis.model.service.pagos.PagoService;
import com.palermotenis.model.service.presentaciones.PresentacionService;
import com.palermotenis.model.service.productos.ProductoService;
import com.palermotenis.model.ws.CurrencyConvertor;

@Service("precioService")
public class PrecioServiceImpl implements PrecioService {

    private static final Logger logger = Logger.getLogger(PrecioServiceImpl.class);

    @Autowired
    private CurrencyConvertor currencyConvertor;

    @Autowired
    private PrecioUnidadDao precioUnidadDao;

    @Autowired
    private PrecioPresentacionDao precioPresentacionDao;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PresentacionService presentacionService;

    @Autowired
    private PagoService pagoService;

    @Override
    public double calculatePrecioUnitarioPesos(Precio precio) {
        double valor = calculateValor(precio);
        Moneda moneda = precio.getId().getMoneda();
        if (moneda.getNombre().equalsIgnoreCase("Pesos")) {
            return valor;
        } else {
            return (calculateCotizacion(moneda) * valor);
        }
    }

    @Override
    public double calculatePrecioUnitarioDolares(Precio precio) {
        double valor = calculateValor(precio);
        Moneda moneda = precio.getId().getMoneda();
        if (moneda.getNombre().equalsIgnoreCase("Dolares")) {
            return valor;
        } else {
            return (calculateCotizacion(moneda) * valor);
        }
    }

    @Override
    public double calculateSubtotalPesos(Precio precio, int cantidad) {
        return (calculatePrecioUnitarioPesos(precio) * cantidad);
    }

    @Override
    public double calculateSubtotalDolares(Precio precio, int cantidad) {
        return (calculatePrecioUnitarioDolares(precio) * cantidad);
    }

    @Override
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

    @Override
    public double calculateTotalPesos(Collection<PedidoProducto> pps) {
        double total = 0.00;
        for (PedidoProducto pp : pps) {
            total += calculateSubtotalPesos(estimarPrecio(pp), pp.getCantidad());
        }
        return total;
    }

    @Override
    public double calculateTotalDolares(Collection<PedidoProducto> pps) {
        double total = 0.00;
        for (PedidoProducto pp : pps) {
            total += calculateSubtotalDolares(estimarPrecio(pp), pp.getCantidad());
        }
        return total;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Precio estimarPrecio(PedidoProducto pedidoProducto) {
        Presentacion presentacion = pedidoProducto.getId().getStock().getPresentacion();
        Producto producto = pedidoProducto.getId().getStock().getProducto();
        Pedido pedido = pedidoProducto.getId().getPedido();
        Pago pago = pedido.getPago();
        return estimarPrecio(presentacion, producto, pedido.getCuotas(), pago);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Precio estimarPrecio(Stock stock, int cuotas) {
        Pago pago = pagoService.getFirstPago();
        Presentacion presentacion = stock.getPresentacion();
        Producto producto = stock.getProducto();
        return estimarPrecio(presentacion, producto, cuotas, pago);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Precio estimarPrecio(Stock stock, Pago pago, int cuotas) {
        Presentacion presentacion = stock.getPresentacion();
        Producto producto = stock.getProducto();
        return estimarPrecio(presentacion, producto, cuotas, pago);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Precio estimarPrecio(Presentacion presentacion, Producto producto, int cuotas, Pago pago) {
        Precio precio = null;
        int cercano = 99;
        List<? extends Precio> precios = findPrecios(producto, pago, presentacion); // traigo todos los de esa forma de
                                                                                    // pago
        if (precios == null || precios.isEmpty()) { // si no hay ninguno con esa forma de pago
            return findPrecio(producto, presentacion); // dame el primero listado
        } else { // hay por lo menos uno con esa forma de pago
            for (Precio pr : precios) {
                int cuotasBase = pr.getId().getCuotas();
                if (cuotasBase == cuotas) { // dame el que coincida las cuotas
                    return pr;
                } else if (Math.abs(cuotasBase - cuotas) < cercano) { // dame el más cercano que coincidan las cuotas
                                                                      // (si hay más de uno, el último listado)
                    cercano = Math.abs(cuotasBase - cuotas);
                    precio = pr;
                }
            }
        }
        return precio;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean hasPrecio(Producto producto) {
        List<? extends Precio> precios = !producto.isPresentable() ? precioUnidadDao.getPrecios(producto)
                : precioPresentacionDao.getPrecios(producto);
        return (!precios.isEmpty());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean hasPrecio(Integer productoId) {
        Producto producto = productoService.getProductById(productoId);
        return hasPrecio(producto);
    }

    @Override
    public List<PrecioPresentacion> getPrecios(Integer productoId, Integer presentacionId) {
        Producto producto = productoService.getProductById(productoId);
        Presentacion presentacion = presentacionService.getPresentacionById(presentacionId);
        return precioPresentacionDao.getPrecios(producto, presentacion);
    }

    private double calculateCotizacion(Moneda moneda) {
        return calculateCotizacion(moneda.getCodigo(), moneda.getContrario().getCodigo());
    }

    private double calculateValor(Precio precio) {
        if (precio == null || precio.getValor() == null) {
            return 0.00;
        }
        return (precio.isEnOferta()) ? precio.getValorOferta() : precio.getValor();
    }

    private List<? extends Precio> findPrecios(Producto producto, Pago pago, Presentacion presentacion) {
        return presentacion == null ? precioUnidadDao.getPrecios(producto, pago) : precioPresentacionDao.getPrecios(
            producto, presentacion, pago);
    }

    private Precio findPrecio(Producto producto, Presentacion presentacion) {
        List<? extends Precio> precios = presentacion == null ? precioUnidadDao.getPrecios(producto)
                : precioPresentacionDao.getPrecios(producto, presentacion);
        if (!precios.isEmpty()) {
            return precios.get(0);
        }
        logger.warn("Producto: " + producto.getModelo().getNombre() + " no tiene Precio! Creando Precio vacío...");
        return null;
    }

}
