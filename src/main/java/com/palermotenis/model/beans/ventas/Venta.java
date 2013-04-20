/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.model.beans.ventas;

import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.clientes.Cliente;
import com.palermotenis.model.beans.usuarios.Usuario;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Poly
 */

@Entity
@Table(name = "ventas")
@NamedQueries({
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v"),
    @NamedQuery(name = "Venta.findById", query = "SELECT v FROM Venta v WHERE v.id = :id"),
    @NamedQuery(name = "Venta.findByFecha", query = "SELECT v FROM Venta v WHERE v.fecha = :fecha")
})
public class Venta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "Fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha = new Date();

    @Basic(optional = false)
    @Column(name = "Cuotas")
    private int cuotas;

    @Basic(optional = false)
    @Column(name = "Total")
    private double total;

    @JoinColumn(name = "Cliente", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cliente cliente;

    @JoinColumn(name = "Pago", referencedColumnName = "Codigo")
    @ManyToOne(optional = false)
    private Pago pago;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venta")
    private Collection<ProductoVenta> productos;

    @JoinColumn(name = "Usuario", referencedColumnName = "usuario")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Venta() {
    }

    public Venta(Cliente cliente, Pago pago) {
        this.cliente = cliente;
        this.pago = pago;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
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

    public Collection<ProductoVenta> getProductos() {
        return productos;
    }

    public void setProductos(Collection<ProductoVenta> productos) {
        this.productos = productos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta other = (Venta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.palermotenis.model.beans.ventas.Venta[id=" + id + "]";
    }

}
