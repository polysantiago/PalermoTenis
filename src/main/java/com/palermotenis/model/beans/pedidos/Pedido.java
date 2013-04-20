/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.beans.pedidos;

import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.clientes.Cliente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "pedidos")
@NamedQueries({
    @NamedQuery(name = "Pedido.findAll", query = "SELECT p FROM Pedido p"),
    @NamedQuery(name = "Pedido.findByNombre", query = "SELECT p FROM Pedido p WHERE p.cliente.nombre = :nombre"),
    @NamedQuery(name = "Pedido.findByEmail", query = "SELECT p FROM Pedido p WHERE p.cliente.email = :email"),
    @NamedQuery(name = "Pedido.findById", query = "SELECT p FROM Pedido p WHERE p.id = :id"),
    @NamedQuery(name = "Pedido.findByTotal", query = "SELECT p FROM Pedido p WHERE p.total = :total"),
    @NamedQuery(name = "Pedido.findByProducto", query = "SELECT p FROM Pedido p JOIN p.pedidosProductos pp WHERE pp.id.stock.producto = :producto")
})
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "Total")
    private double total;

    @Basic(optional = false)
    @Column(name = "Fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha = new Date();

    @JoinColumn(name = "Pago", referencedColumnName = "Codigo")
    @ManyToOne(optional = false)
    private Pago pago;

    @Column(name = "Cuotas")
    private int cuotas;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "Cliente", referencedColumnName = "ID")
    private Cliente cliente;

    @OneToMany(mappedBy = "id.pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<PedidoProducto> pedidosProductos;

    public Pedido() {
    }

    public Pedido(Cliente cliente, Pago pago, int cuotas, double total) {
        this.cliente = cliente;
        this.pago = pago;
        this.cuotas = cuotas;
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public void addPedidoProducto(PedidoProducto pedidoProducto) {
        if (hasPedidoProducto(pedidoProducto)) {
            //TODO handle exception
        }
        pedidosProductos.add(pedidoProducto);
    }

    public void removePedidoProducto(PedidoProducto pedidoProducto) {
        if (!hasPedidoProducto(pedidoProducto)) {
            //TODO handle exception
        }
        pedidosProductos.remove(pedidoProducto);
    }

    public boolean hasPedidoProducto(PedidoProducto pedidoProducto) {
        if(pedidosProductos == null){
            pedidosProductos = new ArrayList<PedidoProducto>();
            return false;
        }
        return pedidosProductos.contains(pedidoProducto);
    }

    /**
     * @return the pedidosProductos
     */
    public Collection<PedidoProducto> getPedidosProductos() {
        return pedidosProductos;
    }

    /**
     * @param pedidosProductos the pedidosProductos to set
     */
    public void setPedidosProductos(Collection<PedidoProducto> pedidosProductos) {
        this.pedidosProductos = pedidosProductos;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the cuotas
     */
    public int getCuotas() {
        return cuotas;
    }

    /**
     * @param cuotas the cuotas to set
     */
    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pedido other = (Pedido) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Pedido[id=" + id + "]";
    }
}
