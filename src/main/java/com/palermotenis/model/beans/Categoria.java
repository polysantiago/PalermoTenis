/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Poly
 */
@Entity
@Table(name = "categorias", catalog = "palermotenis", schema = "")
@NamedQueries({
    @NamedQuery(name = "Categoria.findAll", query = "SELECT c FROM Categoria c"),
    @NamedQuery(name = "Categoria.findById", query = "SELECT c FROM Categoria c WHERE c.id = :id"),
    @NamedQuery(name = "Categoria.findByNombre", query = "SELECT c FROM Categoria c WHERE c.nombre = :nombre")
})
public class Categoria implements Serializable {

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
    private Collection<Modelo> modelos;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the marcas
     */
    public Collection<Marca> getMarcas() {
        return marcas;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param marcas the marcas to set
     */
    public void setMarcas(Collection<Marca> marcas) {
        this.marcas = marcas;
    }


    public void addMarca(Marca marca) {
        if (hasMarca(marca)) {
            //TODO handle exception
        }
        marcas.add(marca);
    }

    public void removeMarca(Marca marca) {
        if (!hasMarca(marca)) {
            //TODO handle exception
        }
        marcas.remove(marca);
    }

    public boolean hasMarca(Marca marca) {
        return marcas.contains(marca);
    }

    /**
     * @return the modelos
     */
    public Collection<Modelo> getModelos() {
        return modelos;
    }

    /**
     * @param modelos the modelos to set
     */
    public void setModelos(Collection<Modelo> modelos) {
        this.modelos = modelos;
    }

    public void addModelo(Modelo modelo) {
        if (hasModelo(modelo)) {
            //TODO handle exception
        }
        modelos.add(modelo);
    }

    public void removeModelo(Modelo modelo) {
        if (!hasModelo(modelo)) {
            //TODO handle exception
        }
        modelos.remove(modelo);
    }

    public boolean hasModelo(Modelo modelo) {
        return modelos.contains(modelo);
    }
}
