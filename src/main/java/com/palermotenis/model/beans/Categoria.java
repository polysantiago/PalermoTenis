package com.palermotenis.model.beans;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.google.common.collect.Sets;

@Entity
@Table(name = "categorias", catalog = "palermotenis", schema = "")
@NamedQueries(
    { @NamedQuery(name = "Categoria.findAll", query = "SELECT c FROM Categoria c"),
            @NamedQuery(name = "Categoria.findById", query = "SELECT c FROM Categoria c WHERE c.id = :id"),
            @NamedQuery(name = "Categoria.findByNombre", query = "SELECT c FROM Categoria c WHERE c.nombre = :nombre"),
            @NamedQuery(name = "Categoria.findByCodigo", query = "SELECT c FROM Categoria c WHERE c.codigo = :codigo") })
public class Categoria implements Serializable {

    private static final long serialVersionUID = 6039884288877004883L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Codigo", updatable = false)
    private String codigo;

    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;

    @ManyToMany(mappedBy = "categorias", fetch = FetchType.LAZY, targetEntity = Marca.class)
    private Collection<Marca> marcas;

    @ManyToMany(mappedBy = "categorias", fetch = FetchType.LAZY, targetEntity = Modelo.class)
    private final Collection<Modelo> modelos = Sets.newHashSet();

    public Categoria() {

    }

    public Categoria(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public Collection<Marca> getMarcas() {
        return marcas;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void addMarca(Marca marca) {
        if (hasMarca(marca)) {
            return;
        }
        marcas.add(marca);
        marca.addCategoria(this);
    }

    public void removeMarca(Marca marca) {
        if (!hasMarca(marca)) {
            return;
        }
        marcas.remove(marca);
        marca.removeCategoria(this);
    }

    public boolean hasMarca(Marca marca) {
        return marcas.contains(marca);
    }

    public Collection<Modelo> getModelos() {
        return Sets.newHashSet(modelos);
    }

    public void addModelo(Modelo modelo) {
        if (hasModelo(modelo)) {
            return;
        }
        modelos.add(modelo);
        modelo.addCategoria(this);
    }

    public void removeModelo(Modelo modelo) {
        if (!hasModelo(modelo)) {
            return;
        }
        modelos.remove(modelo);
        modelo.removeCategoria(this);
    }

    public boolean hasModelo(Modelo modelo) {
        return modelos.contains(modelo);
    }
}
