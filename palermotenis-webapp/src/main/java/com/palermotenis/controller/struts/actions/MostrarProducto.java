package com.palermotenis.controller.struts.actions;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.atributos.AtributoMultipleValores;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoMultipleValores;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoSimple;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.service.atributos.tipos.TipoAtributoService;
import com.palermotenis.model.service.productos.ProductoService;

public class MostrarProducto extends ActionSupport {

    private static final long serialVersionUID = 6896818100286801184L;

    private Integer id;

    private Producto producto;

    private List<TipoAtributoSimple> atributosSimples = Lists.newArrayList();
    private List<TipoAtributoClasificatorio> atributosClasificatorios = Lists.newArrayList();
    private List<TipoAtributoMultipleValores> atributosMultiples = Lists.newArrayList();
    private Set<TipoPresentacion> presentaciones = Sets.newHashSet();

    private Map<TipoAtributoMultipleValores, String> multiplesMap = Maps.newHashMap();

    @Autowired
    private TipoAtributoService tipoAtributoService;

    @Autowired
    private ProductoService productoService;

    @Override
    public String execute() {
        producto = productoService.getProductoByModelo(id);

        Integer tipoProductoId = producto.getTipoProducto().getId();
        createAtributosCollections(tipoAtributoService.getTiposAtributosByTipoProducto(tipoProductoId));

        if (producto.isPresentable()) {
            for (Presentacion presentacion : getProducto().getPresentaciones()) {
                TipoPresentacion tipoPresentacion = presentacion.getTipoPresentacion();
                tipoPresentacion.addPresentacionByProd(presentacion);
                presentaciones.add(tipoPresentacion);
            }
        }

        for (TipoAtributoMultipleValores tipoAtributo : atributosMultiples) {
            List<String> valoresPosibles = Lists.newArrayList();
            for (AtributoMultipleValores atributo : producto.getAtributosMultiplesValores()) {
                if (tipoAtributo.equals(atributo.getTipoAtributo())) {
                    valoresPosibles.add(atributo.getValorPosible().getNombre());
                }
            }
            multiplesMap.put(tipoAtributo, Joiner.on(", ").join(valoresPosibles));
        }
        return SUCCESS;
    }

    private void createAtributosCollections(Collection<TipoAtributo> atributos) {
        Iterables.addAll(atributosSimples, Iterables.filter(atributos, TipoAtributoSimple.class));
        Iterables.addAll(atributosClasificatorios, Iterables.filter(atributos, TipoAtributoClasificatorio.class));
        Iterables.addAll(atributosMultiples, Iterables.filter(atributos, TipoAtributoMultipleValores.class));
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public List<TipoAtributoSimple> getAtributosSimples() {
        return atributosSimples;
    }

    public void setAtributosSimples(List<TipoAtributoSimple> atributosSimples) {
        this.atributosSimples = atributosSimples;
    }

    public List<TipoAtributoClasificatorio> getAtributosClasificatorios() {
        return atributosClasificatorios;
    }

    public void setAtributosClasificatorios(List<TipoAtributoClasificatorio> atributosClasificatorios) {
        this.atributosClasificatorios = atributosClasificatorios;
    }

    public List<TipoAtributoMultipleValores> getAtributosMultiples() {
        return atributosMultiples;
    }

    public void setAtributosMultiples(List<TipoAtributoMultipleValores> atributosMultiples) {
        this.atributosMultiples = atributosMultiples;
    }

    public Set<TipoPresentacion> getPresentaciones() {
        return presentaciones;
    }

    public void setPresentaciones(Set<TipoPresentacion> presentaciones) {
        this.presentaciones = presentaciones;
    }

    public Map<TipoAtributoMultipleValores, String> getMultiplesMap() {
        return multiplesMap;
    }

    public void setMultiplesMap(Map<TipoAtributoMultipleValores, String> multiplesMap) {
        this.multiplesMap = multiplesMap;
    }
}
