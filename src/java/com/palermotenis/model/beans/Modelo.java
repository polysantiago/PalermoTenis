/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans;

import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.imagenes.Imagen;
import com.palermotenis.model.exceptions.IllegalValueException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLInsert;

/**
 *
 * @author Poly
 */
@Entity
@Table(name = "modelos", catalog = "palermotenis", schema = "")
@SQLInsert(callable = true, check = ResultCheckStyle.COUNT, sql = "{call insertarModelo(?,?,?,?)}")
@SQLDelete(callable = true, check = ResultCheckStyle.COUNT, sql = "{call borrarModelo(?,?)}")
@NamedQueries({
    @NamedQuery(name = "Modelo.findAll", query = "SELECT m FROM Modelo m"),
    @NamedQuery(name = "Modelo.findRoots", query = "SELECT m FROM Modelo m WHERE m.padre is null"),
    @NamedQuery(name = "Modelo.findById", query = "SELECT m FROM Modelo m WHERE m.id = :id"),
    @NamedQuery(name = "Modelo.findByNombre", query = "SELECT m FROM Modelo m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Modelo.findByPadre", query = "SELECT m FROM Modelo m WHERE m.padre = :padre"),
    @NamedQuery(name = "Modelo.findByPadre-Active",
    query = "SELECT m FROM Modelo m LEFT JOIN m.producto p " +
                "WHERE m.padre = :padre " +
                "AND (p = null OR (p.activo = 1 AND p.stock > 0))"),
    @NamedQuery(name = "Modelo.findByTipoProducto",
    query = "SELECT DISTINCT m FROM Modelo AS m " +
            "WHERE m.padre = null " +
            "AND m.tipoProducto = :tipoProducto " +
            "ORDER BY m.nombre"),
    @NamedQuery(name = "Modelo.findByMarca,TipoProducto",
    query = "SELECT DISTINCT m FROM Modelo AS m " +
            "WHERE  m.marca = :marca AND m.padre = null " +
            "AND m.tipoProducto = :tipoProducto " +
            "ORDER BY m.left"),
    @NamedQuery(name = "Modelo.findByMarca,TipoProducto-Active",
    query = "SELECT DISTINCT m FROM Modelo m LEFT JOIN m.producto p " +
            "WHERE  m.marca = :marca AND m.padre = null " +
            "AND m.tipoProducto = :tipoProducto AND (p = null OR (p.activo = 1 AND p.stock > 0)) " +
            "ORDER BY m.orden"),
    @NamedQuery(name = "Modelo.findByMarca,TipoProducto-Leafs",
    query = "SELECT DISTINCT m FROM Modelo AS m " +
            "JOIN m.producto p " +
            "WHERE  m.marca = :marca " +
            "AND m.tipoProducto = :tipoProducto " +
            "AND (m.right - m.left) = 1 AND p.activo = 1 " +
            "AND p.stock > 0 " +
            "ORDER BY m.orden")
})

public class Modelo implements Serializable, Comparable<Modelo> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", insertable = false)
    private Integer id;

    @Basic(optional = false)
    @Column(name = "lft", insertable = false)
    private Integer left;

    @Basic(optional = false)
    @Column(name = "rgt", insertable = false)
    private Integer right;

    @Basic(optional = false)
    @Column(name = "orden", insertable = false)
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
    private Collection<Modelo> hijos;

    @OneToOne(mappedBy = "modelo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Producto producto;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "deportes_modelos",
        joinColumns = @JoinColumn(name = "modelo"),
        inverseJoinColumns = @JoinColumn(name = "deporte"))
    private Collection<Deporte> deportes;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "modelo", cascade = CascadeType.REMOVE)
    private Collection<Imagen> imagenes;

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

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the padre
     */
    public Modelo getPadre() {
        return padre;
    }

    /**
     * @param padre the padre to set
     */
    public void setPadre(Modelo padre) {
        this.padre = padre;
    }

    /**
     * @return the left
     */
    public Integer getLeft() {
        return left;
    }

    /**
     * @param left the left to set
     */
    public void setLeft(Integer left) {
        this.left = left;
    }

    /**
     * @return the right
     */
    public Integer getRight() {
        return right;
    }

    /**
     * @param right the right to set
     */
    public void setRight(Integer right) {
        this.right = right;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    /**
     * @return the producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * @return the imagen
     */
    public Collection<Imagen> getImagenes() {
        return imagenes;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagenes(Collection<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    /**
     * @return the hijos
     */
    public Collection<Modelo> getHijos() {
        List<Modelo> hijosList = (List<Modelo>) hijos;
        Collections.sort(hijosList);
        return hijos;
    }

    /**
     * @param hijos the hijos to set
     */
    public void setHijos(Collection<Modelo> hijos) {
        this.hijos = hijos;
    }

    /**
     * @return the marca
     */
    public Marca getMarca() {
        return marca;
    }

    /**
     * @return the deporte
     */
    public Collection<Deporte> getDeportes() {
        return deportes;
    }

    /**
     * @param deporte the deporte to set
     */
    public void setDeportes(Collection<Deporte> deportes) {
        this.deportes = deportes;
    }

    public void addDeporte(Deporte deporte) {
        if (deporte == null) {
            throw new IllegalValueException("El deporte no puede ser null");
        } else if (deportes == null) {
            deportes = new ArrayList<Deporte>();
        } else if (hasDeporte(deporte)) {
            throw new IllegalValueException("El deporte " + deporte.getNombre() + " ya está registrado para el modelo " + nombre);
        }
        deportes.add(deporte);
    }

    public void removeDeporte(Deporte deporte) {
        if (deporte == null) {
            throw new IllegalValueException("El deporte no puede ser null");
        } else if (!hasDeporte(deporte)) {
            throw new IllegalValueException("El deporte " + deporte.getNombre() + " no está registrado para el modelo " + nombre);
        }
        deportes.remove(deporte);
    }

    public boolean hasDeporte(Deporte deporte) {
        return deportes.contains(deporte);
    }

    /**
     * @return the padres
     */
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

    public int compareTo(Modelo m) {
        return left.compareTo(m.left);
    }

    public boolean isRoot() {
        return (padre == null);
    }

    public boolean isLeaf() {
        return (right - left) == 1;
    }

    public boolean isProducible(){
        return (producto != null);
    }

    /**
     * @return the tipoProducto
     */
    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    /**
     * @param tipoProducto the tipoProducto to set
     */
    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    @Override
    public String toString(){
        return "Modelo["+id+","+nombre+"]";
    }
}
