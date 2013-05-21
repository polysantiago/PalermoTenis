/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.model.beans.clientes;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Poly
 */
@Embeddable
public class Direccion implements Serializable {

    @Basic(optional = false)
    @Column(name = "Direccion")
    private String direccion;

    @Basic(optional = false)
    @Column(name = "Ciudad")
    private String ciudad;

    public Direccion(){}

    public Direccion(String direccion, String ciudad) {
        this.direccion = direccion;
        this.ciudad = ciudad;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString(){
        return direccion + " - " + ciudad;
    }

}
