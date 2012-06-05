/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.model.beans.ventas;

import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.clientes.Cliente;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Poly
 */
@Entity
@Table(name = "listados")
@NamedQueries({
    @NamedQuery(name = "Listado.findAll", query = "SELECT l FROM Listado l"),
    @NamedQuery(name = "Listado.findById", query = "SELECT l FROM Listado l WHERE l.id = :id"),
    @NamedQuery(name = "Listado.findByTotal", query = "SELECT l FROM Listado l WHERE l.total = :total"),
    @NamedQuery(name = "Listado.findByCodAutorizacion", query = "SELECT l FROM Listado l WHERE l.codAutorizacion = :codAutorizacion"),
    @NamedQuery(name = "Listado.findByAutorizado", query = "SELECT l FROM Listado l WHERE l.autorizado = :autorizado")
})
public class Listado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;

    @Basic(optional = false)
    @Column(name = "Total")
    private double total;

    @Basic(optional = false)
    @Column(name = "Cod_Autorizacion")
    private String codAutorizacion;

    @Basic(optional = false)
    @Column(name = "Autorizado")
    private boolean autorizado;

    @JoinColumn(name = "Cliente", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cliente cliente;

    @JoinColumn(name = "Pago", referencedColumnName = "Codigo")
    @ManyToOne(optional = false)
    private Pago pago;

    @Basic(optional = false)
    @Column(name = "Cuotas")
    private int cuotas;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stockListadoPK.listado")
    private Collection<StockListado> stocksListado;

    public Listado() {
    }

    public Listado(String id) {
        this.id = id;
    }

    public Listado(String id, double total, String codAutorizacion, boolean autorizado, Pago pago, int cuotas) {
        this.id = id;
        this.total = total;
        this.codAutorizacion = codAutorizacion;
        this.autorizado = autorizado;
        this.pago = pago;
        this.cuotas = cuotas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCodAutorizacion() {
        return codAutorizacion;
    }

    public void setCodAutorizacion(String codAutorizacion) {
        this.codAutorizacion = codAutorizacion;
    }

    public boolean isAutorizado() {
        return autorizado;
    }

    public void setAutorizado(boolean autorizado) {
        this.autorizado = autorizado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public Collection<StockListado> getStocksListado() {
        return stocksListado;
    }

    public void setStockListados(Collection<StockListado> stocksListado) {
        this.stocksListado = stocksListado;
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
        if (!(object instanceof Listado)) {
            return false;
        }
        Listado other = (Listado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.palermotenis.model.beans.ventas.Listado[id=" + id + "]";
    }

}
