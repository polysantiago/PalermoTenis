/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans.productos;

import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.beans.atributos.Atributo;
import com.palermotenis.model.beans.atributos.AtributoClasificatorio;
import com.palermotenis.model.beans.atributos.AtributoMultipleValores;
import com.palermotenis.model.beans.atributos.AtributoTipado;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoMultipleValores;
import com.palermotenis.model.beans.compras.Costo;
import com.palermotenis.model.beans.precios.PrecioPresentacion;
import com.palermotenis.model.beans.precios.PrecioUnidad;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.exceptions.IllegalValueException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Where;

/**
 *
 * @author Poly
 */
@Entity
@Table(name = "productos", catalog = "palermotenis", schema = "")
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByTipoProducto",
    query = "SELECT p FROM Producto p WHERE p.tipoProducto = :tipoProducto"),
    @NamedQuery(name = "Producto.findOfertas",
    query = "SELECT DISTINCT p FROM Producto p "
            + "LEFT JOIN p.preciosUnidad pr "
            + "LEFT JOIN p.preciosCantidad pc "
            + "JOIN p.stocks s "
            + "WHERE pr.enOferta = 1 OR pc.enOferta = 1 AND s.stock > 0"),
    @NamedQuery(name = "Producto.findCountByTipoAndMarca",
    query = "SELECT t, m, "
            + "(SELECT COUNT(*) FROM Producto p WHERE p.tipoProducto = t AND p.modelo.marca = m) "
            + "FROM TipoProducto t, Marca m "
            + "ORDER BY t.nombre, m.nombre"),
    @NamedQuery(name = "Producto.findProductosRelacionados",
    query = "SELECT DISTINCT pp.id.stock.producto "
    + "FROM Pedido p "
    + "JOIN p.pedidosProductos pp "
    + "WHERE pp.id.stock.producto != :producto AND pp.id.stock.stock > 0 AND pp.id.stock.producto.activo = 1 "
        + "AND p IN (SELECT pp2.id.pedido FROM Pedido pd JOIN pd.pedidosProductos pp2 WHERE pp2.id.stock.producto = :producto)")
})
public class Producto implements Serializable {

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
    private Collection<Atributo> atributos;

    @OrderBy(clause = "valor")
    @OneToMany(mappedBy = "id.producto", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<PrecioUnidad> preciosUnidad;

    @OrderBy(clause = "valor")
    @OneToMany(mappedBy = "id.producto", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<PrecioPresentacion> preciosCantidad;
    
    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<Stock> stocks;

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<Costo> costos;

    @Formula(value = "(SELECT SUM(s.stock) FROM stock s WHERE s.producto = ID)")
    private Integer stock;

    @OrderBy(clause = "valor")
    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "productos", targetEntity = Presentacion.class)
    @Where(clause = "(SELECT SUM(s.stock) FROM stock s WHERE s.Presentacion = presentaci0_.Presentacion AND s.Producto = presentaci0_.Producto GROUP BY s.Presentacion) > 0 " +
    "AND (SELECT COUNT(*) FROM precios_presentaciones pp WHERE pp.Producto = presentaci0_.Producto AND pp.Presentacion = presentaci0_.Presentacion) > 0")
    private Set<Presentacion> presentaciones;

    @OrderBy(clause = "tipoatribu1_.nombre")
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "Producto", updatable = false)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @Where(clause = "(SELECT t.tipo FROM tipo_atributos t WHERE t.ID = TipoAtributo) = 'S'")
    private Collection<Atributo> atributosSimples;

    @OrderBy(clause = "tipoatribu1_.nombre")
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "Producto", updatable = false)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @Where(clause = "(SELECT t.tipo FROM tipo_atributos t WHERE t.ID = TipoAtributo) = 'T'")
    private Collection<AtributoTipado> atributosTipados;

    @OrderBy(clause = "tipoatribu1_.nombre")
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "Producto", updatable = false)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @Where(clause = "(SELECT t.tipo FROM tipo_atributos t WHERE t.ID = TipoAtributo) = 'C' " +
    "AND (SELECT s.stock FROM stock s WHERE s.ValorClasificatorio = ValorPosible AND s.Producto = Producto) > 0")
    private Collection<AtributoClasificatorio> atributosClasificatorios;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Producto", updatable = false)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @Where(clause = "(SELECT t.tipo FROM tipo_atributos t WHERE t.ID = TipoAtributo) = 'M'")
    private Collection<AtributoMultipleValores> atributosMultiplesValores;

    public Producto() {
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

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
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
     * @param modelo the modelo to set
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
     * @param presentaciones the presentaciones to set
     */
    public void setPresentaciones(Set<Presentacion> presentaciones) {
        this.presentaciones = presentaciones;
    }

    /**
     * @return the precios
     */
    public Collection<? extends Precio> getPrecios() {
        if(preciosUnidad != null && !preciosUnidad.isEmpty())
            return preciosUnidad;
        else
            return preciosCantidad;
    }   

    public void addPrecio(PrecioUnidad precio) {
        if (hasPrecio(precio)) {
            //TODO handle exception
        }
        preciosUnidad.add(precio);
    }

    public void removePrecio(PrecioUnidad precio) {
        if (!hasPrecio(precio)) {
            //TODO handle exception
        }
        preciosUnidad.remove(precio);
    }

    public final boolean hasPrecio(PrecioUnidad precio) {
        return preciosUnidad.contains(precio);
    }

    public Collection<PrecioUnidad> getPreciosUnidad() {
        return preciosUnidad;
    }

    public void setPreciosUnidad(Collection<PrecioUnidad> preciosUnidad) {
        this.preciosUnidad = preciosUnidad;
    }

    /**
     * @return the preciosCantidad
     */
    public Collection<PrecioPresentacion> getPreciosCantidad() {
        return preciosCantidad;
    }

    /**
     * @param preciosCantidad the preciosCantidad to set
     */
    public void setPreciosCantidad(Collection<PrecioPresentacion> preciosCantidad) {
        this.preciosCantidad = preciosCantidad;
    }

    /**
     * @return the costos
     */
    public Collection<Costo> getCostos() {
        return costos;
    }

    /**
     * @param costos the costos to set
     */
    public void setCostos(Collection<Costo> costos) {
        this.costos = costos;
    }

    public void addCosto(Costo costo) {
        if (hasCosto(costo)) {
            //TODO handle exception
        }
        costos.add(costo);
    }

    public void removeCosto(Costo costo) {
        if (!hasCosto(costo)) {
            //TODO handle exception
        }
        costos.remove(costo);
    }

    public boolean hasCosto(Costo costo) {
        return costos.contains(costo);
    }

    /**
     * @return the stocks
     */
    public Collection<Stock> getStocks() {
        return stocks;
    }

    /**
     * @param stocks the stocks to set
     */
    public void setStocks(Collection<Stock> stocks) {
        this.stocks = stocks;
    }

    public boolean hasStock() {
        return (stock > 0);
    }

    public boolean hasPedidos(){
        if(stocks == null || stocks.isEmpty()){
            return false;
        } else {
            for(Stock s : stocks){
                if(s.hasPedidos()){
                    return true;
                }
            }
            return false;
        }
    }

    public void addPrecioCantidad(PrecioPresentacion precio) {
        if (hasPrecioCantidad(precio)) {
            //TODO handle exception
        }
        preciosCantidad.add(precio);
    }

    public void removePrecioCantidad(PrecioPresentacion precio) {
        if (!hasPrecioCantidad(precio)) {
            //TODO handle exception
        }
        preciosCantidad.remove(precio);
    }

    public boolean hasPrecioCantidad(PrecioPresentacion precio) {
        return preciosCantidad.contains(precio);
    }

    public boolean hasPreciosCantidad() {
        if (preciosCantidad == null) {
            return false;
        } else {
            return !preciosCantidad.isEmpty();
        }
    }

    public void addAtributo(Atributo atributo) throws IllegalValueException {
        if (atributos == null) {
            atributos = new ArrayList<Atributo>();
        } else if (hasAtributo(atributo)) {
            throw new IllegalValueException("El producto ya contiente este atributo");
        }
        atributos.add(atributo);
    }

    public void removeAtributo(Atributo atributo) throws IllegalValueException {
        if (!hasAtributo(atributo)) {
            throw new IllegalValueException("El producto no contiente este atributo");
        }
        atributos.remove(atributo);
    }

    public boolean hasAtributos() {
        if (atributos == null) {
            return false;
        } else {
            return !atributos.isEmpty();
        }
    }

    public boolean hasAtributo(Atributo atributo) {
        return atributos.contains(atributo);
    }

    public boolean isPresentable() {
        if (presentaciones == null) {
            return false;
        } else {
            return !presentaciones.isEmpty();
        }

    }

    public Collection<Atributo> getAtributos() {
        Collection<Atributo> allAtributosSimples = getAtributosSimples();
        Collection<AtributoTipado> allAtributosTipados = getAtributosTipados();
        if (allAtributosSimples != null && allAtributosTipados != null) {
            allAtributosSimples.addAll(getAtributosTipados());
            return allAtributosSimples;
        } else {
            return atributos;
        }
    }

    public Collection<Atributo> getAtributosSimples() {
        return atributosSimples;
    }

    /**
     * @return the atributosTipados
     */
    public Collection<AtributoTipado> getAtributosTipados() {
        return atributosTipados;
    }

    public Collection<AtributoClasificatorio> getAtributosClasificatorios() {
        return atributosClasificatorios;
    }

    public Collection<AtributoMultipleValores> getAtributosMultiplesValores() {
        return atributosMultiplesValores;
    }

    public Set<TipoAtributoClasificatorio> getTipoAtributosClasificatorios() {
        return createSet(AtributoClasificatorio.class);
    }

    public Set<TipoAtributoMultipleValores> getTipoAtributosMultiplesValores() {
        return createSet(AtributoMultipleValores.class);
    }


    // Generic methods
    private <T extends TipoAtributo, S extends Atributo> Set<T> createSet(Class<S> clazz) {
        Set<T> newAtributosSet = null;
        Collection<S> atributosCollection = createCollectionAtributos(clazz);

        if (atributosCollection != null && !atributosCollection.isEmpty()) {
            newAtributosSet = new HashSet<T>();
            for (Atributo a : atributosCollection) {
                newAtributosSet.add((T) a.getTipoAtributo());
            }
        }

        return newAtributosSet;
    }

    private <T extends Atributo> Collection<T> createCollectionAtributos(Class<T> clazz) {
        Collection<T> atributosT = null;
        if (hasAtributos()) {
            atributosT = new ArrayList<T>();
            for (Atributo a : atributos) {
                if (a.getClass() == clazz) {
                    T at = (T) a;
                    atributosT.add(at);
                }
            }
        }
        return atributosT;
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
}
