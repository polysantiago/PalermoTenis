package com.palermotenis.model.service.atributos.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.atributos.AtributoMultipleValores;
import com.palermotenis.model.beans.atributos.AtributoSimple;
import com.palermotenis.model.beans.atributos.AtributoTipado;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoSimple;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoTipado;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.ClasificableState;
import com.palermotenis.model.beans.productos.tipos.DefaultState;
import com.palermotenis.model.beans.productos.tipos.PresentableAndClasificableState;
import com.palermotenis.model.beans.productos.tipos.PresentableState;
import com.palermotenis.model.beans.productos.tipos.State;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.valores.Valor;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.model.beans.valores.ValorPosible;
import com.palermotenis.model.dao.atributos.AtributoMultipleValoresDao;
import com.palermotenis.model.dao.atributos.AtributoSimpleDao;
import com.palermotenis.model.dao.atributos.AtributoTipadoDao;
import com.palermotenis.model.service.atributos.AtributoService;
import com.palermotenis.model.service.atributos.tipos.TipoAtributoService;
import com.palermotenis.model.service.presentaciones.PresentacionService;
import com.palermotenis.model.service.stock.StockService;
import com.palermotenis.model.service.sucursales.SucursalService;
import com.palermotenis.model.service.valores.ValorService;

@Service("atributoService")
@Transactional(propagation = Propagation.REQUIRED)
public class AtributoServiceImpl implements AtributoService {

    @Autowired
    private AtributoSimpleDao atributoSimpleDao;

    @Autowired
    private AtributoTipadoDao atributoTipadoDao;

    @Autowired
    private AtributoMultipleValoresDao atributoMultipleValoresDao;

    @Autowired
    private ValorService valorService;

    @Autowired
    private PresentacionService presentacionService;

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private StockService stockService;

    @Autowired
    private TipoAtributoService tipoAtributoService;

    @Override
    public void delete(AtributoSimple atributo) {
        atributoSimpleDao.destroy(atributo);
    }

    @Override
    public void updateValorPosible(AtributoTipado atributoTipado, ValorPosible valorPosible) {
        atributoTipado.setValorPosible(valorPosible);
        atributoTipadoDao.edit(atributoTipado);
    }

    @Override
    public void persistAtributoSimple(Producto producto, Integer tipoAtributoId, String valor) {
        TipoAtributoSimple tipoAtributo = (TipoAtributoSimple) getTipoAtributo(tipoAtributoId);

        AtributoSimple atributoSimple = new AtributoSimple(tipoAtributo, producto);

        Valor valorObject = new Valor(tipoAtributo);
        Object object = ConvertUtils.convert(valorObject, tipoAtributo.getClase());
        valorObject.setUnidad(object);

        atributoSimple.setValor(valorObject);

        atributoSimpleDao.create(atributoSimple);
    }

    @Override
    public void persistAtributoTipado(Producto producto, Integer tipoAtributoId, Integer valorPosibleId) {
        persistAtributoTipado(new AtributoTipado(), producto, tipoAtributoId, valorPosibleId);
    }

    @Override
    public void persistAtributoMultipleValores(Producto producto, Integer tipoAtributoId, Integer valorPosibleId) {
        persistAtributoTipado(new AtributoMultipleValores(), producto, tipoAtributoId, valorPosibleId);
    }

    private void persistAtributoTipado(AtributoTipado atributoTipado, Producto producto, Integer tipoAtributoId,
            Integer valorPosibleId) {
        TipoAtributoTipado tipoAtributo = (TipoAtributoTipado) getTipoAtributo(tipoAtributoId);
        ValorPosible valorPosible = getValorPosible(valorPosibleId);

        atributoTipado.setProducto(producto);
        atributoTipado.setTipoAtributo(tipoAtributo);
        atributoTipado.setValorPosible(valorPosible);

        atributoTipadoDao.create(atributoTipado);
    }

    @Override
    public void persistAtributosClasificatorios(Producto producto) {
        List<Sucursal> sucursales = getSucursales();
        State state = defineUnknownState(producto, sucursales);
        persistAtributosClasificatorios(producto, state);
    }

    @Override
    public void persistAtributosClasificatorios(Producto producto, Presentacion presentacion) {
        State state = definePresentableState(producto, presentacion);
        persistAtributosClasificatorios(producto, state);
    }

    @Override
    public void persistAtributosClasificatorios(Producto producto, ValorClasificatorio valorClasificatorio) {
        State state = defineClasificableState(producto, valorClasificatorio);
        persistAtributosClasificatorios(producto, state);
    }

    @Override
    public void persistAtributosClasificatorios(Producto producto, Sucursal sucursal) {
        List<Sucursal> sucursales = Lists.newArrayList(sucursal);
        State state = defineUnknownState(producto, sucursales);
        persistAtributosClasificatorios(producto, state);
    }

    private void persistAtributosClasificatorios(Producto producto, State state) {
        state.setStockService(stockService);
        state.createStock();
    }

    private State defineClasificableState(Producto producto, ValorClasificatorio valorClasificatorio) {
        List<Sucursal> sucursales = getSucursales();
        TipoProducto tipoProducto = producto.getTipoProducto();

        if (isPresentable(tipoProducto)) {
            List<Presentacion> presentaciones = getPresentaciones(tipoProducto);
            return new PresentableAndClasificableState(producto, sucursales, valorClasificatorio, presentaciones);
        }
        return new ClasificableState(producto, sucursales, valorClasificatorio);
    }

    private State definePresentableState(Producto producto, Presentacion presentacion) {
        List<Sucursal> sucursales = getSucursales();
        TipoProducto tipoProducto = producto.getTipoProducto();

        if (isClasificable(tipoProducto)) {
            List<ValorClasificatorio> valoresClasificatorios = getValoresClasificatorios(tipoProducto);
            return new PresentableAndClasificableState(producto, sucursales, valoresClasificatorios, presentacion);
        }
        return new PresentableState(producto, sucursales, presentacion);
    }

    private State defineUnknownState(Producto producto, List<Sucursal> sucursales) {
        TipoProducto tipoProducto = producto.getTipoProducto();

        List<ValorClasificatorio> valoresClasificatorios = getValoresClasificatorios(tipoProducto);
        List<Presentacion> presentaciones = getPresentaciones(tipoProducto);

        if (isClasificableAndPresentable(tipoProducto)) {
            return new PresentableAndClasificableState(producto, sucursales, valoresClasificatorios, presentaciones);
        } else if (isClasificable(tipoProducto)) {
            return new ClasificableState(producto, sucursales, valoresClasificatorios);
        } else if (isPresentable(tipoProducto)) {
            return new PresentableState(producto, sucursales, presentaciones);
        }
        return new DefaultState(producto, sucursales);
    }

    private List<Sucursal> getSucursales() {
        return sucursalService.getAllSucursales();
    }

    private List<ValorClasificatorio> getValoresClasificatorios(TipoProducto tipoProducto) {
        if (isClasificable(tipoProducto)) {
            return valorService.getValoresClasificatoriosByTipos(tipoProducto.getTiposAtributoClasificatorios());
        }
        return null;
    }

    private List<Presentacion> getPresentaciones(TipoProducto tipoProducto) {
        if (isPresentable(tipoProducto)) {
            return presentacionService.getPresentacionesByTipos(tipoProducto.getTiposPresentacion());
        }
        return null;
    }

    private boolean isClasificableAndPresentable(TipoProducto tipoProducto) {
        return isClasificable(tipoProducto) && isPresentable(tipoProducto);
    }

    private boolean isClasificable(TipoProducto tipoProducto) {
        return tipoProducto.isClasificable()
                && !CollectionUtils.isEmpty(tipoProducto.getTiposAtributoClasificatorios());
    }

    private boolean isPresentable(TipoProducto tipoProducto) {
        return tipoProducto.isPresentable() && !CollectionUtils.isEmpty(tipoProducto.getTiposPresentacion());
    }

    @Override
    public List<AtributoMultipleValores> getAtributosByProducto(Producto producto) {
        return atributoMultipleValoresDao.queryBy("Producto", "producto", producto);
    }

    @Override
    public AtributoSimple getAtributoSimple(Producto producto, TipoAtributoSimple tipoAtributo) {
        Map<String, Object> args = buildProductoAndTipoAtributoArgs(producto, tipoAtributo);
        return atributoSimpleDao.findBy("Producto,Tipo", args);
    }

    @Override
    public AtributoTipado getAtributoTipado(Producto producto, TipoAtributoSimple tipoAtributo) {
        Map<String, Object> args = buildProductoAndTipoAtributoArgs(producto, tipoAtributo);
        return atributoTipadoDao.findBy("Producto,Tipo", args);
    }

    private Map<String, Object> buildProductoAndTipoAtributoArgs(Producto producto, TipoAtributoSimple tipoAtributo) {
        Map<String, Object> args = new ImmutableMap.Builder<String, Object>()
            .put("producto", producto)
            .put("tipoAtributo", tipoAtributo)
            .build();
        return args;
    }

    private ValorPosible getValorPosible(Integer valorPosibleId) {
        return valorService.getValorPosibleById(valorPosibleId);
    }

    private TipoAtributo getTipoAtributo(Integer tipoAtributoId) {
        return tipoAtributoService.getTipoAtributoById(tipoAtributoId);
    }

    @Override
    public void clearValoresPosibles(ValorPosible valorPosible) {
        for (AtributoTipado atributosTipados : valorPosible.getAtributos()) {
            atributosTipados.setValorPosible(null);
            atributoTipadoDao.edit(atributosTipados);
        }
    }

}
