package com.palermotenis.model.service.presentaciones;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.presentaciones.PresentacionDao;
import com.palermotenis.model.dao.stock.StockDao;
import com.palermotenis.model.service.atributos.AtributoService;
import com.palermotenis.model.service.presentaciones.tipos.TipoPresentacionService;
import com.palermotenis.model.service.stock.StockService;

@Service("presentacionService")
@Transactional(propagation = Propagation.REQUIRED)
public class PresentacionServiceImpl implements PresentacionService {

    @Autowired
    private PresentacionDao presentacionDao;

    @Autowired
    private StockService stockService;

    @Autowired
    private AtributoService atributoService;

    @Autowired
    private TipoPresentacionService tipoPresentacionService;

    @Autowired
    private StockDao stockDao;

    @Override
    public List<Presentacion> getPresentacionesByTipo(Integer tipoPresentacionId) {
        TipoPresentacion tipoPresentacion = tipoPresentacionService.getTipoPresentacionById(tipoPresentacionId);
        return getPresentacionesByTipo(tipoPresentacion);
    }

    @Override
    public List<Presentacion> getPresentacionesByTipo(TipoPresentacion tipoPresentacion) {
        return presentacionDao.queryBy("Tipo", "tipo", tipoPresentacion);
    }

    @Override
    public List<Presentacion> getPresentacionesByTipos(Collection<TipoPresentacion> tiposPresentacion) {
        return presentacionDao.getPresentaciones(tiposPresentacion);
    }

    @Override
    public void createNewPresentacion(Integer tipoPresentacionId, Double cantidad, String unidad, String nombre) {
        TipoPresentacion tipoPresentacion = tipoPresentacionService.getTipoPresentacionById(tipoPresentacionId);
        Presentacion presentacion = createNewPresentacion(cantidad, unidad, nombre, tipoPresentacion);
        updateStock(tipoPresentacion, presentacion);
    }

    private void updateStock(TipoPresentacion tipoPresentacion, Presentacion presentacion) {
        TipoProducto tipoProducto = tipoPresentacion.getTipoProducto();
        if (tipoProducto.isPresentable()) {
            for (Producto producto : tipoProducto.getProductos()) {
                updateStock(tipoPresentacion, presentacion, producto);
            }
        }
    }

    private void updateStock(TipoPresentacion tipoPresentacion, Presentacion presentacion, Producto producto) {
        // Es la primera presentación que sea crea y ya se habian creado stocks anteriormente.
        if (isNewlyCreatedAndHasExistingStock(tipoPresentacion, producto)) {
            for (Stock stock : producto.getStocks()) {
                if (stock.getPresentacion() == null) {
                    stock.setPresentacion(presentacion);
                    stockDao.edit(stock);
                }
            }
        } else {
            atributoService.persistAtributosClasificatorios(producto, presentacion);
        }
    }

    private Presentacion createNewPresentacion(Double cantidad, String unidad, String nombre,
            TipoPresentacion tipoPresentacion) {
        Presentacion presentacion = new Presentacion();
        presentacion.setCantidad(cantidad);
        presentacion.setTipoPresentacion(tipoPresentacion);
        presentacion.setUnidad(unidad);

        if (StringUtils.isEmpty(nombre)) {
            nombre = null;
        }
        presentacion.setNombre(nombre);

        presentacionDao.create(presentacion);
        return presentacion;
    }

    @Override
    public void updatePresentacion(Integer presentacionId, Double cantidad, String nombre, String unidad) {
        Presentacion presentacion = presentacionDao.find(presentacionId);
        presentacion.setCantidad(cantidad);
        presentacion.setNombre(nombre);
        presentacion.setUnidad(unidad);

        presentacionDao.edit(presentacion);
    }

    @Override
    public void delete(Integer presentacionId) {
        Presentacion presentacion = presentacionDao.find(presentacionId);
        for (Stock stock : presentacion.getStocks()) {
            if (hasOnlyOnePresentacion(presentacion.getTipoPresentacion())) { // Es la única presentación
                stockService.clearPresentacion(stock);
            } else {
                stockService.delete(stock);
            }
        }
        presentacionDao.destroy(presentacion);
    }

    private boolean isNewlyCreatedAndHasExistingStock(TipoPresentacion tipoPresentacion, Producto producto) {
        return hasOnlyOnePresentacion(tipoPresentacion) && !CollectionUtils.isEmpty(producto.getStocks());
    }

    private boolean hasOnlyOnePresentacion(TipoPresentacion tipoPresentacion) {
        return tipoPresentacion.getPresentaciones().size() == 1;
    }

}
