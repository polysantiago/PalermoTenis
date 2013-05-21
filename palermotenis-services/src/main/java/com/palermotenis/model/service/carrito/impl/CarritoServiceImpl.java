package com.palermotenis.model.service.carrito.impl;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.carrito.Carrito;
import com.palermotenis.model.beans.carrito.Item;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.service.carrito.CarritoService;
import com.palermotenis.model.service.pagos.PagoService;
import com.palermotenis.model.service.precios.impl.PrecioService;
import com.palermotenis.model.service.presentaciones.PresentacionService;
import com.palermotenis.model.service.productos.ProductoService;
import com.palermotenis.model.service.stock.StockService;
import com.palermotenis.model.service.valores.ValorService;

@Service("carritoService")
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PresentacionService presentacionService;

    @Autowired
    private ValorService valorService;

    @Autowired
    private StockService stockService;

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PrecioService precioService;

    @Override
    @Transactional
    public void add(Carrito carrito, Integer productoId) {
        Producto producto = productoService.getProductById(productoId);
        if (!producto.hasStock()) {
            throw new RuntimeException("Cannot add a product without stock");
        }

        if (CollectionUtils.isEmpty(producto.getStocks())) {
            throw new RuntimeException("No stock found for producto " + producto);
        }

        // take the first item with stock
        Optional<Stock> optional = Iterables.tryFind(producto.getStocks(), new Predicate<Stock>() {
            @Override
            public boolean apply(@Nullable Stock stock) {
                return stock.getStock() > 0;
            }
        });

        Stock stock = optional.get();
        carrito.agregar(1, stock);
        setPrecio(carrito, stock);
    }

    @Override
    public void remove(Carrito carrito, Integer stockId) {
        Stock stock = stockService.getStockById(stockId);
        carrito.quitar(stock);
    }

    @Override
    public void empty(Carrito carrito) {
        carrito.vaciar();
    }

    @Override
    @Transactional
    public void edit(Carrito carrito, Integer cuotas, Integer pagoId) {
        Pago pago = pagoService.getPagoById(pagoId);

        if (carrito.getCuotas() != cuotas) {
            carrito.setCuotas(cuotas);
        }

        if (carrito.getPago() == null) {
            carrito.setPago(pago);
        } else if (!carrito.getPago().equals(pago)) {
            carrito.setPago(pago);
        }

        updatePrecios(carrito);
    }

    @Override
    @Transactional
    public void updateCantidades(Carrito carrito, Map<Integer, Integer> stockCantidadMap) {
        Set<Entry<Integer, Integer>> entrySet = stockCantidadMap.entrySet();
        for (Entry<Integer, Integer> entry : entrySet) {
            Integer stockId = entry.getKey();
            Integer cantidad = entry.getValue();
            Stock stock = stockService.getStockById(stockId);

            if (!carrito.hasStock(stock)) {
                carrito.agregar(cantidad, stock);
                setPrecio(carrito, stock);
            } else if (cantidad <= 0) {
                carrito.quitar(stock);
            } else {
                Item item = carrito.get(stock);
                item.setCantidad(cantidad);
                setPrecio(carrito, stock);
            }
        }
    }

    private void updatePrecios(Carrito carrito) {
        for (Stock stock : carrito.getContenido().keySet()) {
            setPrecio(carrito, stock);
        }
    }

    private void setPrecio(Carrito carrito, Stock stock) {
        if (!carrito.hasStock(stock)) {
            return;
        }
        if (carrito.getPago() == null) {
            carrito.setPago(pagoService.getEfectivo());
        }
        Item item = carrito.get(stock);
        Precio precio = precioService.estimarPrecio(stock, carrito.getPago(), carrito.getCuotas());
        item.setPrecioUnitario(precioService.calculatePrecioUnitarioPesos(precio));
        item.setSubtotal(precioService.calculateSubtotalPesos(precio, item.getCantidad()));
    }

}
