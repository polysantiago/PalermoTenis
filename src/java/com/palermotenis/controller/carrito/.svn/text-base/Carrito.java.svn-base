package com.palermotenis.controller.carrito;

import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.Stock;
import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author Poly
 */
public interface Carrito extends Serializable {

    public void agregar(int cantidad, Stock stock);

    public void setCantidad(int cantidad, Stock stock);

    public void quitar(Stock stock);

    public Map<Stock,Item> getContenido();

    public int getCantidad(Stock stock);

    public double getTotal();
    public int getCantidadItems();

    public void vaciar();
    
    public Pago getPago();
    public void setPago(Pago pago);

    public int getCuotas();
    public void setCuotas(int cuotas);

}
