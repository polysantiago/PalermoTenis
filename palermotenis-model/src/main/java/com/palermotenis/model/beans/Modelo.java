package com.palermotenis.model.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.palermotenis.model.beans.imagenes.Imagen;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;

@Entity
@Table(name = "modelos", catalog = "palermotenis", schema = "")
@NamedQueries(
    {
            @NamedQuery(name = "Modelo.findAll", query = "SELECT m FROM Modelo m"),
            @NamedQuery(name = "Modelo.findRoots", query = "SELECT m FROM Modelo m WHERE m.padre is null"),
            @NamedQuery(name = "Modelo.findById", query = "SELECT m FROM Modelo m WHERE m.id = :id"),
            @NamedQuery(name = "Modelo.findByNombre", query = "SELECT m FROM Modelo m WHERE m.nombre = :nombre"),
            @NamedQuery(name = "Modelo.findByPadre", query = "SELECT m FROM Modelo m WHERE m.padre = :padre"),
            @NamedQuery(name = "Modelo.findMaxRight", query = "SELECT MAX(m.right) FROM Modelo m"),
            @NamedQuery(name = "Modelo.findByPadre-Active", query = "SELECT m FROM Modelo m LEFT JOIN m.producto p "
                    + "WHERE m.padre = :padre " + "AND (p = null OR (p.activo = 1 AND p.stockSum > 0))"),
            @NamedQuery(name = "Modelo.findByTipoProducto", query = "SELECT DISTINCT m FROM Modelo AS m "
                    + "WHERE m.padre = null " + "AND m.tipoProducto = :tipoProducto ORDER BY m.nombre"),
            @NamedQuery(name = "Modelo.findByMarca,TipoProducto", query = "SELECT DISTINCT m FROM Modelo AS m "
                    + "WHERE m.marca = :marca AND m.padre = null AND m.tipoProducto = :tipoProducto "
                    + "ORDER BY m.left"),
            @NamedQuery(name = "Modelo.findByMarca,TipoProducto-Active",
                    query = "SELECT DISTINCT m FROM Modelo m LEFT JOIN m.producto p "
                            + "WHERE  m.marca = :marca AND m.padre = null "
                            + "AND m.tipoProducto = :tipoProducto AND (p = null OR (p.activo = 1 AND p.stockSum > 0)) "
                            + "ORDER BY m.orden"),
            @NamedQuery(name = "Modelo.findByMarca,TipoProducto-Leafs", query = "SELECT DISTINCT m FROM Modelo AS m "
                    + "JOIN m.producto p WHERE m.marca = :marca AND m.tipoProducto = :tipoProducto "
                    + "AND (m.right - m.left) = 1 AND p.activo = 1 AND p.stockSum > 0 ORDER BY m.orden"),
            @NamedQuery(
                    name = "Modelo.editTree",
                    query = "UPDATE Modelo m "
                            + "SET m.left = (CASE WHEN m.left > :right THEN (m.left + 2) ELSE m.left END),"
                            + "m.right = (CASE WHEN m.right >= :right THEN (m.right + 2) ELSE m.right END) WHERE m.right >= :right"),
            @NamedQuery(name = "Modelo.editRightAfterDelete",
                    query = "UPDATE Modelo m SET m.right = (m.right - :width) WHERE m.right > :right"),
            @NamedQuery(name = "Modelo.editLeftAfterDelete",
                    query = "UPDATE Modelo m SET m.left = (m.left - :width) WHERE m.left > :right"),
            @NamedQuery(name = "Modelo.destroyRange", query = "DELETE Modelo m WHERE m.left BETWEEN :left AND :right") })
public class Modelo implements Serializable, Comparable<Modelo> {

    private static final long serialVersionUID = 6859991086725173651L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", insertable = false)
    private Integer id;

    @Basic(optional = false)
    @Column(name = "lft")
    private Integer left;

    @Basic(optional = false)
    @Column(name = "rgt")
    private Integer right;

    @Basic(optional = false)
    @Column(name = "orden")
    private Integer orden;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "padre", referencedColumnName = "ID")
    private Modelo padre;

    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;

    @JoinColumn(name = "tipoProducto", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TipoProducto tipoProducto;

    @OneToMany(mappedBy = "padre", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<Modelo> hijos = Sets.newHashSet();

    @OneToOne(mappedBy = "modelo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Producto producto;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "categorias_modelos", joinColumns = @JoinColumn(name = "modelo"),
            inverseJoinColumns = @JoinColumn(name = "categoria"))
    private final Set<Categoria> categorias = Sets.newHashSet();

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "modelo", cascade = CascadeType.REMOVE)
    private List<Imagen> imagenes;

    @JoinColumn(name = "marca", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Marca marca;

    public Modelo() {
    }

    public Modelo(String text) {
        this.nombre = text;
    }

    public Modelo(String text, Modelo padre) {
        this.nombre = text;
        this.padre = padre;
    }

    public Modelo(Modelo padre, String nombre, TipoProducto tipoProducto, Marca marca) {
        this.padre = padre;
        this.nombre = nombre;
        this.tipoProducto = tipoProducto;
        this.marca = marca;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Modelo getPadre() {
        return padre;
    }

    public void setPadre(Modelo padre) {
        this.padre = padre;
    }

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    public Integer getWidth() {
        return (this.right - this.left) + 1;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    public Collection<Modelo> getHijos() {
        List<Modelo> hijosList = Lists.newArrayList(hijos);
        Collections.sort(hijosList);
        return hijos;
    }

    public void setHijos(Collection<Modelo> hijos) {
        this.hijos = hijos;
    }

    public void addHijo(Modelo hijo) {
        if (hasHijo(hijo)) {
            return;
        }
        hijos.add(hijo);
        hijo.setPadre(this);
    }

    public void removeHijo(Modelo hijo) {
        if (!hasHijo(hijo)) {
            return;
        }
        hijos.remove(hijo);
        hijo.setPadre(null);
    }

    public boolean hasHijo(Modelo hijo) {
        return hijos.contains(hijo);
    }

    public Marca getMarca() {
        return marca;
    }

    public Set<Categoria> getCategorias() {
        return Sets.newHashSet(categorias);
    }

    public void addCategoria(Categoria categoria) {
        if (categorias.contains(categoria)) {
            return;
        }
        categorias.add(categoria);
        categoria.addModelo(this);
    }

    public void removeCategoria(Categoria categoria) {
        if (!categorias.contains(categoria)) {
            return;
        }
        categorias.remove(categoria);
        categoria.removeModelo(this);
    }

    public boolean hasCategoria(Categoria categoria) {
        return categorias.contains(categoria);
    }

    public Collection<Modelo> getPadres() {
        List<Modelo> padres = new ArrayList<Modelo>();
        Modelo current = this;
        while (current.getPadre() != null) {
            padres.add(current.getPadre());
            current = current.getPadre();
        }
        Collections.sort(padres);
        return padres;
    }

    @Override
    public int compareTo(Modelo m) {
        return left.compareTo(m.left);
    }

    public boolean isRoot() {
        return (padre == null);
    }

    public boolean isLeaf() {
        return (right - left) == 1;
    }

    public boolean isProducible() {
        return (producto != null);
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return "Modelo[" + id + "," + nombre + "]";
    }
}
