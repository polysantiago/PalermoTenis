package com.palermotenis.model.beans.productos.tipos;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoMultipleValores;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoSimple;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoTipado;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.beans.productos.Producto;

@Entity
@Table(name = "tipo_productos", catalog = "palermotenis", schema = "")
@NamedQueries(
    {
            @NamedQuery(name = "TipoProducto.findAll", query = "SELECT t FROM TipoProducto t ORDER BY t.nombre"),
            @NamedQuery(name = "TipoProducto.findByNombre",
                    query = "SELECT t FROM TipoProducto t WHERE t.nombre = :nombre"),
            @NamedQuery(name = "TipoProducto.findChildless", query = "select distinct t from TipoProducto as t "
                    + "join t.productos p " + "where p.activo = 1 order by t.nombre") })
@Transactional
public class TipoProducto implements Serializable {

    private static final long serialVersionUID = 7674647568570434408L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;

    @Basic(optional = false)
    @Column(name = "Presentable")
    private boolean presentable;

    @OneToMany(mappedBy = "tipoProducto", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<Producto> productos;

    @OneToMany(mappedBy = "tipoProducto", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<TipoAtributo> tiposAtributos;

    @OneToMany(mappedBy = "tipoProducto", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<Modelo> modelos;

    @OneToMany(mappedBy = "tipoProducto", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Where(clause = "padre is null")
    private Collection<Modelo> modelosRoots;

    @OneToMany(mappedBy = "tipoProducto", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<TipoPresentacion> tiposPresentacion;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "tipoProducto", updatable = false)
    @Where(clause = "Tipo='S'")
    private Collection<TipoAtributoSimple> tiposAtributosSimples;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "tipoProducto", updatable = false)
    @Where(clause = "Tipo='T'")
    private Collection<TipoAtributoTipado> tiposAtributosTipados;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "tipoProducto", updatable = false)
    @Where(clause = "Tipo='M'")
    private Collection<TipoAtributoMultipleValores> tiposAtributosMultiplesValores;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "tipoProducto", updatable = false)
    @Where(clause = "Tipo='C'")
    private Collection<TipoAtributoClasificatorio> tiposAtributosClasificatorios;

    public TipoProducto() {
    }

    public TipoProducto(String nombre, Boolean presentable) {
        this.nombre = nombre;
        this.presentable = presentable;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the productos
     */
    public Collection<Producto> getProductos() {
        return productos;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param productos
     *            the productos to set
     */
    public void setProductos(Collection<Producto> productos) {
        this.productos = productos;
    }

    /**
     * @return the tiposAtributos
     */
    public Collection<TipoAtributo> getTiposAtributos() {
        return tiposAtributos;
    }

    /**
     * @param tiposAtributos
     *            the tiposAtributos to set
     */
    public void setTiposAtributos(Collection<TipoAtributo> tiposAtributos) {
        this.tiposAtributos = tiposAtributos;
    }

    /**
     * @return the modelos
     */
    public Collection<Modelo> getModelos() {
        return modelos;
    }

    /**
     * @param modelos
     *            the modelos to set
     */
    public void setModelos(Collection<Modelo> modelos) {
        this.modelos = modelos;
    }

    public Collection<Modelo> getModelosRoots() {
        return modelosRoots;
    }

    /**
     * @return the tiposPresentacion
     */
    public Collection<TipoPresentacion> getTiposPresentacion() {
        return tiposPresentacion;
    }

    /**
     * @param tiposPresentacion
     *            the tiposPresentacion to set
     */
    public void setTiposPresentacion(Collection<TipoPresentacion> tiposPresentacion) {
        this.tiposPresentacion = tiposPresentacion;
    }

    public void addProducto(Producto producto) {
        if (!producto.getTipoProducto().getClass().isInstance(this)) {
            throw new IllegalArgumentException("El producto no corresponde a este tipo");
        }
        if (productos.contains(producto)) {
            throw new IllegalArgumentException("El producto no corresponde a este tipo");
        }
        productos.add(producto);
    }

    public void removeProducto(Producto producto) {
        if (!productos.contains(producto)) {
            throw new IllegalArgumentException("El producto no corresponde a este tipo");
        }
        productos.remove(producto);
    }

    public boolean hasTipoAtributos() {
        if (tiposAtributos == null) {
            return false;
        } else {
            return !tiposAtributos.isEmpty();
        }
    }

    public Collection<TipoAtributoSimple> getTipoAtributoSimples() {
        return tiposAtributosSimples;
    }

    public Collection<TipoAtributoClasificatorio> getTiposAtributoClasificatorios() {
        return tiposAtributosClasificatorios;
    }

    public Collection<TipoAtributoTipado> getTiposAtributoTipados() {
        return tiposAtributosTipados;
    }

    public Collection<TipoAtributoMultipleValores> getTiposAtributoMultiples() {
        return tiposAtributosMultiplesValores;
    }

    public boolean hasTiposAtributos() {
        if (tiposAtributos == null) {
            return false;
        } else {
            return !tiposAtributos.isEmpty();
        }
    }

    /**
     * @return the presentable
     */
    public boolean isPresentable() {
        return presentable;
    }

    /**
     * @param presentable
     *            the presentable to set
     */
    public void setPresentable(Boolean presentable) {
        this.presentable = presentable;
    }

    public boolean isClasificable() {
        if (tiposAtributosClasificatorios == null) {
            return false;
        }
        return !tiposAtributosClasificatorios.isEmpty();
    }
}
