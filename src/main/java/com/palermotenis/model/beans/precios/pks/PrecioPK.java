package com.palermotenis.model.beans.precios.pks;

import java.io.Serializable;

import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.productos.Producto;

public interface PrecioPK extends Serializable {

    public Producto getProducto();

    public void setProducto(Producto producto);

    public Moneda getMoneda();

    public void setMoneda(Moneda moneda);

    public Pago getPago();

    public void setPago(Pago pago);

    public Integer getCuotas();

    public void setCuotas(Integer cuotas);
}
