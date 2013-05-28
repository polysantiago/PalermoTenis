package com.palermotenis.model.beans.productos;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Where;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.atributos.Atributo;
import com.palermotenis.model.beans.atributos.AtributoClasificatorio;
import com.palermotenis.model.beans.atributos.AtributoMultipleValores;
import com.palermotenis.model.beans.atributos.AtributoSimple;
import com.palermotenis.model.beans.atributos.AtributoTipado;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoMultipleValores;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoTipado;
import com.palermotenis.model.beans.compras.Costo;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.precios.PrecioPresentacion;
import com.palermotenis.model.beans.precios.PrecioUnidad;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.exceptions.IllegalValueException;

@Entity
@Table(name = "productos", catalog = "palermotenis", schema = "")
@NamedQueries(
    {
            @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
            @NamedQuery(name = "Producto.findByModelo", query = "SELECT p FROM Producto p WHERE p.modelo = :modelo"),
            @NamedQuery(name = "Producto.findByTipoProducto",
                    query = "SELECT p FROM Producto p WHERE p.tipoProducto = :tipoProducto"),
            @NamedQuery(name = "Producto.findOfertas", query = "SELECT DISTINCT p FROM Producto p "
                    + "LEFT JOIN p.preciosUnidad pr " + "LEFT JOIN p.preciosCantidad pc " + "JOIN p.stocks s "
                    + "WHERE pr.enOferta = 1 OR pc.enOferta = 1 AND s.stock > 0"),
            @NamedQuery(name = "Producto.findCountByTipoAndMarca", query = "SELECT t, m, "
                    + "(SELECT COUNT(*) FROM Producto p WHERE p.tipoProducto = t AND p.modelo.marca = m) "
                    + "FROM TipoProducto t, Marca m ORDER BY t.nombre, m.nombre"),
            @NamedQuery(
                    name = "Producto.findProductosRelacionados",
                    query = "SELECT DISTINCT pp.id.stock.producto "
                            + "FROM Pedido p "
                            + "JOIN p.pedidosProductos pp "
                            + "WHERE pp.id.stock.producto != :producto AND pp.id.stock.stock > 0 AND pp.id.stock.producto.activo = 1 "
                            + "AND p IN (SELECT pp2.id.pedido FROM Pedido pd JOIN pd.pedidosProductos pp2 WHERE pp2.id.stock.producto = :producto)") })
public class Producto implements Serializable {

    private static final long serialVersionUID = 4951192844589939898L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = true)
    @Column(name = "Descripcion")
    private String descripcion;

    @Basic(optional = false)
    @Column(name = "Activo", insertable = false)
    private boolean activo;

    @ManyToOne()
    @JoinColumn(name = "TipoProducto")
    private TipoProducto tipoProducto;

    @OneToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "Modelo", unique = true)
    private Modelo modelo;

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Atributo> atributos;

    @OrderBy(clause = "valor")
    @OneToMany(mappedBy = "id.producto", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PrecioUnidad> preciosUnidad = Lists.newArrayList();

    @OrderBy(clause = "valor")
    @OneToMany(mappedBy = "id.producto", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PrecioPresentacion> preciosCantidad = Lists.newArrayList();

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Stock> stocks = Lists.newArrayList();

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<Costo> costos;

    @Formula(value = "(SELECT SUM(s.stock) FROM stock s WHERE s.producto = ID)")
    @Basic(fetch = FetchType.LAZY)
    private Integer stockSum;

    @OrderBy(clause = "valor")
    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "productos", targetEntity = Presentacion.class)
    @Where(
            clause = "(SELECT SUM(s.stock) FROM stock s WHERE s.Presentacion = presentaci0_.Presentacion AND s.Producto = presentaci0_.Producto GROUP BY s.Presentacion) > 0 "
                    + "AND (SELECT COUNT(*) FROM precios_presentaciones pp WHERE pp.Producto = presentaci0_.Producto AND pp.Presentacion = presentaci0_.Presentacion) > 0")
    private Set<Presentacion> presentaciones;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "Producto", updatable = false)
    @Where(clause = "(SELECT t.tipo FROM tipo_atributos t WHERE t.ID = TipoAtributo) = 'S'")
    private List<AtributoSimple> atributosSimples;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "Producto", updatable = false)
    @Where(clause = "(SELECT t.tipo FROM tipo_atributos t WHERE t.ID = TipoAtributo) = 'T'")
    private List<AtributoTipado> atributosTipados;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "Producto", updatable = false)
    @Where(
            clause = "(SELECT t.tipo FROM tipo_atributos t WHERE t.ID = TipoAtributo) = 'C' "
                    + "AND (SELECT s.stock FROM stock s WHERE s.ValorClasificatorio = ValorPosible AND s.Producto = Producto) > 0")
    private List<AtributoClasificatorio> atributosClasificatorios;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "Producto", updatable = false)
    @Where(clause = "(SELECT t.tipo FROM tipo_atributos t WHERE t.ID = TipoAtributo) = 'M'")
    private List<AtributoMultipleValores> atributosMultiplesValores;

    public Producto() {
    }

    public Producto(String descripcion, TipoProducto tipoProducto, Modelo modelo) {
        this.descripcion = descripcion;
        this.tipoProducto = tipoProducto;
        this.modelo = modelo;
    }

    public Producto(Collection<Precio> precios, TipoProducto tipoProducto, Modelo modelo) {
        this.tipoProducto = tipoProducto;
        this.modelo = modelo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcionCorta() {
        String[] palabras = descripcion.split(" ", 21);
        if (palabras.length < 21) {
            return descripcion;
        } else {
            String descCorta = "";
            for (int i = 0; i < 20; i++) {
                descCorta += palabras[i] + " ";
            }
            return descCorta.trim() + "...";
        }
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    /**
     * @return the modelo
     */
    public Modelo getModelo() {
        return modelo;
    }

    /**
     * @param modelo
     *            the modelo to set
     */
    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the presentaciones
     */
    public Collection<Presentacion> getPresentaciones() {
        return presentaciones;
    }

    /**
     * @param presentaciones
     *            the presentaciones to set
     */
    public void setPresentaciones(Set<Presentacion> presentaciones) {
        this.presentaciones = presentaciones;
    }

    public Collection<? extends Precio> getPrecios() {
        if (CollectionUtils.isNotEmpty(preciosUnidad)) {
            return preciosUnidad;
        }
        return preciosCantidad;
    }

    public void addPrecio(Precio precio) {
        if (hasPrecio(precio)) {
            return;
        }
        if (precio instanceof PrecioUnidad) {
            preciosUnidad.add((PrecioUnidad) precio);
        } else if (precio instanceof PrecioPresentacion) {
            preciosCantidad.add((PrecioPresentacion) precio);
        }
        precio.getId().setProducto(this);
    }

    public void removePrecio(Precio precio) {
        if (!hasPrecio(precio)) {
            return;
        }
        if (precio instanceof PrecioUnidad) {
            preciosUnidad.remove(precio);
        } else if (precio instanceof PrecioPresentacion) {
            preciosCantidad.remove(precio);
        }
    }

    public final boolean hasPrecio(Precio precio) {
        return preciosUnidad.contains(precio) || preciosCantidad.contains(precio);
    }

    public Collection<PrecioUnidad> getPreciosUnidad() {
        return preciosUnidad;
    }

    public void setPreciosUnidad(List<PrecioUnidad> preciosUnidad) {
        this.preciosUnidad = preciosUnidad;
    }

    public Collection<PrecioPresentacion> getPreciosCantidad() {
        return preciosCantidad;
    }

    public void setPreciosCantidad(List<PrecioPresentacion> preciosCantidad) {
        this.preciosCantidad = preciosCantidad;
    }

    public Collection<Costo> getCostos() {
        return costos;
    }

    public void setCostos(Collection<Costo> costos) {
        this.costos = costos;
    }

    public void addCosto(Costo costo) {
        if (hasCosto(costo)) {
            return;
        }
        costos.add(costo);
    }

    public void removeCosto(Costo costo) {
        if (!hasCosto(costo)) {
            return;
        }
        costos.remove(costo);
    }

    public boolean hasCosto(Costo costo) {
        return costos.contains(costo);
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public boolean hasStock() {
        return getStockSum() > 0;
    }

    public Integer getStockSum() {
        return stockSum;
    }

    public void setStockSum(Integer stockSum) {
        this.stockSum = stockSum;
    }

    public void addStock(Stock stock) {
        if (stocks.contains(stock)) {
            return;
        }
        stocks.add(stock);
        stock.setProducto(this);
    }

    public void removeStock(Stock stock) {
        if (!stocks.contains(stock)) {
            return;
        }
        stocks.remove(stock);
        stock.setProducto(null);
    }

    public boolean hasPedidos() {
        if (CollectionUtils.isEmpty(stocks)) {
            return false;
        }
        for (Stock stock : stocks) {
            if (stock.hasPedidos()) {
                return true;
            }
        }
        return false;
    }

    public void addPrecioCantidad(PrecioPresentacion precio) {
        if (hasPrecioCantidad(precio)) {
            return;
        }
        preciosCantidad.add(precio);
    }

    public void removePrecioCantidad(PrecioPresentacion precio) {
        if (!hasPrecioCantidad(precio)) {
            return;
        }
        preciosCantidad.remove(precio);
    }

    public boolean hasPrecioCantidad(PrecioPresentacion precio) {
        return preciosCantidad.contains(precio);
    }

    public boolean hasPreciosCantidad() {
        if (preciosCantidad == null) {
            return false;
        }
        return !preciosCantidad.isEmpty();
    }

    public void addAtributo(AtributoSimple atributo) throws IllegalValueException {
        if (atributos == null) {
            atributos = Lists.newArrayList();
        } else if (hasAtributo(atributo)) {
            throw new IllegalValueException("El producto ya contiente este atributo");
        }
        atributos.add(atributo);
    }

    public void removeAtributo(AtributoSimple atributo) throws IllegalValueException {
        if (!hasAtributo(atributo)) {
            throw new IllegalValueException("El producto no contiente este atributo");
        }
        atributos.remove(atributo);
    }

    public boolean hasAtributos() {
        if (atributos == null) {
            return false;
        }
        return !atributos.isEmpty();
    }

    public boolean hasAtributo(AtributoSimple atributo) {
        return atributos.contains(atributo);
    }

    public boolean isPresentable() {
        return CollectionUtils.isNotEmpty(presentaciones);
    }

    public boolean isClasificable() {
        return tipoProducto.isClasificable();
    }

    public List<Atributo> getAtributos() {
        List<AtributoSimple> allAtributosSimples = getAtributosSimples();
        List<AtributoTipado> allAtributosTipados = getAtributosTipados();
        if (allAtributosSimples != null && allAtributosTipados != null) {
            List<Atributo> allAtributos = Lists.newArrayList();
            Iterables.addAll(allAtributos, getAtributosSimples());
            Iterables.addAll(allAtributos, getAtributosTipados());
            return allAtributos;
        } else {
            return atributos;
        }
    }

    public List<AtributoSimple> getAtributosSimples() {
        Collections.sort(atributosSimples, new AtributosComparator());
        return atributosSimples;
    }

    public List<AtributoTipado> getAtributosTipados() {
        Collections.sort(atributosTipados, new AtributosComparator());
        return atributosTipados;
    }

    public List<AtributoClasificatorio> getAtributosClasificatorios() {
        Collections.sort(atributosClasificatorios, new AtributosComparator());
        return atributosClasificatorios;
    }

    public List<AtributoMultipleValores> getAtributosMultiplesValores() {
        Collections.sort(atributosMultiplesValores, new AtributosComparator());
        return atributosMultiplesValores;
    }

    public Set<TipoAtributoClasificatorio> getTipoAtributosClasificatorios() {
        return createSet(AtributoClasificatorio.class);
    }

    public Set<TipoAtributoMultipleValores> getTipoAtributosMultiplesValores() {
        return createSet(AtributoMultipleValores.class);
    }

    // Generic methods
    @SuppressWarnings("unchecked")
    private <T extends TipoAtributoTipado, S extends AtributoTipado> Set<T> createSet(Class<S> clazz) {
        Set<T> newAtributosSet = null;
        Collection<S> atributosCollection = createCollectionAtributos(clazz);

        if (CollectionUtils.isNotEmpty(atributosCollection)) {
            newAtributosSet = Sets.newHashSet();
            for (AtributoTipado atributo : atributosCollection) {
                newAtributosSet.add((T) atributo.getTipoAtributo());
            }
        }

        return newAtributosSet;
    }

    @SuppressWarnings("unchecked")
    private <T extends AtributoTipado> Collection<T> createCollectionAtributos(Class<T> clazz) {
        Collection<T> atributosT = Lists.newArrayList();
        for (AtributoTipado a : atributosTipados) {
            if (clazz.isAssignableFrom(a.getClass())) {
                atributosT.add((T) a);
            }
        }
        return atributosT;
    }

    @Override
    public String toString() {
        return "Producto[nombre=" + modelo.getNombre() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Producto other = (Producto) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    private class AtributosComparator implements Comparator<Atributo> {

        @Override
        public int compare(Atributo atributo, Atributo otro) {
            return atributo.getTipoAtributo().getNombre().compareTo(otro.getTipoAtributo().getNombre());
        }

    }
}
