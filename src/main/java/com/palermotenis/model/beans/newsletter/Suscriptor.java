/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.model.beans.newsletter;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Poly
 */
@Entity
@Table(name = "suscriptores")
@NamedQueries({
    @NamedQuery(name = "Suscriptor.findAll", query = "SELECT s FROM Suscriptor s"),
    @NamedQuery(name = "Suscriptor.findActive", query = "SELECT s FROM Suscriptor s WHERE s.activo = 1"),
    @NamedQuery(name = "Suscriptor.findById", query = "SELECT s FROM Suscriptor s WHERE s.id = :id"),
    @NamedQuery(name = "Suscriptor.findByEmail", query = "SELECT s FROM Suscriptor s WHERE s.email = :email"),
    @NamedQuery(name = "Suscriptor.findByRandomStr", query = "SELECT s FROM Suscriptor s WHERE s.randomStr = :randomStr"),
    @NamedQuery(name = "Suscriptor.findByActivo", query = "SELECT s FROM Suscriptor s WHERE s.activo = :activo")
})
public class Suscriptor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "Email")
    private String email;

    @Basic(optional = false)
    @Column(name = "RandomStr")
    private String randomStr;
    
    @Basic(optional = false)
    @Column(name = "Activo")
    private boolean activo;

    public Suscriptor() {
    }


    public Suscriptor(String email, String randomStr, boolean activo) {
        this.email = email;
        this.randomStr = randomStr;
        this.activo = activo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRandomStr() {
        return randomStr;
    }

    public void setRandomStr(String randomStr) {
        this.randomStr = randomStr;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
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
        if (!(object instanceof Suscriptor)) {
            return false;
        }
        Suscriptor other = (Suscriptor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.palermotenis.model.beans.newsletter.Suscriptor[id=" + id + "]";
    }

}
