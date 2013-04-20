/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.controller.struts.actions.JsonActionSupport;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.atributos.AtributoTipado;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoTipado;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.ClasificableState;
import com.palermotenis.model.beans.productos.tipos.PresentableAndClasificableState;
import com.palermotenis.model.beans.productos.tipos.State;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.model.beans.valores.ValorPosible;

/**
 * 
 * @author Poly
 */
public class ValorPosibleAction extends JsonActionSupport {

    private static final long serialVersionUID = -7754759545053197071L;

    private Collection<ValorPosible> valoresPosibles;

    private Integer tipoAtributoId;
    private Integer valorPosibleId;

    private String unidad;

    @Autowired
    private GenericDao<ValorPosible, Integer> valorPosibleDao;

    @Autowired
    private GenericDao<TipoAtributoTipado, Integer> tipoAtributoTipadoDao;

    @Autowired
    private GenericDao<Stock, Integer> stockDao;

    @Autowired
    private GenericDao<Sucursal, Integer> sucursalDao;

    @Autowired
    private GenericDao<Presentacion, Integer> presentacionDao;

    @Autowired
    private GenericDao<AtributoTipado, Integer> atributoTipadoDao;

    public String show() {
        TipoAtributo tipoAtributo = tipoAtributoTipadoDao.find(tipoAtributoId);
        valoresPosibles = valorPosibleDao.queryBy("TipoAtributo",
            new ImmutableMap.Builder<String, Object>().put("tipoAtributo", tipoAtributo).build());
        return SHOW;
    }

    public String create() {
        TipoAtributoTipado t = tipoAtributoTipadoDao.find(tipoAtributoId);
        ValorPosible v = new ValorPosible();
        if (isValidField(t, unidad)) {
            v.setTipoAtributo(t);
            v.setUnidad(ConvertUtils.convert(unidad, t.getClase()));
            valorPosibleDao.create(v);
            if (t instanceof TipoAtributoClasificatorio) {
                ValorClasificatorio vc = new ValorClasificatorio();
                vc.setId(v.getId());

                List<ValorClasificatorio> vcl = new ImmutableList.Builder<ValorClasificatorio>().add(vc).build();
                List<Sucursal> sucursales = sucursalDao.findAll();
                for (Producto p : t.getTipoProducto().getProductos()) {

                    // Es el primer valor clas. que sea crea y ya se habian creado stocks anteriormente.
                    if (t.getValoresPosibles().size() == 1 && p.getStocks() != null && !p.getStocks().isEmpty()) {
                        for (Stock stock : p.getStocks()) {
                            if (stock.getValorClasificatorio() == null) { // verifiquemos que sea null, no hagamos
                                                                          // cagadas
                                stock.setValorClasificatorio(vc);
                                stockDao.edit(stock);
                            }
                        }
                    } else {
                        State state = new ClasificableState(p, sucursales, vcl);
                        if (p.getTipoProducto().isPresentable()) {
                            Collection<TipoPresentacion> tp = t.getTipoProducto().getTiposPresentacion();
                            List<Presentacion> prs = presentacionDao.queryBy("TipoList", "tipoList", tp);
                            state = new PresentableAndClasificableState(p, sucursales, vcl, prs);
                        }
                        state.setStockDao(stockDao);
                        state.createStock();
                    }
                }
            }
            success();
        } else {
            writeResponse("El formato para el campo no es válido");
        }
        return JSON;
    }

    public String edit() {
        ValorPosible v = valorPosibleDao.find(valorPosibleId);
        if (isValidField(v.getTipoAtributo(), unidad)) {
            try {
                v.setUnidad(ConvertUtils.convert(unidad, v.getTipoAtributo().getClase()));
                valorPosibleDao.edit(v);
                success();
            } catch (HibernateException ex) {
                failure(ex);
            } catch (Exception ex) {
                failure(ex);
            }
        } else {
            writeResponse("El campo no es válido");
        }
        return JSON;
    }

    public String destroy() {
        try {
            ValorPosible vp = valorPosibleDao.find(valorPosibleId);

            if (vp instanceof ValorClasificatorio) {
                for (Stock stock : ((ValorClasificatorio) vp).getStocks()) {
                    // Es el único valor clasasificatorio que queda
                    if (vp.getTipoAtributo().getValoresPosibles().size() == 1) {
                        stock.setValorClasificatorio(null);
                        stockDao.edit(stock);
                    } else {
                        stockDao.destroy(stock);
                    }
                }
            }

            TipoAtributoTipado tipoAtributo = vp.getTipoAtributo();
            if (tipoAtributo != null) {
                tipoAtributo.getValoresPosibles().remove(vp);
                tipoAtributoTipadoDao.edit(tipoAtributo);
            }

            for (AtributoTipado atributosAtributoTipado : vp.getAtributos()) {
                atributosAtributoTipado.setValorPosible(null);
                atributoTipadoDao.edit(atributosAtributoTipado);
            }

            valorPosibleDao.destroy(vp);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
    }

    private boolean isValidField(TipoAtributo t, String unidad) {
        try {
            ConvertUtils.convert(unidad, t.getClase());
        } catch (ConversionException ex) {
            return false;
        }
        return true;
    }

    /**
     * @return the valoresPosibles
     */
    public Collection<ValorPosible> getValoresPosibles() {
        return valoresPosibles;
    }

    /**
     * @param tipoAtributoId
     *            the tipoAtributoId to set
     */
    public void setTipoAtributoId(Integer tipoAtributoId) {
        this.tipoAtributoId = tipoAtributoId;
    }

    /**
     * @param valorPosibleId
     *            the valorPosibleId to set
     */
    public void setValorPosibleId(Integer valorPosibleId) {
        this.valorPosibleId = valorPosibleId;
    }

    /**
     * @param unidad
     *            the unidad to set
     */
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

}
