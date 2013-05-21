package com.palermotenis.model.service.atributos;

import java.util.List;

import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.atributos.AtributoMultipleValores;
import com.palermotenis.model.beans.atributos.AtributoSimple;
import com.palermotenis.model.beans.atributos.AtributoTipado;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoSimple;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.model.beans.valores.ValorPosible;

public interface AtributoService {

    void delete(AtributoSimple atributo);

    void updateValorPosible(AtributoTipado atributoTipado, ValorPosible valorPosible);

    void persistAtributoSimple(Producto producto, Integer tipoAtributoId, String valor);

    void persistAtributoTipado(Producto producto, Integer tipoAtributoId, Integer valorPosibleId);

    void persistAtributoMultipleValores(Producto producto, Integer tipoAtributoId, Integer valorPosibleId);

    void persistAtributosClasificatorios(Producto producto);

    void persistAtributosClasificatorios(Producto producto, ValorClasificatorio valorClasificatorio);

    void persistAtributosClasificatorios(Producto producto, Presentacion presentacion);

    void persistAtributosClasificatorios(Producto producto, Sucursal sucursal);

    void clearValoresPosibles(ValorPosible valorPosible);

    List<AtributoMultipleValores> getAtributosByProducto(Producto producto);

    AtributoSimple getAtributoSimple(Producto producto, TipoAtributoSimple tipoAtributo);

    AtributoTipado getAtributoTipado(Producto producto, TipoAtributoSimple tipoAtributo);
}
