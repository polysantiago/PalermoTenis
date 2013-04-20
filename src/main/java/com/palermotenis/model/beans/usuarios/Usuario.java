/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.model.beans.usuarios;

import com.palermotenis.model.beans.authorities.Rol;
import com.palermotenis.model.beans.clientes.Cliente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Proxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Poly
 */
@Entity
@Table(name = "usuarios")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByUsuario", query = "SELECT u FROM Usuario u WHERE u.usuario = :usuario"),
    @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password"),
    @NamedQuery(name = "Usuario.findByActivo", query = "SELECT u FROM Usuario u WHERE u.activo = :activo")})
@Proxy(lazy=false)
public class Usuario implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;

    @Basic(optional = false)
    @Column(name = "password")
    private String password;

    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "authorities",
        joinColumns = @JoinColumn(name = "usuario"),
        inverseJoinColumns = @JoinColumn(name = "rol"))
    private Collection<Rol> roles;

    @OneToOne(optional = true, cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    @JoinColumn(name = "cliente")
    private Cliente cliente;

    public Usuario() {}

    public Usuario(String usuario) {
        this.usuario = usuario;
    }

    public Usuario(String usuario, String password, boolean activo) {
        this.usuario = usuario;
        this.password = password;
        this.activo = activo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * @return the cliente
     */    
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the roles
     */
    public Collection<Rol> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(Collection<Rol> roles) {
        this.roles = roles;
    }

    public void addRol(Rol rol) {
        if(roles == null){
            roles = new ArrayList<Rol>();
        }
        if (hasRol(rol)) {
            //TODO handle exception
        }
        getRoles().add(rol);
    }

    public void removeRol(Rol rol) {
        if (!hasRol(rol)) {
            //TODO handle exception
        }
        getRoles().remove(rol);
    }

    public boolean hasRol(Rol rol) {
        return getRoles().contains(rol);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuario != null ? usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Usuario[usuario=" + usuario + "]";
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return (Collection<GrantedAuthority>)(Object)getRoles();
    }

    public String getUsername() {
        return usuario;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return activo;
    }

}
