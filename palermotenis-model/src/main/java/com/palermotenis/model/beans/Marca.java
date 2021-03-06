package com.palermotenis.model.beans;

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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "marcas", catalog = "palermotenis", schema = "")
@NamedQueries(
    {
            @NamedQuery(name = "Marca.findAll", query = "SELECT m FROM Marca m"),
            @NamedQuery(name = "Marca.findById", query = "SELECT m FROM Marca m WHERE m.id = :id"),
            @NamedQuery(name = "Marca.findByNombre", query = "SELECT m FROM Marca m WHERE m.nombre = :nombre"),
            @NamedQuery(name = "Marca.findByTipoProductoAndActiveProducto",
                    query = "SELECT DISTINCT p.modelo.marca FROM TipoProducto tp "
                            + "JOIN tp.productos p JOIN p.stocks s "
                            + "WHERE tp = :tipoProducto AND p.activo = 1 AND s.stock > 0 "
                            + "ORDER BY p.modelo.marca.nombre") })
public class Marca implements Serializable, Comparable<Marca> {

    private static final long serialVersionUID = -4459106816415528277L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;

    @Lob
    @Column(name = "Imagen")
    private byte[] imagen;

    @Column(name = "Tamanio")
    private long tamanio;

    @Column(name = "ContentType")
    private String contentType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "modelos", joinColumns = @JoinColumn(name = "marca"), inverseJoinColumns = @JoinColumn(
            name = "categoria"))
    private Collection<Categoria> categorias;

    @OneToMany(mappedBy = "marca", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<Modelo> modelos;

    public Marca() {
    }

    public Marca(String nombre) {
        this.nombre = nombre;
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
     * @return the categorias
     */
    public Collection<Categoria> getCategorias() {
        return categorias;
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
     * @return the imagen
     */
    public byte[] getImagen() {
        return imagen;
    }

    /**
     * @param imagen
     *            the imagen to set
     */
    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    /**
     * @return the tamanio
     */
    public long getTamanio() {
        return tamanio;
    }

    /**
     * @param tamanio
     *            the tamanio to set
     */
    public void setTamanio(long tamanio) {
        this.tamanio = tamanio;
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType
     *            the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @param categorias
     *            the categorias to set
     */
    public void setCategorias(Collection<Categoria> categorias) {
        this.categorias = categorias;
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

    public void addCategoria(Categoria categoria) {
        if (hasCategoria(categoria)) {
            // TODO handle exception
        }
        categorias.add(categoria);
    }

    public void removeCategoria(Categoria categoria) {
        if (!hasCategoria(categoria)) {
            // TODO handle exception
        }
        categorias.remove(categoria);
    }

    public boolean hasCategoria(Categoria categoria) {
        return categorias.contains(categoria);
    }

    public void addModelo(Modelo modelo) {
        if (hasModelo(modelo)) {
            // TODO handle exception
        }
        modelos.add(modelo);
    }

    public void removeModelo(Modelo modelo) {
        if (!hasModelo(modelo)) {
            // TODO handle exception
        }
        modelos.remove(modelo);
    }

    public boolean hasModelo(Modelo modelo) {
        return modelos.contains(modelo);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 53 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        hash = 53 * hash + (this.categorias != null ? this.categorias.hashCode() : 0);
        return hash;
    }

    @Override
    public int compareTo(Marca anotherMarca) {
        return nombre.compareToIgnoreCase((anotherMarca).getNombre());
    }

    @Override
    public String toString() {
        return nombre;
    }
}
