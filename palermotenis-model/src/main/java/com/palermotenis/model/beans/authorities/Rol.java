/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */

package com.palermotenis.model.beans.authorities;

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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.palermotenis.model.beans.usuarios.Usuario;

/**
 * 
 * @author Poly
 */
@Entity
@Table(name = "roles")
@NamedQueries(
    { @NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r"),
            @NamedQuery(name = "Rol.findById", query = "SELECT r FROM Rol r WHERE r.id = :id"),
            @NamedQuery(name = "Rol.findByRol", query = "SELECT r FROM Rol r WHERE r.rol = :rol") })
public class Rol implements Serializable, GrantedAuthority {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "Rol")
    private String rol;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, targetEntity = Usuario.class, cascade = CascadeType.REMOVE)
    private Collection<Usuario> usuarios;

    public Rol() {
    }

    public Rol(Integer id) {
        this.id = id;
    }

    public Rol(String rol) {
        this.rol = rol;
    }

    public Rol(Integer id, String rol) {
        this.id = id;
        this.rol = rol;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * @return the usuarios
     */
    public Collection<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios
     *            the usuarios to set
     */
    public void setUsuarios(Collection<Usuario> usuarios) {
        this.usuarios = usuarios;
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
        if (!(object instanceof Rol)) {
            return false;
        }
        Rol other = (Rol) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return rol;
    }

    @Override
    public String getAuthority() {
        return rol;
    }

}
