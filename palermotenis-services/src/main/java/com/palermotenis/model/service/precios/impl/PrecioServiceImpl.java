package com.palermotenis.model.service.precios.impl;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriTemplate;

import com.google.common.collect.ImmutableMap;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.pedidos.Pedido;
import com.palermotenis.model.beans.pedidos.PedidoProducto;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.precios.PrecioPresentacion;
import com.palermotenis.model.beans.precios.PrecioUnidad;
import com.palermotenis.model.beans.precios.pks.PrecioPK;
import com.palermotenis.model.beans.precios.pks.PrecioPresentacionPK;
import com.palermotenis.model.beans.precios.pks.PrecioProductoPK;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.dao.precios.PrecioDao;
import com.palermotenis.model.dao.precios.PrecioPresentacionDao;
import com.palermotenis.model.dao.precios.PrecioUnidadDao;
import com.palermotenis.model.service.monedas.MonedaService;
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
    private PrecioDao precioDao;

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

    @Autowired
    private MonedaService monedaService;

    @Override
    @Transactional
    public void create(Integer productoId, Integer pagoId, Integer monedaId, Integer presentacionId, Double valor,
            Double valorOferta, Integer cuotas, boolean enOferta) {
        Producto producto = getProducto(productoId);
        Pago pago = getPago(pagoId);
        Moneda moneda = getMoneda(monedaId);

        if (cuotas == null) {
            cuotas = 1;
        }

        PrecioPK pk = createPK(producto, pago, moneda, presentacionId, cuotas);
        if (getPrecio(pk) != null) {
            throw new RuntimeException("Ya existe un precio con esas características");
        }
        Precio precio = createPrecio(pk, valor, enOferta, valorOferta);
        precioDao.create(precio);
        producto.addPrecio(precio);
    }

    @Override
    @Transactional
    public void update(Integer productoId, Integer pagoId, Integer monedaId, Integer presentacionId, Double valor,
            Double valorOferta, Integer cuotas, boolean enOferta, Integer newPagoId, Integer newMonedaId,
            Integer newPresentacionId, Integer newCuotas) {
        Producto producto = getProducto(productoId);

        if (newCuotas == null) {
            newCuotas = 1;
        }

        Pago pago = getPago(pagoId);
        Moneda moneda = getMoneda(monedaId);

        Pago newPago = getPago(newPagoId);
        Moneda newMoneda = getMoneda(newMonedaId);

        PrecioPK pk = createPK(producto, pago, moneda, presentacionId, cuotas);
        PrecioPK newPk = createPK(producto, newPago, newMoneda, newPresentacionId, newCuotas);

        Precio precio = getPrecio(pk);
        if (pk.equals(newPk)) {
            updateNonPkValues(valor, valorOferta, enOferta, producto, precio);
            precioDao.edit(precio);
        } else {
            if (getPrecio(newPk) != null) {
                throw new RuntimeException("Ya existe un precio con esas características");
            }
            precioDao.destroy(precio);
            precioDao.create(createPrecio(newPk, valor, enOferta, valorOferta));
        }
    }

    private Precio getPrecio(PrecioPK pk) {
        return precioDao.find(pk);
    }

    private void updateNonPkValues(Double valor, Double valorOferta, boolean enOferta, Producto producto, Precio precio) {
        precio.setValor(valor);
        precio.setEnOferta(enOferta);
        if (enOferta && precio.getOrden() == 0) {
            precio.setOrden(producto.getId());
        }
        precio.setValorOferta(valorOferta);
        if (precio.getOrden() == null) {
            precio.setOrden(producto.getId());
        }
    }

    @Override
    @Transactional
    public void destroy(Integer productoId, Integer pagoId, Integer monedaId, Integer presentacionId, Integer cuotas) {
        Producto producto = getProducto(productoId);
        Pago pago = getPago(pagoId);
        Moneda moneda = getMoneda(monedaId);

        Precio precio = getPrecio(createPK(producto, pago, moneda, presentacionId, cuotas));
        precioDao.destroy(precio);
    }

    private PrecioPK createPK(Producto producto, Pago pago, Moneda moneda, Integer presentacionId, Integer cuotas) {
        if (presentacionId != null) {
            Presentacion presentacion = getPresentacion(presentacionId);
            return new PrecioPresentacionPK(producto, moneda, pago, presentacion, cuotas);
        }
        return new PrecioProductoPK(producto, moneda, pago, cuotas);
    }

    private Precio createPrecio(PrecioPK pk, Double valor, boolean enOferta, Double valorOferta) {
        Precio precio = pk.getProducto().isPresentable() ? new PrecioPresentacion((PrecioPresentacionPK) pk, valor)
                : new PrecioUnidad((PrecioProductoPK) pk, valor);
        precio.setEnOferta(enOferta);
        if (enOferta) {
            precio.setOrden(pk.getProducto().getId());
        }
        precio.setValorOferta(valorOferta);
        return precio;
    }

    @Override
    @Transactional
    public void moveOffer(Integer productoId, Integer productoOrden, Integer productoRgtId, Integer productoRgtOrden) {
        precioDao.moveOffer(productoId, productoOrden, productoRgtId, productoRgtOrden);
    }

    @Override
    public double calculatePrecioUnitarioPesos(Precio precio) {
        double valor = calculateValor(precio);
        Moneda moneda = precio.getId().getMoneda();
        if (moneda.getNombre().equalsIgnoreCase("Pesos")) {
            return valor;
        }
        return (calculateCotizacion(moneda) * valor);
    }

    @Override
    public double calculatePrecioUnitarioDolares(Precio precio) {
        double valor = calculateValor(precio);
        Moneda moneda = precio.getId().getMoneda();
        if (moneda.getNombre().equalsIgnoreCase("Dolares")) {
            return valor;
        }
        return (calculateCotizacion(moneda) * valor);
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

        String url = "http://quote.yahoo.com/d/quotes.txt?s={from}{to}=X&f=l1&e=.csv";
        UriTemplate uriTemplate = new UriTemplate(url);
        URI expanded = uriTemplate.expand(new ImmutableMap.Builder<String, Object>()
            .put("from", from)
            .put("to", to)
            .build());

        ClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        try {
            ClientHttpResponse clientHttpResponse = factory.createRequest(expanded, HttpMethod.GET).execute();
            String result = IOUtils.toString(clientHttpResponse.getBody());
            return Double.valueOf(result);
        } catch (IOException e) {
            logger.error(e);
            try {
                return currencyConvertor.ConversionRate(from, to);
            } catch (Exception ex) {
                logger.error(ex);
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
        Pago pago = pagoService.getEfectivo();
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
        Producto producto = getProducto(productoId);
        return hasPrecio(producto);
    }

    @Override
    public Precio getPrecioById(PrecioPK precioPk) {
        return getPrecio(precioPk);
    }

    @Override
    public List<? extends Precio> getPrecios(Integer productoId, Integer presentacionId) {
        Producto producto = getProducto(productoId);

        if (presentacionId != null) {
            Presentacion presentacion = getPresentacion(presentacionId);
            return precioPresentacionDao.getPrecios(producto, presentacion);
        }
        return precioUnidadDao.getPrecios(producto);
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

    private Producto getProducto(Integer productoId) {
        return productoService.getProductById(productoId);
    }

    private Moneda getMoneda(Integer monedaId) {
        return monedaService.getMonedaById(monedaId);
    }

    private Pago getPago(Integer pagoId) {
        return pagoService.getPagoById(pagoId);
    }

    private Presentacion getPresentacion(Integer presentacionId) {
        return presentacionService.getPresentacionById(presentacionId);
    }

}
