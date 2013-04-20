package com.palermotenis.controller.struts.actions;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.atributos.AtributoMultipleValores;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoMultipleValores;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.beans.productos.Producto;

/**
 * 
 * @author Poly
 */
public class MostrarProducto extends ActionSupport {

    private static final long serialVersionUID = 6896818100286801184L;

    private Integer id;

    private InputStream inputStream;

    private Producto producto;

    private Collection<TipoAtributo> atributosSimples = new ArrayList<TipoAtributo>();
    private Collection<TipoAtributoClasificatorio> atributosClasificatorios = new ArrayList<TipoAtributoClasificatorio>();
    private Collection<TipoAtributoMultipleValores> atributosMultiples = new ArrayList<TipoAtributoMultipleValores>();
    private Collection<TipoPresentacion> presentaciones = new ArrayList<TipoPresentacion>();

    private Map<TipoAtributoMultipleValores, StringBuilder> multiplesMap = new HashMap<TipoAtributoMultipleValores, StringBuilder>();

    @Autowired
    private GenericDao<Modelo, Integer> modeloDao;

    @Autowired
    private GenericDao<TipoAtributo, Integer> tipoAtributoDao;

    @Override
    public String execute() {
        Modelo modelo = modeloDao.find(id);
        producto = modelo.getProducto();

        createAtributosCollections(tipoAtributoDao.queryBy("TipoProducto", new ImmutableMap.Builder<String, Object>()
            .put("tipoProducto", producto.getTipoProducto())
            .build()));

        if (producto.isPresentable()) {
            for (Presentacion p : getProducto().getPresentaciones()) {
                if (!p.getTipoPresentacion().hasPresentacionByProd(p)) {
                    p.getTipoPresentacion().addPresentacionByProd(p);
                }
                if (!getPresentaciones().contains(p.getTipoPresentacion())) {
                    getPresentaciones().add(p.getTipoPresentacion());
                }
            }
            setPresentaciones(getPresentaciones());
        }

        for (TipoAtributoMultipleValores t : atributosMultiples) {
            for (AtributoMultipleValores a : producto.getAtributosMultiplesValores()) {
                if (t.equals(a.getTipoAtributo())) {
                    if (getMultiplesMap().containsKey(t)) {
                        getMultiplesMap().get(t).append(a.getValorPosible().getNombre()).append(", ");
                    } else {
                        getMultiplesMap().put(t,
                            new StringBuilder().append(a.getValorPosible().getNombre()).append(", "));
                    }
                }
            }
            StringBuilder s = multiplesMap.get(t);
            if (s != null) {
                s.delete(s.lastIndexOf(", "), s.length() - 1);
            }
        }

        return SUCCESS;
    }

    private void createAtributosCollections(Collection<TipoAtributo> atributos) {
        for (TipoAtributo t : atributos) {
            if (t instanceof TipoAtributoClasificatorio) {
                getAtributosClasificatorios().add((TipoAtributoClasificatorio) t);
            } else if (t instanceof TipoAtributoMultipleValores) {
                getAtributosMultiples().add((TipoAtributoMultipleValores) t);
            } else {
                getAtributosSimples().add(t);
            }
        }
    }

    /**
     * @param prodId
     *            the prodId to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @return the producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * @return the atributosSimples
     */
    public Collection<TipoAtributo> getAtributosSimples() {
        return atributosSimples;
    }

    /**
     * @param atributosSimples
     *            the atributosSimples to set
     */
    public void setAtributosSimples(Collection<TipoAtributo> atributosSimples) {
        this.atributosSimples = atributosSimples;
    }

    /**
     * @return the atributosClasificatorios
     */
    public Collection<TipoAtributoClasificatorio> getAtributosClasificatorios() {
        return atributosClasificatorios;
    }

    /**
     * @param atributosClasificatorios
     *            the atributosClasificatorios to set
     */
    public void setAtributosClasificatorios(Collection<TipoAtributoClasificatorio> atributosClasificatorios) {
        this.atributosClasificatorios = atributosClasificatorios;
    }

    /**
     * @return the atributosMultiples
     */
    public Collection<TipoAtributoMultipleValores> getAtributosMultiples() {
        return atributosMultiples;
    }

    /**
     * @param atributosMultiples
     *            the atributosMultiples to set
     */
    public void setAtributosMultiples(Collection<TipoAtributoMultipleValores> atributosMultiples) {
        this.atributosMultiples = atributosMultiples;
    }

    /**
     * @return the presentaciones
     */
    public Collection<TipoPresentacion> getPresentaciones() {
        return presentaciones;
    }

    /**
     * @param presentaciones
     *            the presentaciones to set
     */
    public void setPresentaciones(Collection<TipoPresentacion> presentaciones) {
        this.presentaciones = presentaciones;
    }

    /**
     * @return the multiplesMap
     */
    public Map<TipoAtributoMultipleValores, StringBuilder> getMultiplesMap() {
        return multiplesMap;
    }

    /**
     * @param multiplesMap
     *            the multiplesMap to set
     */
    public void setMultiplesMap(Map<TipoAtributoMultipleValores, StringBuilder> multiplesMap) {
        this.multiplesMap = multiplesMap;
    }
}
