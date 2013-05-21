package com.palermotenis.model.service.sucursales.impl;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableMap;
import com.palermotenis.infrastructure.testsupport.base.BaseSpringTestCase;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.model.dao.stock.StockDao;
import com.palermotenis.model.dao.sucursales.SucursalDao;
import com.palermotenis.model.service.presentaciones.PresentacionService;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;
import com.palermotenis.model.service.sucursales.SucursalService;
import com.palermotenis.model.service.valores.ValorService;

public class SucursalServiceImplTest extends BaseSpringTestCase {

    private static final String PHONE = "111111";
    private static final String ADDRESS = "test address";
    private static final String NOMBRE = "test";

    private static final String PHONE_2 = "222222";
    private static final String ADDRESS_2 = "test address2";
    private static final String NOMBRE_2 = "test2";

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private TipoProductoService tipoProductoService;

    @Autowired
    private ValorService valorService;

    @Autowired
    private PresentacionService presentacionService;

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    private StockDao stockDao;

    private Sucursal sucursal;

    @Before
    @Transactional
    public void createNewSucursal() {
        sucursalService.createNewSucursal(NOMBRE, ADDRESS, PHONE);
        sucursal = sucursalDao.findBy("Nombre", "nombre", NOMBRE);
    }

    @Test
    @Transactional
    public void testCreateNewSucursal() {
        assertNotNull(sucursal);
        assertSame(NOMBRE, sucursal.getNombre());
        assertSame(ADDRESS, sucursal.getDireccion());
        assertSame(PHONE, sucursal.getTelefono());
        testCreatedStock();
    }

    private void testCreatedStock() {
        for (TipoProducto tipoProducto : getAllProductos()) {
            for (Producto producto : tipoProducto.getProductos()) {
                List<Stock> stocks = getStocks(producto);
                if (tipoProducto.isPresentable() && tipoProducto.isClasificable()) {
                    List<ValorClasificatorio> valoresClasificatorios = getValoresClasificatorios(tipoProducto);
                    List<Presentacion> presentaciones = getPresentaciones(tipoProducto);
                    assertThat(stocks.size(), is(valoresClasificatorios.size() * presentaciones.size()));
                } else if (tipoProducto.isClasificable()) {
                    List<ValorClasificatorio> valoresClasificatorios = getValoresClasificatorios(tipoProducto);
                    assertThat(stocks.size(), is(valoresClasificatorios.size()));
                } else if (tipoProducto.isPresentable()) {
                    List<Presentacion> presentaciones = getPresentaciones(tipoProducto);
                    assertThat(stocks.size(), is(presentaciones.size()));
                } else {
                    assertThat(stocks.size(), is(1));
                }
            }
        }
    }

    private List<Stock> getStocks(Producto producto) {
        return stockDao.queryBy("Producto,Sucursal",
            new ImmutableMap.Builder<String, Object>().put("producto", producto).put("sucursal", sucursal).build());
    }

    private List<TipoProducto> getAllProductos() {
        return tipoProductoService.getAllTipoProducto();
    }

    private List<ValorClasificatorio> getValoresClasificatorios(TipoProducto tipoProducto) {
        return valorService.getValoresClasificatoriosByTipos(tipoProducto.getTiposAtributoClasificatorios());
    }

    private List<Presentacion> getPresentaciones(TipoProducto tipoProducto) {
        return presentacionService.getPresentacionesByTipos(tipoProducto.getTiposPresentacion());
    }

    @Test
    @Transactional
    public void testUpdateSucursal() {
        sucursalService.updateSucursal(sucursal.getId(), NOMBRE_2, ADDRESS_2, PHONE_2);
        assertNotNull(sucursal);
        assertSame(NOMBRE_2, sucursal.getNombre());
        assertSame(ADDRESS_2, sucursal.getDireccion());
        assertSame(PHONE_2, sucursal.getTelefono());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void testDeleteStocksLinkedToSucursal() {
        assertNotNull(sucursal);
        assertThat(sucursal.getStocks(), is(not(empty())));
        sucursalService.deleteSucursal(sucursal.getId());
        assertNull(sucursal.getStocks());
    }

    @Test(expected = EntityNotFoundException.class)
    @Transactional
    public void testDeleteSucursal() {
        assertNotNull(sucursal);
        sucursalService.deleteSucursal(sucursal.getId());
        sucursalDao.load(sucursal.getId());
    }

}
