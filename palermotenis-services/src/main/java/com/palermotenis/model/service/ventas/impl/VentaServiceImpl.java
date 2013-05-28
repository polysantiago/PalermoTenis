package com.palermotenis.model.service.ventas.impl;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.pedidos.Pedido;
import com.palermotenis.model.beans.pedidos.PedidoProducto;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.beans.ventas.Listado;
import com.palermotenis.model.beans.ventas.ProductoVenta;
import com.palermotenis.model.beans.ventas.StockListado;
import com.palermotenis.model.beans.ventas.StockListadoPK;
import com.palermotenis.model.beans.ventas.Venta;
import com.palermotenis.model.dao.listados.ListadoDao;
import com.palermotenis.model.dao.listados.StockListadoDao;
import com.palermotenis.model.dao.ventas.VentaDao;
import com.palermotenis.model.exceptions.NullPriceException;
import com.palermotenis.model.service.carrito.PedidoService;
import com.palermotenis.model.service.clientes.ClienteService;
import com.palermotenis.model.service.pagos.PagoService;
import com.palermotenis.model.service.precios.PrecioService;
import com.palermotenis.model.service.stock.StockService;
import com.palermotenis.model.service.ventas.VentaService;
import com.palermotenis.util.StringUtility;

@Service("ventaService")
public class VentaServiceImpl implements VentaService {

    @Autowired
    private ListadoDao listadoDao;

    @Autowired
    private VentaDao ventaDao;

    @Autowired
    private StockListadoDao stockListadoDao;

    @Autowired
    private StockService stockService;

    @Autowired
    private PagoService pagoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PrecioService precioService;

    @Autowired
    private PedidoService pedidoService;

    @Override
    @Transactional
    public void confirmOrder(Integer pedidoId, Usuario usuario) {
        Pedido pedido = pedidoService.getPedidoById(pedidoId);
        Venta venta = new Venta(pedido.getCliente(), pedido.getPago());

        for (PedidoProducto pedidoProducto : pedido.getPedidosProductos()) {
            ProductoVenta productoVenta = new ProductoVenta();
            Stock stock = pedidoProducto.getId().getStock();

            productoVenta.setProducto(StringUtility.buildNameFromStock(stock));
            productoVenta.setPrecioUnitario(pedidoProducto.getSubtotal() / pedidoProducto.getCantidad());
            productoVenta.setSubtotal(pedidoProducto.getSubtotal());
            productoVenta.setVenta(venta);
            productoVenta.setStock(stock);

            venta.addProductoVenta(productoVenta);

            stockService.onSold(stock, pedidoProducto.getCantidad());
        }
        venta.setTotal(pedido.getTotal());
        venta.setCuotas(pedido.getCuotas());
        venta.setUsuario(usuario);
        ventaDao.create(venta);

        pedidoService.onConfirmed(pedidoId);
    }

    @Override
    @Transactional
    public void confirmSale(String listadoId, Usuario usuario) throws NullPriceException {
        Listado listado = getListado(listadoId);

        Venta venta = new Venta(listado.getCliente(), listado.getPago());

        for (StockListado stockListado : listado.getStocksListado()) {
            Stock stock = stockListado.getStockListadoPK().getStock();

            double unitario = stockListado.getPrecioUnitario();
            double subtotal = stockListado.getSubtotal();

            if (unitario == 0.0) {
                throw new NullPriceException();
            }

            ProductoVenta productoVenta = new ProductoVenta(StringUtility.buildNameFromStock(stock), unitario, subtotal);
            productoVenta.setVenta(venta);
            productoVenta.setStock(stock);

            venta.addProductoVenta(productoVenta);

            stockService.onSold(stock, stockListado.getCantidad());
        }
        venta.setCuotas(listado.getCuotas());
        venta.setTotal(listado.getTotal());
        venta.setUsuario(usuario);
        ventaDao.create(venta);

        onConfirmed(listado);
    }

    @Override
    @Transactional
    public void onConfirmed(Listado listado) {
        listadoDao.destroy(listado);
    }

    @Override
    public Listado getListadoById(String listadoId) {
        return getListado(listadoId);
    }

    @Override
    @Transactional
    public Listado authorizeListing(String username, String password, String listadoId) {
        Listado listado = getListado(listadoId);
        listado.setAutorizado(true);
        listadoDao.edit(listado);
        return listado;
    }

    @Override
    @Transactional
    public Listado registerNewPurchase(Integer pagoId, Integer cuotas, Integer clienteId, Map<Integer, Integer> stocks) {
        double total = 0.00;
        Listado listado = new Listado(RandomStringUtils.randomAlphanumeric(10));
        Pago pago = pagoService.getPagoById(pagoId);

        for (Entry<Integer, Integer> entry : stocks.entrySet()) {
            Integer stockId = entry.getKey();
            Integer cantidad = entry.getValue();

            StockListado stockListado = new StockListado();
            Stock stock = getStock(stockId);
            Precio precio = precioService.estimarPrecio(stock, pago, cuotas);

            stockListado.setStockListadoPK(new StockListadoPK(listado, stock));
            stockListado.setCantidad(cantidad);
            stockListado.setPrecioUnitario(precioService.calculatePrecioUnitarioPesos(precio));
            stockListado.setSubtotal(precioService.calculateSubtotalPesos(precio, cantidad));

            listado.addStockListado(stockListado);
            total += stockListado.getSubtotal();
        }

        listado.setTotal(total);
        listado.setPago(pago);
        listado.setCuotas(cuotas);
        listado.setCodAutorizacion(RandomStringUtils.randomAlphanumeric(10));
        listado.setCliente(clienteService.getClienteById(clienteId));
        listado.setAutorizado(true);

        listadoDao.create(listado);

        return listado;
    }

    @Override
    @Transactional
    public Listado updateListingQuantities(String listadoId, Map<Integer, Integer> quantities) {
        Listado listado = getListado(listadoId);

        for (Integer stockId : quantities.keySet()) {
            Stock stock = getStock(stockId);
            Integer quantity = quantities.get(stockId);
            StockListado stockListado = getStockListado(listado, stock);
            stockListado.setCantidad(quantity);
            stockListado.setSubtotal(stockListado.getPrecioUnitario() * quantity);
        }

        updateTotal(listado);

        listadoDao.edit(listado);

        return listado;
    }

    @Override
    @Transactional
    public Listado updateListingPrices(String listadoId, Map<Integer, Double> prices, boolean authorized) {
        Listado listado = getListado(listadoId);

        listado.setAutorizado(authorized);

        for (Integer stockId : prices.keySet()) {
            Stock stock = getStock(stockId);
            Double price = prices.get(stockId);
            StockListado stockListado = getStockListado(listado, stock);
            stockListado.setPrecioUnitario(prices.get(stockId));
            stockListado.setSubtotal(stockListado.getCantidad() * price);
        }

        updateTotal(listado);

        listadoDao.edit(listado);

        return listado;
    }

    private void updateTotal(Listado listado) {
        double total = 0.00;
        for (StockListado stockListado : listado.getStocksListado()) {
            total += stockListado.getSubtotal();
        }
        listado.setTotal(total);
    }

    private Stock getStock(Integer stockId) {
        return stockService.getStockById(stockId);
    }

    private StockListado getStockListado(Listado listado, Stock stock) {
        return stockListadoDao.find(new StockListadoPK(listado, stock));
    }

    private Listado getListado(String listadoId) {
        return listadoDao.find(listadoId);
    }

}
