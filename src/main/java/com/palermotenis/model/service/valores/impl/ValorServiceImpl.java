package com.palermotenis.model.service.valores.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoTipado;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.model.beans.valores.ValorPosible;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoTipadoDao;
import com.palermotenis.model.dao.stock.StockDao;
import com.palermotenis.model.dao.valores.ValorClasificatorioDao;
import com.palermotenis.model.dao.valores.ValorPosibleDao;
import com.palermotenis.model.service.atributos.AtributoService;
import com.palermotenis.model.service.atributos.tipos.TipoAtributoService;
import com.palermotenis.model.service.valores.ValorService;

@Service("valoresService")
@Transactional(propagation = Propagation.REQUIRED)
public class ValorServiceImpl implements ValorService {

    @Autowired
    private ValorPosibleDao valorPosibleDao;

    @Autowired
    private ValorClasificatorioDao valorClasificatorioDao;

    @Autowired
    private TipoAtributoTipadoDao tipoAtributoTipadoDao;

    @Autowired
    private AtributoService atributoService;

    @Autowired
    private StockDao stockDao;

    @Autowired
    private TipoAtributoService tipoAtributoService;

    @Override
    public ValorPosible getValorPosibleById(Integer valorPosibleId) {
        return valorPosibleDao.find(valorPosibleId);
    }

    @Override
    public ValorClasificatorio getValorClasificatorioById(Integer valorClasificatorioId) {
        return valorClasificatorioDao.find(valorClasificatorioId);
    }

    @Override
    public List<ValorPosible> getValoresPosiblesByTipo(Integer tipoAtributoTipadoId) {
        TipoAtributo tipoAtributo = getTipoAtributoTipado(tipoAtributoTipadoId);
        return valorPosibleDao.queryBy("TipoAtributo", "tipoAtributo", tipoAtributo);
    }

    private TipoAtributoTipado getTipoAtributoTipado(Integer tipoAtributoTipadoId) {
        return tipoAtributoTipadoDao.getTipoAtributoTipadoById(tipoAtributoTipadoId);
    }

    @Override
    public List<ValorClasificatorio> getValoresClasificatoriosByTipos(
            Collection<TipoAtributoClasificatorio> tiposAtributosClasificatorios) {
        return valorClasificatorioDao.getValoresClasificatoriosByTiposAtributo(tiposAtributosClasificatorios);
    }

    @Override
    public void updateValor(Integer valorPosibleId, String unidad) {
        ValorPosible valorPosible = getValorPosibleById(valorPosibleId);
        if (isValidField(valorPosible.getTipoAtributo(), unidad)) {
            valorPosible.setUnidad(convert(unidad, valorPosible.getTipoAtributo()));
            valorPosibleDao.edit(valorPosible);
        }
        throw new ConversionException("El campo no es válido");
    }

    @Override
    public void createNewValor(Integer tipoAtributoTipadoId, String unidad) {
        TipoAtributoTipado tipoAtributoTipado = getTipoAtributoTipado(tipoAtributoTipadoId);

        if (isValidField(tipoAtributoTipado, unidad)) {
            if (tipoAtributoTipado instanceof TipoAtributoClasificatorio) {
                ValorClasificatorio valorClasificatorio = createNewValorClasificatorio(unidad,
                    (TipoAtributoClasificatorio) tipoAtributoTipado);
                updateStock(tipoAtributoTipado, valorClasificatorio);
            } else {
                createNewValorPosible(unidad, tipoAtributoTipado);
            }
        }
        throw new ConversionException("El formato para el campo no es válido");
    }

    @Override
    public void deleteValor(Integer valorPosibleId) {
        ValorPosible valorPosible = getValorPosibleById(valorPosibleId);
        if (valorPosible instanceof ValorClasificatorio) {
            ValorClasificatorio valorClasificatorio = (ValorClasificatorio) valorPosible;
            clearStock(valorClasificatorio);
        }
        tipoAtributoService.clearValoresPosibles(valorPosible);
        atributoService.clearValoresPosibles(valorPosible);
        valorPosibleDao.destroy(valorPosible);
    }

    private void clearStock(ValorClasificatorio valorClasificatorio) {
        for (Stock stock : valorClasificatorio.getStocks()) {
            clearStock(valorClasificatorio, stock);
        }
    }

    private void clearStock(ValorClasificatorio valorClasificatorio, Stock stock) {
        // Es el único valor clasasificatorio que queda
        if (hasOnlyOneValorPosible(valorClasificatorio.getTipoAtributo())) {
            stock.setValorClasificatorio(null);
            stockDao.edit(stock);
        } else {
            stockDao.destroy(stock);
        }
    }

    private void updateStock(TipoAtributoTipado tipoAtributoTipado, ValorClasificatorio valorClasificatorio) {
        for (Producto producto : tipoAtributoTipado.getTipoProducto().getProductos()) {
            // Es el primer valor clas. que sea crea y ya se habian creado stocks anteriormente.
            if (isNewlyCreatedAndHasExistingStock(tipoAtributoTipado, producto)) {
                for (Stock stock : producto.getStocks()) {
                    updateStock(valorClasificatorio, stock);
                }
            } else {
                atributoService.persistAtributosClasificatorios(producto, valorClasificatorio);
            }

        }
    }

    private void updateStock(ValorClasificatorio valorClasificatorio, Stock stock) {
        if (stock.getValorClasificatorio() == null) { // verifiquemos que sea null, no hagamos cagadas
            stock.setValorClasificatorio(valorClasificatorio);
            stockDao.edit(stock);
        }
    }

    private void createNewValorPosible(String unidad, TipoAtributoTipado tipoAtributoTipado) {
        ValorPosible valorPosible = setValor(new ValorPosible(), tipoAtributoTipado, unidad);
        valorPosibleDao.create(valorPosible);
    }

    private ValorClasificatorio createNewValorClasificatorio(String unidad,
            TipoAtributoClasificatorio tipoAtributoClasificatorio) {
        ValorClasificatorio valorClasificatorio = setValor(new ValorClasificatorio(), tipoAtributoClasificatorio,
            unidad);
        valorClasificatorioDao.create(valorClasificatorio);
        return valorClasificatorio;
    }

    private <V extends ValorPosible, T extends TipoAtributoTipado> V setValor(V valor, T tipoAtributoTipado,
            String unidad) {
        valor.setTipoAtributo(tipoAtributoTipado);
        valor.setUnidad(convert(unidad, tipoAtributoTipado));
        return valor;
    }

    private Object convert(String unidad, TipoAtributoTipado tipoAtributoTipado) {
        return ConvertUtils.convert(unidad, tipoAtributoTipado.getClase());
    }

    private boolean isValidField(TipoAtributo tipoAtributo, String unidad) {
        try {
            ConvertUtils.convert(unidad, tipoAtributo.getClase());
        } catch (ConversionException ex) {
            return false;
        }
        return true;
    }

    private boolean isNewlyCreatedAndHasExistingStock(TipoAtributoTipado tipoAtributoTipado, Producto producto) {
        return hasOnlyOneValorPosible(tipoAtributoTipado) && !CollectionUtils.isEmpty(producto.getStocks());
    }

    private boolean hasOnlyOneValorPosible(TipoAtributoTipado tipoAtributoTipado) {
        return tipoAtributoTipado.getValoresPosibles().size() == 1;
    }

}
