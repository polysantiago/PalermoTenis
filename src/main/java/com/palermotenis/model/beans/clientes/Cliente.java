/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */

package com.palermotenis.model.beans.clientes;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.palermotenis.model.beans.pedidos.Pedido;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.beans.ventas.Venta;

/**
 * 
 * @author Poly
 */
@Entity
@Table(name = "clientes")
@NamedQueries(
    {
            @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
            @NamedQuery(name = "Cliente.findById", query = "SELECT c FROM Cliente c WHERE c.id = :id"),
            @NamedQuery(name = "Cliente.findByNombre", query = "SELECT c FROM Cliente c WHERE c.nombre LIKE :nombre"),
            @NamedQuery(name = "Cliente.findByEmail", query = "SELECT c FROM Cliente c WHERE c.email LIKE :email"),
            @NamedQuery(name = "Cliente.findByTelefono", query = "SELECT c FROM Cliente c WHERE c.telefono = :telefono") })
@Proxy(lazy = false)
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;

    @Basic(optional = false)
    @Column(name = "Email")
    private String email;

    @Embedded
    private Direccion direccion;

    @Basic(optional = false)
    @Column(name = "Telefono")
    private String telefono;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private Collection<Venta> ventas;

    @OneToOne(mappedBy = "cliente", cascade =
        { CascadeType.PERSIST, CascadeType.REMOVE })
    private Usuario usuario;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Collection<Pedido> pedidos;

    public Cliente() {
    }

    public Cliente(String nombre, String email, Direccion direccion, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
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

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Collection<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(Collection<Venta> ventas) {
        this.ventas = ventas;
    }

    public Collection<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Collection<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario
     *            the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.palermotenis.model.beans.clientes.Cliente[id=" + id + "]";
    }

}
