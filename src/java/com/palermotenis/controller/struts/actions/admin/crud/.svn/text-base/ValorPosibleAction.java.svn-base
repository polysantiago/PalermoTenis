/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.crud;

import com.google.common.collect.ImmutableList;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.atributos.AtributoTipado;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoTipado;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.beans.productos.tipos.ClasificableState;
import com.palermotenis.model.beans.productos.tipos.PresentableAndClasificableState;
import com.palermotenis.model.beans.productos.tipos.State;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.model.beans.valores.ValorPosible;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.hibernate.HibernateException;

/**
 *
 * @author Poly
 */
public class ValorPosibleAction extends ActionSupport {

    private final String SHOW = "show";
    private final String JSON = "json";
    private GenericDao<ValorPosible, Integer> valorPosibleService;
    private GenericDao<TipoAtributoTipado, Integer> tipoAtributoTipadoService;
    private GenericDao<Stock, Integer> stockService;
    private GenericDao<Sucursal, Integer> sucursalService;
    private GenericDao<Presentacion, Integer> presentacionService;
    private GenericDao<AtributoTipado, Integer> atributoTipadoService;
    private Collection<ValorPosible> valoresPosibles;
    private Integer tipoAtributoId;
    private Integer valorPosibleId;
    private String unidad;
    private InputStream inputStream;

    public String show() {
        TipoAtributo tipoAtributo = tipoAtributoTipadoService.find(tipoAtributoId);
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("tipoAtributo", tipoAtributo);
        valoresPosibles = valorPosibleService.queryBy("TipoAtributo", args);
        return SHOW;
    }

    public String create() {
        TipoAtributoTipado t = tipoAtributoTipadoService.find(tipoAtributoId);
        ValorPosible v = new ValorPosible();
        if (isValidField(t, unidad)) {
            v.setTipoAtributo(t);
            v.setUnidad(ConvertUtils.convert(unidad, t.getClase()));
            valorPosibleService.create(v);
            if (t instanceof TipoAtributoClasificatorio) {
                ValorClasificatorio vc = new ValorClasificatorio();
                vc.setId(v.getId());

                List<ValorClasificatorio> vcl = new ImmutableList.Builder<ValorClasificatorio>().add(vc).build();
                List<Sucursal> sucursales = sucursalService.findAll();
                for (Producto p : t.getTipoProducto().getProductos()) {

                    //Es el primer valor clas. que sea crea y ya se habian creado stocks anteriormente.
                    if (t.getValoresPosibles().size() == 1 && p.getStocks() != null && !p.getStocks().isEmpty()) {
                        for (Stock stock : p.getStocks()) {
                            if (stock.getValorClasificatorio() == null) { //verifiquemos que sea null, no hagamos cagadas
                                stock.setValorClasificatorio(vc);
                                stockService.edit(stock);
                            }
                        }
                    } else {
                        State state = new ClasificableState(p, sucursales, vcl);
                        if (p.getTipoProducto().isPresentable()) {
                            Collection<TipoPresentacion> tp = t.getTipoProducto().getTiposPresentacion();
                            List<Presentacion> prs = presentacionService.queryBy("TipoList", "tipoList", tp);
                            state = new PresentableAndClasificableState(p, sucursales, vcl, prs);
                        }
                        state.setStockService(stockService);
                        state.createStock();
                    }
                }
            }
            inputStream = StringUtility.getInputString("OK");
        } else {
            inputStream = StringUtility.getInputString("El formato para el campo no es válido");
        }
        return JSON;
    }

    public String edit() {
        ValorPosible v = valorPosibleService.find(valorPosibleId);
        if (isValidField(v.getTipoAtributo(), unidad)) {
            try {
                v.setUnidad(ConvertUtils.convert(unidad, v.getTipoAtributo().getClase()));
                valorPosibleService.edit(v);
                inputStream = StringUtility.getInputString("OK");
            } catch (HibernateException ex) {
                Logger.getLogger(ValorPosibleAction.class.getName()).log(Level.SEVERE, null, ex);
                inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
            } catch (Exception ex) {
                Logger.getLogger(ValorPosibleAction.class.getName()).log(Level.SEVERE, null, ex);
                inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
            }
        } else {
            inputStream = StringUtility.getInputString("El campo no es válido");
        }
        return JSON;
    }

    public String destroy() {
        try {
            ValorPosible vp = valorPosibleService.find(valorPosibleId);
            
            if (vp instanceof ValorClasificatorio) {
                for (Stock stock : ((ValorClasificatorio) vp).getStocks()) {
                    //Es el único valor clasasificatorio que queda
                    if (vp.getTipoAtributo().getValoresPosibles().size() == 1) {
                        stock.setValorClasificatorio(null);
                        stockService.edit(stock);
                    } else {                        
                        stockService.destroy(stock);
                    }
                }
            }
            
            TipoAtributoTipado tipoAtributo = vp.getTipoAtributo();
            if (tipoAtributo != null) {
                tipoAtributo.getValoresPosibles().remove(vp);
                tipoAtributoTipadoService.edit(tipoAtributo);
            }
            
            for (AtributoTipado atributosAtributoTipado : vp.getAtributos()) {
                atributosAtributoTipado.setValorPosible(null);
                atributoTipadoService.edit(atributosAtributoTipado);
            }
            
            valorPosibleService.destroy(vp);
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(ValorPosibleAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
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
     * @param valorPosibleService the valorPosibleService to set
     */
    public void setValorPosibleService(GenericDao<ValorPosible, Integer> valorPosibleService) {
        this.valorPosibleService = valorPosibleService;
    }

    /**
     * @return the valoresPosibles
     */
    public Collection<ValorPosible> getValoresPosibles() {
        return valoresPosibles;
    }

    /**
     * @param tipoAtributoId the tipoAtributoId to set
     */
    public void setTipoAtributoId(Integer tipoAtributoId) {
        this.tipoAtributoId = tipoAtributoId;
    }

    /**
     * @param valorPosibleId the valorPosibleId to set
     */
    public void setValorPosibleId(Integer valorPosibleId) {
        this.valorPosibleId = valorPosibleId;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param tipoAtributoTipadoService the tipoAtributoTipadoService to set
     */
    public void setTipoAtributoTipadoService(GenericDao<TipoAtributoTipado, Integer> tipoAtributoTipadoService) {
        this.tipoAtributoTipadoService = tipoAtributoTipadoService;
    }

    /**
     * @param stockService the stockService to set
     */
    public void setStockService(GenericDao<Stock, Integer> stockService) {
        this.stockService = stockService;
    }

    public void setPresentacionService(GenericDao<Presentacion, Integer> presentacionService) {
        this.presentacionService = presentacionService;
    }

    public void setSucursalService(GenericDao<Sucursal, Integer> sucursalService) {
        this.sucursalService = sucursalService;
    }

    public void setAtributoTipadoService(GenericDao<AtributoTipado, Integer> atributoTipadoService) {
        this.atributoTipadoService = atributoTipadoService;
    }
}
