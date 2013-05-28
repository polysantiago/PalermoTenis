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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.palermotenis.model.beans.pedidos.PedidoProducto;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;

@Entity
@Table(name = "stock")
@NamedQueries(
    {
            @NamedQuery(name = "Stock.findAll", query = "SELECT s FROM Stock s"),
            @NamedQuery(name = "Stock.findById", query = "SELECT s FROM Stock s WHERE s.id = :id"),
            @NamedQuery(name = "Stock.findByStock", query = "SELECT s FROM Stock s WHERE s.stock = :stock"),
            @NamedQuery(name = "Stock.findByProducto", query = "SELECT s FROM Stock s WHERE s.producto = :producto"),
            @NamedQuery(
                    name = "Stock.findByProducto-CountPrecio",
                    query = "SELECT COUNT(s) FROM Stock s LEFT JOIN s.producto.preciosUnidad p LEFT JOIN s.producto.preciosCantidad pc WHERE s.producto = :producto AND (s.presentacion = null OR s.presentacion = pc.id.presentacion)"),
            @NamedQuery(
                    name = "Stock.findByProductoClasificable",
                    query = "SELECT s FROM Stock s LEFT JOIN s.presentacion p LEFT JOIN p.tipoPresentacion tp LEFT JOIN s.valorClasificatorio vc WHERE s.producto = :producto ORDER BY s.sucursal.nombre DESC, vc.valor, tp.nombre, p.cantidad"),
            @NamedQuery(name = "Stock.findByProducto,SumOfStock",
                    query = "SELECT SUM(s.stock) FROM Stock s WHERE s.producto = :producto GROUP BY s.producto"),
            @NamedQuery(
                    name = "Stock.findByProducto,ValorClasificatorio",
                    query = "SELECT s FROM Stock s WHERE s.producto = :producto AND s.valorClasificatorio = :valorClasificatorio"),
            @NamedQuery(name = "Stock.findByProducto,Presentacion",
                    query = "SELECT s FROM Stock s WHERE s.producto = :producto AND s.presentacion = :presentacion"),
            @NamedQuery(
                    name = "Stock.findByProducto,Presentacion-Active",
                    query = "SELECT s FROM Stock s WHERE s.producto = :producto AND s.presentacion = :presentacion AND s.stock > 0"),
            @NamedQuery(
                    name = "Stock.findByProducto,Presentacion,ValorClasificatorio",
                    query = "SELECT s FROM Stock s WHERE s.producto = :producto AND s.valorClasificatorio = :valorClasificatorio AND s.presentacion = :presentacion ORDER BY s.producto.modelo.nombre"),
            @NamedQuery(name = "Stock.findByProducto,Sucursal",
                    query = "SELECT s FROM Stock s WHERE s.producto = :producto AND s.sucursal = :sucursal"),
            @NamedQuery(
                    name = "Stock.findByProducto,Sucursal,Presentacion",
                    query = "SELECT s FROM Stock s WHERE s.producto = :producto AND s.sucursal = :sucursal AND s.presentacion = :presentacion"),
            @NamedQuery(
                    name = "Stock.findByProducto,Sucursal,ValorClasificatorio",
                    query = "SELECT s FROM Stock s WHERE s.producto = :producto AND s.valorClasificatorio = :valorClasificatorio AND s.sucursal = :sucursal"),
            @NamedQuery(
                    name = "Stock.findByProducto,Sucursal,ValorClasificatorio,Presentacion",
                    query = "SELECT s FROM Stock s WHERE s.producto = :producto AND s.valorClasificatorio = :valorClasificatorio AND s.presentacion = :presentacion AND s.sucursal = :sucursal"),
            @NamedQuery(name = "Stock.findByTipoProducto",
                    query = "SELECT DISTINCT s FROM Stock s WHERE s.producto.tipoProducto = :tipoProducto"),
            @NamedQuery(name = "Stock.findByTipoProducto-Count",
                    query = "SELECT COUNT(s) FROM Stock s WHERE s.producto.tipoProducto = :tipoProducto"),
            @NamedQuery(
                    name = "Stock.findByTipoProducto-CountPrecio",
                    query = "SELECT COUNT(s) FROM Stock s LEFT JOIN s.producto.preciosUnidad p LEFT JOIN s.producto.preciosCantidad pc WHERE s.producto.tipoProducto = :tipoProducto AND (s.presentacion = null OR s.presentacion = pc.id.presentacion)"),
            @NamedQuery(
                    name = "Stock.findByTipoProducto,Marca",
                    query = "SELECT DISTINCT s FROM Stock s WHERE s.producto.tipoProducto = :tipoProducto AND s.producto.modelo.marca = :marca"),
            @NamedQuery(
                    name = "Stock.findByTipoProducto,Marca-Count",
                    query = "SELECT COUNT(s) FROM Stock s WHERE s.producto.tipoProducto = :tipoProducto AND s.producto.modelo.marca = :marca"),
            @NamedQuery(
                    name = "Stock.findByTipoProducto,Marca-CountPrecio",
                    query = "SELECT COUNT(s) FROM Stock s LEFT JOIN s.producto.preciosUnidad p LEFT JOIN s.producto.preciosCantidad pc WHERE s.producto.tipoProducto = :tipoProducto AND s.producto.modelo.marca = :marca AND (s.presentacion = null OR s.presentacion = pc.id.presentacion)"),
            @NamedQuery(name = "Stock.findByModelo", query = "SELECT s FROM Stock s WHERE s.producto.modelo = :modelo"),
            @NamedQuery(name = "Stock.findByModelo-Count",
                    query = "SELECT COUNT(s) FROM Stock s WHERE s.producto.modelo = :modelo"),
            @NamedQuery(
                    name = "Stock.findByModelo-CountPrecio",
                    query = "SELECT COUNT(s) FROM Stock s LEFT JOIN s.producto.preciosUnidad p LEFT JOIN s.producto.preciosCantidad pc WHERE s.producto.modelo = :modelo"),
            @NamedQuery(
                    name = "Stock.findByNombre",
                    query = "SELECT s FROM Stock s JOIN s.producto.modelo m LEFT JOIN s.producto.modelo.padre p WHERE m.nombre LIKE :nombre OR p.nombre LIKE :nombre OR m.marca.nombre LIKE :nombre or s.producto.tipoProducto.nombre LIKE :nombre)"),
            @NamedQuery(
                    name = "Stock.findByNombre-Active",
                    query = "SELECT s FROM Stock s JOIN s.producto.modelo m LEFT JOIN s.producto.modelo.padre p WHERE s.stock > 0 AND (m.nombre LIKE :nombre OR p.nombre LIKE :nombre OR m.marca.nombre LIKE :nombre OR s.producto.tipoProducto.nombre LIKE :nombre)"),
            @NamedQuery(name = "Stock.findBySucursal", query = "SELECT s FROM Stock s WHERE s.sucursal = :sucursal") })
public class Stock implements Serializable {

    private static final long serialVersionUID = 5472751555314019947L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "Producto", referencedColumnName = "ID")
    @ManyToOne
    private Producto producto;
    @JoinColumn(name = "ValorClasificatorio", referencedColumnName = "ID")
    @ManyToOne
    private ValorClasificatorio valorClasificatorio;
    @JoinColumn(name = "Presentacion", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Presentacion presentacion;
    @JoinColumn(name = "Sucursal", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sucursal sucursal;
    @Column(name = "Stock", insertable = false)
    private Integer stock;
    @Column(name = "CodigoDeBarra")
    private String codigoDeBarra;
    @OneToMany(mappedBy = "id.stock", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Collection<PedidoProducto> pedidos;

    public Stock() {
    }

    public Stock(Producto producto, Sucursal sucursal) {
        this.producto = producto;
        this.sucursal = sucursal;
    }

    public Stock(Producto producto, Sucursal sucursal, ValorClasificatorio valor) {
        this.producto = producto;
        this.sucursal = sucursal;
        this.valorClasificatorio = valor;
    }

    public Stock(Producto producto, Sucursal sucursal, Presentacion presentacion) {
        this.producto = producto;
        this.sucursal = sucursal;
        this.presentacion = presentacion;
    }

    public Stock(Producto producto, Sucursal sucursal, ValorClasificatorio valorClasificatorio,
            Presentacion presentacion) {
        this.producto = producto;
        this.sucursal = sucursal;
        this.valorClasificatorio = valorClasificatorio;
        this.presentacion = presentacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getCodigoDeBarra() {
        return codigoDeBarra;
    }

    public void setCodigoDeBarra(String codigoDeBarra) {
        this.codigoDeBarra = codigoDeBarra;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public ValorClasificatorio getValorClasificatorio() {
        return valorClasificatorio;
    }

    public void setValorClasificatorio(ValorClasificatorio valorClasificatorio) {
        this.valorClasificatorio = valorClasificatorio;
    }

    public Presentacion getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(Presentacion presentacion) {
        this.presentacion = presentacion;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Collection<PedidoProducto> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Collection<PedidoProducto> pedidos) {
        this.pedidos = pedidos;
    }

    public boolean hasPedidos() {
        return pedidos != null && !pedidos.isEmpty();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public void onSold(Integer quantity) {
        if (stock - quantity >= 0) {
            stock -= quantity;
        }
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stock)) {
            return false;
        }
        Stock other = (Stock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Stock[producto=" + producto + ",sucursal= " + sucursal + ",valorClasificatorio=" + valorClasificatorio
                + ",presentacion=" + presentacion + "]";
    }

    @PrePersist
    public void createCodigoDeBarras() {
        String modeloZF = ("0000" + producto.getModelo().getId()).substring(String
            .valueOf(producto.getModelo().getId())
            .length());
        String productoZF = ("0000" + producto.getId()).substring(String.valueOf(producto.getId()).length());
        String presentacionZF = (presentacion != null) ? ("0000" + presentacion.getId()).substring(String.valueOf(
            presentacion.getId()).length()) : "0000";
        setCodigoDeBarra(modeloZF + productoZF + presentacionZF);
    }
}
