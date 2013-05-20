package com.palermotenis.model.dao.atributos.tipos;

import java.util.List;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.Dao;

public interface TipoAtributoDao extends Dao<TipoAtributo, Integer> {

    void updateTipoAtributoClass(Character metadata, TipoAtributo tipoAtributo);

    List<TipoAtributo> getTiposAtributosByTipoProducto(TipoProducto tipoProducto);

}
